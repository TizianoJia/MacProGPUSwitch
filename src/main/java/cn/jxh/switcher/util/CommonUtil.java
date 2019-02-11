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

import java.io.IOException;
import java.util.Properties;

/**
 * 通用工具类
 *
 * @author JiaXiaohei
 */
public class CommonUtil {

    /**
     * 同步MAVEN版本号
     *
     * @return 版本号
     */
    public static String getAppVersion() {
        String appVersion = null;
        Properties properties = new Properties();
        try {
            properties.load(CommonUtil.class.getClassLoader().getResourceAsStream("app.properties"));
            if (!properties.isEmpty()) {
                appVersion = properties.getProperty("app.version");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appVersion;
    }

}
