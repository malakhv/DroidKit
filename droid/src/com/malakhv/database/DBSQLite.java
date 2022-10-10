/* *
 * Copyright (C) 2015 Mikhail Malakhov <malakhv@gmail.com>
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

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.malakhv.util.StrUtils;

import java.util.Arrays;

/**
 * The basic class for working with SQLite database.
 * @author Mikhail.Malakhov
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class DBSQLite extends SQLiteOpenHelper {

    private final Context mContext;

    /**
     * Make a new {@link DBSQLite} instance with specified parameters.
     * */
    public DBSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * Make a new {@link DBSQLite} instance with specified parameters.
     * */
    public DBSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory,
            int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
    }

    /**
     * @return The current context.
     * */
    protected Context getContext() {
        return mContext;
    }

    /**
     * Returns readable cursor for a table.
     * @return The readable cursor, or null.
     * */
    public Cursor getReadableCursor(String table) {
        return getReadableCursor(table, null);
    }

    /**
     * Returns readable cursor for a table.
     * @return The readable cursor, or null.
     * */
    public Cursor getReadableCursor(String table, String locale) {
        return getReadableCursor(table, locale, null, null);
    }

    /**
     * Returns readable cursor for a table's row with specified {@code id}.
     * @return The readable cursor, or null.
     * */
    public Cursor getReadableCursor(String table, String locale, long id) {
        return getReadableCursor(table, locale, BaseColumns._ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /**
     * Returns readable cursor for a table.
     * @return The readable cursor, or null.
     * */
    public Cursor getReadableCursor(String table, String locale, String selection,
            String[] selectionArgs ) {
        return getReadableCursor(table, null, locale, selection,
                selectionArgs, null, null, null);
    }

    /**
     * Returns readable cursor for a table.
     * @return The readable cursor, or null.
     * */
    public Cursor getReadableCursor(String table, String[] columns, String locale, String selection,
                                    String[] selectionArgs, String groupBy, String having, String orderBy) {
        final boolean hasLocale = !StrUtils.isEmpty(locale);

        // Build selection
        String sel = selection;
        if (hasLocale) {
            sel = StrUtils.isEmpty(sel) ? "" : (sel + " and ");
            sel += SQLiteContract.LocaleTable.COLUMN_LOCALE + " = ? ";
        }

        // Build selection arguments
        String[] args = null;
        if (hasLocale) {
            if (selectionArgs != null) {
                args = Arrays.copyOf(selectionArgs, selectionArgs.length + 1);
                args[args.length -1 ] = locale;
            } else {
                args = new String[] { locale };
            }
        } else {
            args = selectionArgs;
        }

        // Receive cursor
        try {
            return this.getReadableDatabase().query(table, columns, sel, args, groupBy,
                    having, orderBy);
        } catch (SQLiteException e) {
            return null;
        }
    }

    /**
     * Get writable cursor for a table.
     * @param table The name of table.
     * @return Writable Cursor for this table.
     * */
    public Cursor getWritableCursor(String table) {
        return this.getWritableDatabase().query(table,null, null, null, null,
                null, null);
    }



    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns
     * data.
     * @param sql The SQL statement to be executed. Multiple statements separated by semicolons
     *            are not supported.
     * */
    public static boolean execSQL(SQLiteDatabase db, String sql) {
        if (db == null) return false;
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Attaches the database with given {@code name} and {@code path}.
     * */
    public static boolean attachDatabase(SQLiteDatabase db, String name, String path) {
        return execSQL(db, "ATTACH '" + path + "' AS " + name);
    }

    /**
     * Detaches the database with specified {@code name}.
     * */
    public static boolean detachDatabase(SQLiteDatabase db, String name) {
        return execSQL(db, "DETACH DATABASE " + name);
    }

    /**
     * Execute SQL query for drop table from data base.
     * @param db The data base.
     * @param table The name of table.
     * */
    public static boolean dropTable(SQLiteDatabase db, String table) {
        return DBSQLite.execSQL(db, "DROP TABLE IF EXISTS " + table);
    }

    /**
     * Execute SQL query for drop view from data base.
     * @param db The data base.
     * @param table The name of view.
     * */
    public static boolean dropView(SQLiteDatabase db, String table) {
        return DBSQLite.execSQL(db, "DROP VIEW IF EXISTS " + table);
    }

    /**
     * Execute SQL query for removing all data from specified table.
     * @param db The data base.
     * @param table The name of table.
     * */
    public static boolean clearTable(SQLiteDatabase db, String table) {
        return DBSQLite.execSQL(db, "" + table);
    }

}