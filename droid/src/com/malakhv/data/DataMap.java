/* *
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

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
public class DataMap extends DataObject<Map<String, String>> {

    /** The tag for LogCat. */
    private static final String TAG = DataMap.class.getSimpleName();

    /**
     * Construct a new {@link DataMap} instance with default values.
     * */
    public DataMap() { this(null); }

    /**
     * Construct a new {@link DataMap} instance with specified values.
     * @param key The setting's key.
     * */
    public DataMap(String key) {
        super(key); setData(new HashMap<String, String>());
    }

    /**
     * Returns whether this {@link DataMap} contains the specified key.
     * */
    public boolean contains(String key) { return getData().containsKey(key); }

    /**
     * Returns whether this {@link DataMap} contains the specified key/value pairs.
     * */
    public boolean contains(String key, String value) {
        final String v = get(key);
        return v != null && v.equals(value);
    }

    /**
     * Returns whether this {@link DataMap} contains the specified key/value pairs.
     * */
    public boolean contains(String key, int value) {
        return contains(key, String.valueOf(value));
    }

    /**
     * Returns whether this {@link DataMap} contains the specified key/value pairs.
     * */
    public boolean contains(String key, boolean value) {
        return contains(key, value ? 1 : 0);
    }

    /**
     * Returns the value associated with the given key, or {@code null} if no mapping for the
     * specified key is found.
     * @return A {@code String} value.
     * */
    public String get(String key) { return getData().get(key); }

    /**
     * Returns the value associated with the given key, or {@code def} if no mapping for the
     * specified key is found.
     * @return A {@code String} value.
     * */
    public String get(String key, String def) {
        final String value = get(key);
        return value != null ? value : def;
    }

    /**
     * Returns the value associated with the given key, or {@code 0} if no mapping of the
     * desired type exists for the given key.
     * @return An {@code int} value.
     * */
    public int getInt(String key) { return getInt(key, 0); }

    /**
     * Returns the value associated with the given key, or {@code def} if no mapping of the
     * desired type exists for the given key.
     * @return An {@code int} value.
     * */
    public int getInt(String key, int def) {
        final String value = get(key);
        if (value == null) return def;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.e(TAG, "getInt: value \"" + value +
                    "\" for key \"" + key + "\" cannot be parsed as an integer value");
        }
        return def;
    }

    /**
     * Returns the value associated with the given key, or {@code 0} if no mapping of the
     * desired type exists for the given key.
     * @return An {@code long} value.
     * */
    public long getLong(String key) { return getInt(key, 0); }

    /**
     * Returns the value associated with the given key, or {@code def} if no mapping of the
     * desired type exists for the given key.
     * @return An {@code long} value.
     * */
    public long getLong(String key, long def) {
        final String value = get(key);
        if (value == null) return def;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.e(TAG, "getLong: value \"" + value +
                    "\" for key \"" + key + "\" cannot be parsed as an long value");
        }
        return def;
    }

    /**
     * Returns the value associated with the given key, or {@code false} if no mapping of the
     * desired type exists for the given key.
     * @return A {@code boolean} value.
     * */
    public boolean getBoolean(String key) { return getBoolean(key, false); }

    /**
     * Returns the value associated with the given key, or {@code def} if no mapping of the
     * desired type exists for the given key.
     * @return A {@code boolean} value.
     * */
    public boolean getBoolean(String key, boolean def) {
        final String value = get(key);
        if (value == null) return def;
        final String trueValue= "1";
        final String falseValue= "0";
        if (!trueValue.equals(value) && !falseValue.equals(value)) {
            Log.e(TAG, "getBoolean: value \"" + value +
                    "\" for key \"" + key + "\" cannot be parsed as an boolean value");
            return def;
        }
        return trueValue.equals(value);
    }

    /**
     * Maps the specified key to the specified value.
     * @return The value of any previous mapping with the specified key or null if there was no
     * mapping.
     * */
    public String put(String key, String value) {
        if (key == null || key.isEmpty()) return null;
        if (value == null || value.isEmpty()) {
            return remove(key);
        } else {
            return getData().put(key, value);
        }
    }

    /**
     * Maps the specified key to the specified value.
     * @return The value of any previous mapping with the specified key or null if there was no
     * mapping.
     * */
    public String put(String key, int value) {
        return put(key, String.valueOf(value)); }

    /**
     * Maps the specified key to the specified value.
     * @return The value of any previous mapping with the specified key or null if there was no
     * mapping.
     * */
    public String put(String key, long value) {
        return put(key, String.valueOf(value)); }

    /**
     * Maps the specified key to the specified value.
     * @return The value of any previous mapping with the specified key or null if there was no
     * mapping.
     * */
    public String put(String key, boolean value) { return put(key, value ? 1 : 0); }

    /**
     * Removes a mapping with the specified key from this Map.
     * @return The value of the removed mapping or null if no mapping for the specified key
     * was found.
     * */
    public String remove(String key) { return getData().remove(key); }

    /**
     * Removes all elements from this {@link DataMap}, leaving it empty.
     * @see #isEmpty()
     * @see #size()
     * */
    @Override
    public void clear() { if (getData() != null) getData().clear(); }

    /**
     * Returns a string with key/value pairs stored in this {@link DataMap}.
     * */
    @Override
    public String toString() {
        final Map<String, String> map = getData();
        if (map == null || map.isEmpty()) return ""; // Empty string, by default
        StringBuilder sBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            sBuilder.append(key).append(DELIMITER_KEY_VALUE).append(map.get(key))
                    .append(DELIMITER_KEY_VALUE_PAIRS);
        }
        return sBuilder.toString().trim();
    }

    /**
     * Loads key/value pairs to this {@link DataMap} from specified string.
     * @return The number of key/value pairs in this {@link DataMap}.
     * */
    @Override
    public int fromString(String source) {
        clear();
        if (source == null || source.length() == 0) return size();
        String[] entries = source.split(DELIMITER_KEY_VALUE_PAIRS);
        final Map<String, String> map = getData();
        for (String entry : entries) {
            String[] pair = entry.split(DELIMITER_KEY_VALUE);
            if (pair.length == 2) map.put(pair[0], pair[1]);
        }
        return size();
    }

    /**
     * Returns the number of key/value pairs in this {@link DataMap}.
     * @see #isEmpty()
     * */
    @Override
    public int size() { return getData().size(); }

}
