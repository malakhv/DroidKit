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

package com.malakhv.util;

import com.malakhv.data.BitwiseSet;

/**
 * Class contains some methods to convert values between different types.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
public final class Converter {

    /** The default {@code byte} value. */
    public static final byte DEF_BYTE = -1;

    /** The default {@code int} value. */
    public static final int DEF_INT = -1;

    /** The default {@code long} value. */
    public static final long DEF_LONG = -1L;

    /** The default {@code float} value. */
    public static final float DEF_FLOAT = -1F;

    /** The default {@code double} value. */
    public static final double DEF_DOUBLE = -1D;

    /** The default prefix for hexadecimal value that represent as {@code string}. */
    public static final String DEF_HEX_PREFIX = "0x";

    /** The default prefix for binary value that represent as {@code string}. */
    public static final String DEF_BIN_PREFIX = "0b";

    /**
     * Converts specified {@code int} to binary string with default prefix.
     * @see #DEF_BIN_PREFIX
     * @see #intToBinaryString(int, String)
     * */
    public static String intToBinaryString(int value) {
        return intToBinaryString(value, DEF_BIN_PREFIX);
    }

    /**
     * Converts specified {@code int} to binary string.
     * @see #intToBinaryString(int)
     * */
    public static String intToBinaryString(int value, String prefix) {
        final BitwiseSet bs = new BitwiseSet(value);
        return prefix + bs.toBinaryString();
    }

    /**
     * Converts specified {@code int} to hexadecimal string with default prefix.
     * @see #DEF_HEX_PREFIX
     * @see #intToHexStr(int, String)
     * */
    public static String intToHexStr(int value) {
        return intToHexStr(value, DEF_HEX_PREFIX);
    }

    /**
     * Converts specified {@code int} to hexadecimal string.
     * @see #intToHexStr(int)
     * */
    public static String intToHexStr(int value, String prefix) {
        return prefix + Integer.toHexString(value).toUpperCase();
    }

    /**
     * Converts specified string to {@code byte} value.
     * @return The string {@code value} represent as {@code byte}, or {@link #DEF_BYTE} when
     * converting impossible.
     * */
    public static byte strToByte(String value) {
        return strToByte(value, DEF_BYTE);
    }

    /**
     * Converts specified string to {@code byte} value.
     * @return The string {@code value} represent as {@code byte}, or {@code def} when
     * converting impossible.
     * */
    public static byte strToByte(String value, byte def) {
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Byte.parseByte(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code int} value.
     * @return The string {@code value} represent as {@code int}, or {@link #DEF_INT} when
     * converting impossible.
     * */
    public static int strToInt(String value) {
        return strToInt(value, DEF_INT);
    }

    /**
     * Converts specified string to {@code int} value.
     * @return The string {@code value} represent as {@code int}, or {@code def} when
     * converting impossible.
     * */
    public static int strToInt(String value, int def) {
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code long} value.
     * @return The string {@code value} represent as {@code long}, or {@link #DEF_LONG} when
     * converting impossible.
     * */
    public static long strToLong(String value) {
        return strToLong(value, DEF_LONG);
    }

    /**
     * Converts specified string to {@code long} value.
     * @return The string {@code value} represent as {@code long}, or {@code def} when
     * converting impossible.
     * */
    public static long strToLong(String value, long def) {
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code float} value.
     * @return The string {@code value} represent as {@code float}, or {@link #DEF_FLOAT} when
     * converting impossible.
     * */
    public static float strToFloat(String value) {
        return strToFloat(value, DEF_FLOAT);
    }

    /**
     * Converts specified string to {@code float} value.
     * @return The string {@code value} represent as {@code float}, or {@code def} when
     * converting impossible.
     * */
    public static float strToFloat(String value, float def) {
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Converts specified string to {@code double} value.
     * @return The string {@code value} represent as {@code double}, or {@link #DEF_DOUBLE} when
     * converting impossible.
     * */
    public static double strToDouble(String value) {
        return strToDouble(value, DEF_DOUBLE);
    }

    /**
     * Converts specified string to {@code double} value.
     * @return The string {@code value} represent as {@code double}, or {@code def} when
     * converting impossible.
     * */
    public static double strToDouble(String value, double def) {
        if (StrUtils.isEmpty(value)) return def;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return def;
        }
    }

}
