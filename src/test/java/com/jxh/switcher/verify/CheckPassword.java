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

package com.jxh.switcher.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 检测Root密码 验证
 *
 * @author JiaXiaohei
 */
public class CheckPassword {

    public static void main(String[] args) {

        // TODO Auto-generated method stub

        String password = "";

        String[] command = {"/bin/bash", "-c", "echo \"" + password + "\" | sudo -S " + "whoami"};

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

        if (processList.size() != 0 && processList.get(0).equals("root")) {
            System.out.println("Password Success");
        } else {
            System.out.println("Password Error");
        }
    }

}
