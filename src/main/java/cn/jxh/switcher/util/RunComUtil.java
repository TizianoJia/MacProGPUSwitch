/*
 * Copyright 2019. easyDebug.net
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
 */

package cn.jxh.switcher.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Shell操作类
 *
 * @author JiaXiaohei
 */
public class RunComUtil {

    public static int getGpuStatus() {
        int i = 0;
        String[] command = {"/bin/sh", "-c", "pmset -g"};

        List<String> processList = runCom(command);

        for (String process : processList) {
            if (process.startsWith("gpuswitch")) {
                i = Integer.parseInt(process.replaceAll("gpuswitch| ", ""));
            }
        }
        return i;
    }

    public static String getHardwareUUID() {
        String str = "";
        String[] command = {"/bin/sh", "-c", "system_profiler SPHardwareDataType"};

        List<String> processList = runCom(command);

        for (String process : processList) {
            if (process.startsWith("Hardware UUID:")) {
                str = process.replaceAll("Hardware UUID:| |-", "");
            }
        }
        return str;
    }

    public static void switchStatus(String password, int status) {
        String[] command = {"/bin/bash", "-c", "echo \"" + password + "\" | sudo -S " + "pmset -a GPUSwitch " + status};
        runCom(command);
    }

    public static boolean checkPassword(String password) {
        String[] command = {"/bin/bash", "-c", "echo \"" + password + "\" | sudo -S " + "whoami"};
        List<String> processList = runCom(command);
        if (processList.size() != 0 && processList.get(0).equals("root")) {
            return true;
        } else {
            return false;
        }
    }

    private static List<String> runCom(String[] command) {

        List<String> processList = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line.trim());
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processList;
    }

}
