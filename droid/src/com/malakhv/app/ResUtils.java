/* *
 * Copyright (C) 1996-2023 Mikhail Malakhov <malakhv@gmail.com>
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

package com.malakhv.app;

import android.content.Context;
import android.content.res.Resources;

import com.malakhv.util.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class contains some things to work with app resources.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class ResUtils {

    /**
     * Copies raw resources to file.
     * @param res The app resources.
     * @param rawId The raw resource id.
     * @param path The full file path to copy raw resource.
     *
     * @throws IOException When any problem with file operations happens.
     * */
    public static void copyRawToFile(Resources res, int rawId, String path) throws IOException {
        try (InputStream in = res.openRawResource(rawId);
             FileOutputStream out = new FileOutputStream(path)) {
            FileUtils.copy(in, out);
        }
    }

    /**
     * Copies raw resources to file.
     * @param res The app resources.
     * @param rawId The raw resource id.
     * @param path The file to copy raw resource.
     *
     * @throws IOException When any problem with file operations happens.
     * */
    public static void copyRawToFile(Resources res, int rawId, File path) throws IOException {
        if (path != null) {
            copyRawToFile(res, rawId, path.getAbsolutePath());
        }
    }

    /**
     * Return a resource identifier for the given resource name.
     * @param res The app resources.
     * @param pkg The package to find resource.
     * @param type The resource type to find.
     * @param name The name of the desired resource.
     *
     * @return The associated resource identifier. Returns {@code 0} if no such
     * resource was found ({@code 0} is not a valid resource ID).
     * */
    public static int getId(Resources res, String pkg, String type, String name) {
        return res.getIdentifier(name, type, pkg);
    }

    /**
     * Return a resource identifier for the given resource name.
     * @param context The app context to accesses to the resources.
     * @param type The resource type to find.
     * @param name The name of the desired resource.
     *
     * @return The associated resource identifier. Returns {@code 0} if no such
     * resource was found ({@code 0} is not a valid resource ID).
     * */
    public static int getId(Context context, String type, String name) {
        return getId(context.getResources(), context.getPackageName(), type, name);
    }

    /**
     * This class has only static data, not need to create instance.
     * */
    private ResUtils() { /* Empty */ }
}
