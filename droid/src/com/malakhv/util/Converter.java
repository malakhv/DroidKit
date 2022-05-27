package com.malakhv.util;

public final class Converter {

    public static final int DEF_INT = -1;

    public static final long DEF_LONG = -1L;

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

}
