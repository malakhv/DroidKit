/**
 * Copyright (C) 2013 Mikhail Malakhov <malakhv@live.ru>
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

package com.malakhv.data;

/**
 * @author Mikhail.Malakhov [malakhv@live.ru|https://github.com/malakhv]
 */
// TODO May be DataWrapper
public class DataObject<T> {

    /**
     * The string uses as delimiter between {@link DataObject} into string. This delimiter used in
     * the formation of the resulting string to separate one {@link DataObject}
     * from another {@link DataObject}.
     * */
    static final String DELIMITER_DATA = ";"; /* package access */

    /**
     * The string uses as delimiter between keys and values into string. This delimiter used in
     * the formation of the resulting string to separate keys and values into {@link DataObject}.
     * */
    static final String DELIMITER_KEY_VALUE = "="; /* package access */

    /**
     * The string used as delimiter between key/value pairs into string. This delimiter used in
     * the formation of the resulting string to separate key/value pairs into {@link DataObject}.
     * */
    static final String DELIMITER_KEY_VALUE_PAIRS = " "; /* package access */

    /**
     * The key that used for load/store raw data from/to any data storage. If it is equal
     * {@code null} the data will not be loaded/saved.
     * */
    private String mKey = null;

    /**
     * The inner data object that represents raw data for load/store from/to any data storage.
     * */
    private Object mObject = null;

    /**
     * Construct a new {@link DataObject} instance with default values.
     * */
    public DataObject() { this(null); }

    /**
     * Construct a new {@link DataObject} instance with specified values.
     * @param key The setting's key.
     * */
    public DataObject(String key) { super(); mKey = key; }

    /**
     * Returns the key value.
     * */
    public String getKey() { return mKey; }

    /**
     * Set the new value for key.
     * */
    public void setKey(String key) { mKey = key; }

    /**
     * Returns the number of elements stored in this {@link DataObject}.
     * */
    public int size() { return getData() != null ? 1: 0; }

    /**
     * Checks this {@link DataObject} has any data or not.
     * */
    public boolean isEmpty() { return size() > 0; }

    /**
     * Returns the raw data object.
     * */
    @SuppressWarnings("unchecked")
    public T getData() { return (T) mObject; }

    /**
     * Set the new raw data object.
     * */
    public void setData(T object) { mObject = object; }

    /**
     * Clear a data in this object.
     * */
    public void clear() { mObject = null; }

    /**
     * Returns a data of this object as string. This method used for store data to any data storage
     * as string.
     * */
    @Override
    public String toString() { return mObject != null ? mObject.toString() : null; }

    /**
     * Load data for this object from string. This method used for load data from any data storage.
     * @return The number of elements stored in this {@link DataObject}.
     * */
    public int fromString(String source) { clear(); mObject = source; return size(); }

}