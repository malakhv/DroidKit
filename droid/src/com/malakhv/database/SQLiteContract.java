/* *
 * Copyright (C) 2022 Mikhail Malakhov <malakhv@gmail.com>
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

import android.provider.BaseColumns;

/**
 * The common SQLite database contract.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings("unused")
public class SQLiteContract {

    /** The default value for row ID, if data doesn't exist. */
    public static final long NO_ID = -1;

    /**
     * The base interface for any table in database.
     * */
    public interface BaseTable extends BaseColumns { /* Empty, right now */ }

    /**
     * The base interface for any global table (table that sync with server) in database.
     * */
    public interface GlobalTable extends BaseColumns {

        /**
         * The unique global ID for a row.
         * <p>Type: INTEGER</p>
         * */
        String COLUMN_GLOBAL_ID = "_id_global";

    }

    /**
     * The base interface for any table in database which support different localization.
     * */
    public interface LocaleTable {

        /**
         * The localisation of a row object.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_LOCALE = "locale";

    }

    /**
     * The base interface for any table in database which includes location information.
     * */
    public interface LocationTable {

        /**
         * The location of a row object in WGS84 format (for example 50.083698,45.407367).
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_LOCATION = "location";

    }

    /**
     * The base interface for any table in database which includes direct links to popular
     * map services.
     * */
    public interface MapTable {

        /**
         * The direct link to a row object on Google map.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_MAP_GOOGLE = "google";

        /**
         * The direct link to a row object on MAPS.ME map.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_MAP_MAPSME = "mapsme";

        /**
         * The direct link to a row object on Mapy.cz map.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_MAP_MAPYCZ = "mapycz";

        /**
         * The direct link to a row object on Yandex map.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_MAP_YANDEX = "yandex";

    }

    /**
     * The base interface for any table in database which includes link to a web-site.
     * */
    public interface WebTable {

        /**
         * The web link to a row object.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_WEB = "web";

    }

    /**
     * The base interface for any table in database which includes link to a Wikipedia.
     * */
    public interface WikiTable {

        /**
         * The Wikipedia link to a row object.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_WIKI = "wiki";

    }

    /**
     * The base interface for any table in database which includes the name of stock
     * keeping unit.
     * */
    public interface SkuTable {

        /**
         * The stock keeping unit.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        String COLUMN_SKU = "sku";

    }

    //------------------------------------------------------------------------------------------
    // Locale
    //------------------------------------------------------------------------------------------

    /**
     * The special table contains information about supported locales.
     * */
    public static class TableLocale {

        /** The name of this table. */
        public static final String TABLE_NAME = "locale";

        /**
         * The locale code contains language and country/region code. For example: {@code ru_RU},
         * {@code en_US}, {@code cs_rCZ}. By default we can use only language code in app.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        public static final String COLUMN_CODE = "code";

        /**
         * The locale name on its language.
         * <p>Type: TEXT</p>
         * */
        // @Column(Cursor.FIELD_TYPE_STRING)
        public static final String COLUMN_NAME = "name";

        /**
         * The "enabled" flag. It means enabled locale for user, or not. By default,
         * it's {@code true}.
         * <p>Type: BOOLEAN</p>
         * */
        public static final String COLUMN_ENABLED = "enabled";

        /** The SQL expression to create this table. */
        public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_CODE + " TEXT PRIMARY KEY ASC UNIQUE NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_ENABLED + " BOOLEAN DEFAULT (1) );";

    }

    /**
     * The view (virtual table) contains information about available to the user locales.
     * */
    public static class ViewLocale extends TableLocale {

        /** The name of this view. */
        public static final String VIEW_NAME = "view_locale";

        /** The SQL expression to create this view. */
        public static final String SQL_CREATE =  "CREATE VIEW " + VIEW_NAME
                + " AS SELECT * FROM " + TABLE_NAME
                + " WHERE " + TABLE_NAME + "." + COLUMN_ENABLED + " = 1;";

    }

    //------------------------------------------------------------------------------------------
    // SQLiteContract
    //------------------------------------------------------------------------------------------

    /** This class has only static data, not need to create instance. */
    protected SQLiteContract() { /* Empty */ }

}
