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

package com.malakhv.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains some things to work with files.
 * @author Mikhail.Malakhov
 * */
@SuppressWarnings("unused")
public final class FileUtils {

    /** The tag for LogCat. */
    private static final String TAG = FileUtils.class.getSimpleName();

    /** The default buffer size for stream operations. */
    static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Copy {@code in} stream to {@code out} stream.
     * */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        if (in == null || out == null) return;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * Removes specified {@code file}. If specified {@code file} is a directory, all files into
     * it will be removed.
     * */
    public static void deleteAll(File file) {
        if (file == null || !file.exists()) return;
        if (file.isDirectory()) {
            File[] all = file.listFiles();
            if (all == null) return;
            for (File f : all) deleteAll(f);
        }
        delete(file);
    }

    /**
     * Just wrapper on {@link File#delete()} with additional logs.
     * */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean delete(File file) {
        return file != null && file.exists() && file.delete();
    }

    /**
     * @return The list of files matches by {@code filter} from specified directory
     * and all subdirectories.
     * */
    public static List<File> listFiles(File file, FilenameFilter filter) {
        List<File> outFiles = new ArrayList<>();
        findFiles(file, filter, outFiles);
        return outFiles;
    }

    /**
     * Implements recursive search.
     * */
    private static void findFiles(File file, FilenameFilter filter, List<File> outFiles) {
        if (!file.isDirectory()) {
            if (filter != null) {
                if (filter.accept(null, file.getName())) outFiles.add(file);
            } else {
                outFiles.add(file);
            }
            return;
        }
        File[] files = file.listFiles();
        if (files == null || files.length <= 0) return;
        for (File f: files) {
            findFiles(f, filter, outFiles);
        }
    }

    /**
     * This class has only static data, not need to create instance.
     * */
    private FileUtils() { /* Empty */ }
}
