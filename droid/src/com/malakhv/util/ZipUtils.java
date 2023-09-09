/* *
 * Copyright (C) 1996-2018 Mikhail Malakhov <malakhv@gmail.com>
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

import static com.malakhv.util.FileUtils.DEFAULT_BUFFER_SIZE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class contains some things to work with zip archives.
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
@SuppressWarnings({"unused"})
public final class ZipUtils {

    /** The tag for LogCat. */
    private static final String TAG = ZipUtils.class.getSimpleName();

    /**
     * Unzips specified file to specified path. If zip file has root dir, it will be
     * unzipped as {@code unzipPath/zip_root_dir/*}.
     * */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void unzipTo(File zipFile, File unzipPath) throws IOException {

        // Checking zipFile
        if (!zipFile.exists()) {
            throw new FileNotFoundException("The input zip file not found.");
        }
        if (!zipFile.canRead()) {
            throw new IOException("Cannot read input zip file.");
        }

        // Checking unzip path
        if (!unzipPath.canWrite()) {
            throw new IOException("Cannot write to output zip file.");
        }

        // Unzipping file to specified path
        final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while(zipEntry != null) {
            final File f = new File(unzipPath, zipEntry.getName());
            if (!zipEntry.isDirectory()) {
                // Sometimes, we should unzip files in media dir before media dir entry. WTF???
                FileOutputStream fos = new FileOutputStream(f);
                while ((read = zipInputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, read);
                }
                fos.close();
            } else {
                f.mkdirs();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.closeEntry();
        zipInputStream.close();
    }

    /**
     * Unzips specified file to specified path. If zip file has root dir, it will be
     * unzipped as {@code unzipPath/zip_root_dir/*}.
     * */
    public static void unzipTo(String  zipFile, String  unzipPath) throws IOException {
        final File zf = new File(zipFile);
        final File up = new File(unzipPath);
        unzipTo(zf, up);
    }

    /**
     * This class has only static data, not need to create instance.
     * */
    private ZipUtils() { /* Empty */ }
}
