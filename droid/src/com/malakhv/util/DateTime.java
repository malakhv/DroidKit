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

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Class contains some methods for working with date and time.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
public final class DateTime {

    /** The number of milliseconds in one minute. */
    public static final long MILLIS_PER_MINUTE = 1000 * 60;

    /** The number of milliseconds in one hour. */
    public static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;

    /** The number of milliseconds in one day. */
    public static final long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

    /**
     * Checks the specified hour value valid or not.
     * */
    public static boolean isValidHour(int hour) { return hour >= 0 && hour <= 23; }

    /**
     * Checks the specified minute value valid or not.
     * */
    public static boolean isValidMinute(int minute) { return minute >= 0 && minute <= 59; }

    /**
     * Returns the minute of day value from specified time.
     * */
    public static int getMinuteOfDay(long time) {
        return (int) ((time % MILLIS_PER_DAY) / MILLIS_PER_MINUTE);
    }

    public static int getMinuteOfHour(long time) {
        return getMinuteOfDay(time) % 60;
    }

    /**
     * Returns the hour of day value from specified time.
     * */
    public static int getHourOfDay(long time) {
        return (int) ((time % MILLIS_PER_DAY) / MILLIS_PER_HOUR);
    }

    /**
     * Returns the hour of day value from specified time.
     * */
    public static int getHourOfDay(long time, TimeZone zone) {
        return zone != null ? getHourOfDay(time + zone.getOffset(time))
                :   getHourOfDay(time);
    }

    public static long getMillis(int hour, int minute) {
        return MILLIS_PER_HOUR * hour + MILLIS_PER_MINUTE * minute;
    }

    /**
     * Returns {@link Calendar} instance with default time zone and zero Unix time.
     * */
    private static Calendar getUtcCalendar() {
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        return c;
    }

    /**
     * Checks the two time periods are Intersected or not.
     * @param s1 The start time stamp of period 1
     * @param e1 The end time stamp of period 1
     * @param s2 The start time stamp of period 2
     * @param e2 The end time stamp of period 2
     * */
    public static boolean isTimeIntersects(long s1, long e1, long s2, long e2) {
        s1 = getMinuteOfDay(s1); e1 = getMinuteOfDay(e1);
        s2 = getMinuteOfDay(s2); e2 = getMinuteOfDay(e2);
        return (s1 > e1 && s2 > e2) // Both the intervals cross over midnight
                || ((s1 > e1 || s2 > e2) && !(e1 < s2 && e2 < s1))
                || (s1 < e2 && e1 > s2); // Both the intervals not cross over midnight
    }

    /* This class has only static data, not need to create instance. */
    private DateTime() { /* Empty */ }
}
