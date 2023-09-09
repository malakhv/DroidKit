/* *
 * Copyright (C) 1996-2013 Mikhail Malakhov <malakhv@gmail.com>
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

package com.malakhv.util;

/**
 * Class contains some methods for working with Strings.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings({"unused"})
public final class StrUtils {

    /** The empty string. */
    public static final String EMPTY = "";

    /** The space character. */
    public static final String CHAR_SPACE = " ";

    /** The dot character. */
    public static final String CHAR_DOT = ".";

    /** The comma character. */
    public static final String CHAR_COMMA = ",";

    /** The equal sign. */
    public static final String CHAR_EQUAL = "=";

    /** The special char: end of the line. */
    public static final String CHAR_NEW_LINE = "\n";

    /** The special char: empty line. */
    public static final String CHAR_EMPTY_LINE = "\n\n";

    /** The special char: Slash*/
    public static final String CHAR_SLASH = "/";

    /**
     * Returns {@code true} if the string is null, 0-length, or this string contains only
     * whitespaces.
     * */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }

    /**
     * Converts specified string to {@code long} value.
     * @return The string {@code value} represent as {@code long}, or {@code def}.
     * */
    public static long toLong(String value, long def) {
        if (isEmpty(value)) return def;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code int} value.
     * @return The string {@code value} represent as {@code int}, or {@code def}.
     * */
    public static int toInt(String value, int def) {
        if (isEmpty(value)) return def;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code float} value.
     * @return The string {@code value} represent as {@code float}, or {@code def}.
     * */
    public static float toFloat(String value, float def) {
        if (isEmpty(value)) return def;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * This class has only static data, not need to create instance.
     * */
    private StrUtils() { /* Empty */ }
}
