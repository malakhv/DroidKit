/* *
 * Copyright (C) 2021 Mikhail Malakhov <malakhv@gmail.com>
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

package com.malakhv.droid;

import com.devlear.droid.BuildConfig;

/**
 * @author Mikhail.Malakhov
 */
public final class Library {

    /**
     * Returns the library's build type.
     * */
    public String getBuildType() {
        return BuildConfig.BUILD_TYPE;
    }

    /**
     * Returns the library's package name.
     * */
    public static String getPackageName() {
        return BuildConfig.LIBRARY_PACKAGE_NAME;
    }

}
