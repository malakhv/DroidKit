/* *
 * Copyright (C) 2018 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * */

package com.malakhv.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The abstract class represents the list of rows in database table.
 * @see RowObject
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
public abstract class RowList<E extends RowObject> implements Iterable<E> {

    /**
     * The collection of items into this object. By default, we user {@link ArrayList}, but
     * child class can change it in {@link #makeItems(int)}.
     * @see #makeItems(int)
     * */
    private final List<E> mItems;

    /**
     * The class of item in this list. It will used to construct them.
     * */
    private final Class<E> mItemClass;

    /**
     * The assigned database.
     * @see #assign(DBSQLite)
     * */
    private DBSQLite mDB = null;

    /**
     * Construct a new {@link RowList} instance wit specified parameters.
     * @param itemClass The class of item in this list.
     * */
    public RowList(Class<E> itemClass) {
        this(itemClass, 0);
    }

    /**
     * Construct a new {@link RowList} instance wit specified parameters.
     * @param itemClass The class of item in this list.
     * @param capacity The initial capacity of this list, or 0, for default capacity.
     * */
    public RowList(Class<E> itemClass, int capacity) {
        mItems = makeItems(capacity);
        mItemClass = itemClass;
    }

    /**
     * Makes an item instance.
     * @see #mItemClass
     * */
    protected E makeItem() {
        try {
            return mItemClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    /**
     * Makes an empty collection of items for this object. By default, an {@link ArrayList} will
     * be created, but this behaviour can be changed in child classes.
     * @param capacity The initial capacity of the collection, or 0, for default capacity.
     * */
    protected List<E> makeItems(int capacity) {
        if (capacity > 0) {
            return new ArrayList<>(capacity);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Clears all data in this {@link RowList}.
     * */
    public void clear() {
        mItems.clear();
    }

    /**
     * @return True if this object contains no elements.
     * */
    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    /**
     * @return The number of elements in this object (its cardinality).
     * */
    public int size() {
        return mItems.size();
    }

    /**
     * @return The element at the specified index (position) in this list.
     * */
    public E getItem(int index) {
        return mItems.get(index);
    }

    /**
     * Removes the element at the specified position in this list
     * */
    public void remove(int index) {
        mItems.remove(index);
    }

    /**
     * Updates this list. The child class can implements any update logic. By default this
     * method clears list.
     * */
    public void update() {
        clear();
    }

    /**
     * Called before an {@code item} will be added to this list. This method can used to manage
     * adding operation during data loading.
     * @return True, if you want to have specified item in this list, otherwise false.
     * */
    protected boolean onItemAdd(E item) {
        return item != null;
    }

    /**
     * Assign specified database to this list.
     * */
    protected void assign(DBSQLite db) {
        mDB = db;
    }

    protected boolean hasDatabase() {
        return mDB != null;
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale) {
        load(table, locale, null, null);
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale, int max) {
        load(db, table, locale, null, null, max);
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale, String selection,
            String[] selectionArgs) {
        load(db, table, locale, selection, selectionArgs, null);
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale, String selection,
            String[] selectionArgs, int max) {
        load(db, table, locale, selection, selectionArgs, null, max);
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale, String selection,
            String[] selectionArgs, String orderBy) {
        load(db, table, locale, selection, selectionArgs, orderBy, 0);
    }

    /**
     * Loads data to this list from specified database.
     * */
    protected void load(DBSQLite db, String table, String locale, String selection,
            String[] selectionArgs, String orderBy, int max) {
        if (db == null) return;
        final Cursor cursor = db.getReadableCursor(table, null, locale, selection,
                selectionArgs, null, null, orderBy);
        clear();
        if (cursor == null || !cursor.moveToFirst()) return;
        if (max <= 0) max = Integer.MAX_VALUE;
        do {
            final E item = makeItem();
            item.load(cursor);
            if (onItemAdd(item)) mItems.add(item);
        } while (cursor.moveToNext() && --max > 0);
        cursor.close();
    }

    /**
     *  Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale) {
        load(mDB, table, locale);
    }

    /**
     *  Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale, int max) {
        load(mDB, table, locale, max);
    }

    /**
     *  Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale, String selection, String[] selectionArgs) {
        load(mDB, table, locale, selection, selectionArgs);
    }

    /**
     * Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale, String selection, String[] selectionArgs,
            int max) {
        load(mDB, table, locale, selection, selectionArgs, max);
    }

    /**
     * Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale, String selection, String[] selectionArgs,
            String orderBy) {
        load(mDB, table, locale, selection, selectionArgs, orderBy);
    }

    /**
     * Loads data to this list from assigned database.
     * */
    protected void load(String table, String locale, String selection, String[] selectionArgs,
            String orderBy, int max) {
        load(mDB, table, locale, selection, selectionArgs, orderBy, max);
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<E> iterator() {
        return mItems.iterator();
    }

}
