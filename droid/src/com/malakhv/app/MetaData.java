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

package com.malakhv.app;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

/**
 * Class contains methods to retrieving meta data from application components.
 * 
 * @author Mikhail Malakhov
 * */
@SuppressWarnings("unused")
public final class MetaData {

    /** Tag for LogCat. */
    private static final String LOG_TAG = "MetaData";

    /** Not needed to create instance of this class. */
    private MetaData() {}

    /**
     * Returns meta data from {@link ActivityInfo} as {@link Bundle} object.
     *
     * @param activity The application component for retrieving meta data.
     * */
    public static Bundle getExtras(Activity activity) {
        try {
            final ActivityInfo ai = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(),PackageManager.GET_META_DATA);
            return ai != null ? ai.metaData : null;
        } catch (NameNotFoundException e) {
            Log.v(LOG_TAG, "Cannot get meta data: " + activity.getComponentName().toString());
            return null;
        }
    }

    /**
     * Returns the value from meta data associated with the given key, or defaultValue if no
     * mapping of the desired type exists for the given key.
     * 
     * @param activity The application component for retrieving meta data.
     * @param key The key for retrieving data
     * @param defaultValue The default value
     *
     * @return The String value associated with the given key, or defaultValue if no valid String
     * object is currently mapped to that key.
     * */
    public static String getString(Activity activity, String key, String defaultValue) {
        final Bundle extras = MetaData.getExtras(activity);
        if (extras != null) {
            return extras.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * Returns the value from metadata associated with the given key as {@code long} value, or
     * {@code defaultValue} if no mapping of the desired type exists for the given key.
     * */
    public static long getLong(Activity activity, String key, long defaultValue) {
        final Bundle extras = MetaData.getExtras(activity);
        if (extras != null) {
            return extras.getLong(key,defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * Returns the value from metadata associated with the given key as {@code int} value, or
     * {@code defaultValue} if no mapping of the desired type exists for the given key.
     * */
    public static long getInt(Activity activity, String key, int defaultValue) {
        final Bundle extras = MetaData.getExtras(activity);
        if (extras != null) {
            return extras.getInt(key,defaultValue);
        } else {
            return defaultValue;
        }
    }

    /**
     * Returns the value from meta data associated with the given key, or null if no
     * mapping of the desired type exists for the given key.
     * 
     * @param activity The application component for retrieving meta data.
     * @param key The key for retrieving data
     *
     * @return The String value associated with the given key, or null if no valid String
     * object is currently mapped to that key.
     * */
    public static String getString(Activity activity, String key) {
        return MetaData.getString(activity, key, null);
    }
}