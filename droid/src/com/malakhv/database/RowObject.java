/* *
 * Copyright (C) 1996-2018 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 * */

package com.malakhv.database;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.malakhv.util.StrUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Class represent a row in database's table as an object.
 * @see RowList
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
public class RowObject {

    /** The row's data as a map. We store all values as {@code String}. */
    private final Map<String, String> mData = new HashMap<>();

    /**
     * Construct a new {@link RowObject} instance with default parameters.
     * */
    public RowObject() { super(); }

    /**
     * Construct a new {@link RowObject} instance as a copy of specified {@link RowObject}.
     * */
    public RowObject(RowObject origin) {
        this();
        this.obtain(origin);
    }

    /**
     * @return True if this object has no any data, otherwise false.
     * */
    public boolean isEmpty() {
        return mData.isEmpty();
    }

    /**
     * Removes all data from this object.
     * */
    public void clear() {
        mData.clear();
    }

    /**
     * @return The column's value as {@code int}.
     * */
    public int getInt(String column, int def) {
        final String value = mData.get(column);
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }
    
    /**
     * @return The column's value as {@code int}.
     * */
    public int getInt(String column) {
        return getInt(column, -1);
    }

    /**
     * @return The column's value as {@code long}.
     * */
    public long getLong(String column, long def) {
        final String value = mData.get(column);
        return StrUtils.toLong(value, def);
    }

    /**
     * @return The column's value as {@code long}.
     * */
    public long getLong(String column) {
        return getLong(column, -1);
    }

    /**
     * @return The column's value as {@link String}.
     * */
    public String getString(String column) {
        return mData.get(column);
    }

    /**
     * @return The row's id, or {@code -1}.
     * */
    // TODO Want to have constant NO_ID = -1, may be in my common DBSQLite class
    public long getId() {
        return getLong(BaseColumns._ID);
    }

    /**
     * @return True, if the row's data contains id.
     * */
    public boolean hasId() {
        return mData.containsKey(BaseColumns._ID);
    }

    /**
     * @return True, if the row's data contains specified field and it's not empty.
     * */
    public boolean hasData(String field) {
        if (StrUtils.isEmpty(field)) return false;
        final String data = mData.get(field);
        return !StrUtils.isEmpty(data);
    }

    /**
     * Put new data to this object.
     * @return True, if data has been added, otherwise false.
     * */
    protected boolean putRaw(String column, String value) {
        if (StrUtils.isEmpty(column) || StrUtils.isEmpty(value)) return false;
        mData.put(column, value);
        return true;
    }

    /**
     * Called immediately before data item from database will be added to this object.
     * This is a good place to process {@code value}.
     * <p>The default implementation just returns the original value.</p>
     *
     * @return The {@value} that will be added to this object.
     * */
    protected String onItemLoad(String key, String value) {
        return value; // In default implementation we just return value as is
    }

    /**
     * Called immediately before data loading begins.
     * <p>The default implementation just clear existing data.</p>
     * */
    protected void onPreLoad() {
        clear();
    }

    /**
     * Called immediately after loading data.
     * */
    protected void onPostLoad() {
        /* Do nothing in default implementation */
    }

    /**
     * Loads data from cursor.
     * */
    public void load(Cursor cursor) {
        onPreLoad();
        int count;
        if (cursor == null || cursor.getPosition() < 0 || (count = cursor.getCount()) <= 0
                || cursor.getPosition() >= count) return;
        int col = cursor.getColumnCount();
        for (int i = 0; i < col; i++) {
            final String key = cursor.getColumnName(i);
            String value = cursor.getString(i);
            value = onItemLoad(key, value);
            putRaw(key, value);
        }
        onPostLoad();
    }

    /**
     * Obtains data from other object.
     * */
    public void obtain(RowObject origin) {
        clear();
        if (origin == null || origin.isEmpty()) return;
        mData.putAll(origin.mData);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("RowObject{");
        if (isEmpty()) {
            builder.append("empty}");
            return builder.toString();
        }

        TreeSet<String> keys = new TreeSet<>(mData.keySet());
        for (String key: keys) {
            builder.append(key)
                    .append(StrUtils.CHAR_EQUAL)
                    .append(mData.get(key)).append(StrUtils.CHAR_COMMA);
        }
        builder.append("}");
        return builder.toString();
    }
}
