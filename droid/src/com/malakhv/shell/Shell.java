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

package com.malakhv.shell;

import com.malakhv.util.StreamUtils;

import java.io.DataOutputStream;

/**
 * Basic class for execute shell commands.
 *
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 * */
// TODO Need to write JavaDoc
@SuppressWarnings("unused")
public final class Shell {

    /** Tag for LogCat. */
    private static final String LOG_TAG = Shell.class.getSimpleName();

    /** Command from super user. */
    private static final String CMD_SU = "su";

    /** Command from user. */
    private static final String CMD_SH = "sh";

    /** Command: exit. */
    private static final String CMD_EXIT = "exit\n";

    /** Command end line. */
    private static final String CMD_LINE_END = "\n";

    /** Command execution failure code. */
    public static final int EXEC_CMD_FAILED = -1;

    public static final String EXEC_FAILED_CMD_EMPTY_MSG = "Execution failed, command is empty";

    /**
     * This class has only static data, not need to create instance.
     * */
    private Shell() {}

    /*----------------------------------------------------------------------------------------*/
    /* General constants
    /*----------------------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------------------------*/
    /* General static fields
    /*----------------------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------------------------*/
    /* Constructor and static initialization methods
    /*----------------------------------------------------------------------------------------*/

    /*----------------------------------------------------------------------------------------*/
    /* Execute
    /*----------------------------------------------------------------------------------------*/

    /**
     * Execute specified shell commands.
     * */
    public static Result execute(String command) {
        return execute(command, false);
    }

    /**
     * Execute specified shell commands.
     * */
    public static Result execute(String command, boolean root) {
        return execute(command, root, true);
    }

    /**
     * Execute specified shell commands.
     * */
    public static Result execute(String command, boolean root, boolean resultMsg) {
        return execute(new String[] {command}, root, resultMsg);
    }

    /**
     * Execute specified shell commands.
     * */
    public static Result execute(String[] commands, boolean root, boolean resultMsg) {
        try {
            return executeWithThrow(commands, root, resultMsg);
        } catch (Exception e) {
            return new Result(EXEC_CMD_FAILED, null, e.getMessage());
        }
    }

    /**
     * Execute specified shell commands.
     */
    private static Result executeWithThrow(String[] commands, boolean root, boolean resultMsg)
            throws Exception {
        int code = EXEC_CMD_FAILED;
        /* Проверка входных параметров */
        if (commands == null || commands.length == 0) {
            return new Result(code, null, EXEC_FAILED_CMD_EMPTY_MSG);
        }

        String successMsg = null;
        String errorMsg = null;
        Process process = null;
        DataOutputStream os = null;

        // Try to execute command
        try {
            process = Runtime.getRuntime().exec(root ? CMD_SU : CMD_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) continue;
                os.write(command.getBytes());
                os.writeBytes(CMD_LINE_END); os.flush();
            }
            os.writeBytes(CMD_EXIT); os.flush();
            code = process.waitFor();
            if (resultMsg) {
                successMsg = StreamUtils.toString(process.getInputStream(), true);
                errorMsg = StreamUtils.toString(process.getErrorStream(), true);
            }
        } finally {
            if (os != null) os.close();
            if (process != null) process.destroy();
        }
        return new Result(code, successMsg, errorMsg);
    }

    /*----------------------------------------------------------------------------------------*/
    /* Command result
    /*----------------------------------------------------------------------------------------*/

    /**
     * Class describe result of execution shell command.
     *
     * @author Mikhail.Malakhov [malakhv@live.ru|https://github.com/malakhv]
     * */
    public static class Result {

        /** The command. */
        public final String command;

        /** The result code. */
        public final int code;

        /** The success message. */
        public final String successMsg;

        /** The error message. */
        public final String errorMsg;

        /**
         * Create {@link Result} instance with specified values.
         * */
        public Result(String cmd, int code) { this(cmd, code, null, null); }

        /**
         * Создает объект {@link Result} с заданными
         * параметрами.
         *
         * @param code код результата выполнения команд
         * @param successMsg сообщения команд
         * @param errorMsg сообщения об ошибках
         *
         * */
        public Result(int code, String successMsg, String errorMsg) {
            this(null, code, successMsg, errorMsg);
        }

        /**
         * Create {@link Result} instance with specified values.
         * */
        public Result(String cmd, int code, String successMsg, String errorMsg) {
            this.command = cmd; this.code = code;
            this.successMsg = successMsg; this.errorMsg = errorMsg;
        }

        /**
         * Returns {@code true} if success message exists.
         * */
        public boolean hasSuccessMsg() { return successMsg != null; }

        /**
         * Returns {@code true} if error message exists.
         * */
        public boolean hasErrorMsg() { return errorMsg != null; }

        /**
         * Check success message.
         * */
        public boolean isSuccess() {
            return hasSuccessMsg() &&
                    (successMsg.contains("Success") || successMsg.contains("success"));
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public int hashCode() {
            return 31 * code + (successMsg != null ? successMsg.hashCode() : 0) +
                    (errorMsg != null ? errorMsg.hashCode() : 0);
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public String toString() {
            return "Result code: " + code + "\n" + "Success Msg: " +
                    (successMsg != null ? successMsg : "") + "\n" + "Error Msg: " +
                    (errorMsg != null ? errorMsg : "");
        }
    }
}
