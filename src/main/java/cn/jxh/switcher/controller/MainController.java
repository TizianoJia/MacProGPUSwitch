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

package cn.jxh.switcher.controller;

import cn.jxh.switcher.constant.AppConstant;
import cn.jxh.switcher.util.CommonUtil;
import cn.jxh.switcher.util.RunComUtil;
import cn.jxh.switcher.util.SavePwdUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 主界面方法
 *
 * @author JiaXiaohei
 */
public class MainController implements Initializable {

    @FXML
    private Label statusText;

    @FXML
    private PasswordField rootPassword;

    @FXML
    private CheckBox rememberPassword;

    @FXML
    private ToggleGroup switchType;

    @FXML
    private RadioButton radioButton0;

    @FXML
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStatus();
        showSavePwd();
    }

    @FXML
    private void applyApp() {
        Toggle target = switchType.getSelectedToggle();
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (null == target) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("未选择切换模式");
        } else if (!RunComUtil.checkPassword(rootPassword.getText())) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("Root密码错误");
        } else {
            try {
                RunComUtil.switchStatus(rootPassword.getText(), Integer.parseInt((String) target.getUserData()));
                SavePwdUtil.savePwd(rememberPassword.isSelected(), rootPassword.getText());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("完成");
                alert.setHeaderText("操作完成");
            } catch (Exception e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("错误");
                alert.setHeaderText(e.getLocalizedMessage());
            }
        }
        showStatus();
        alert.showAndWait();
    }

    private void showStatus() {
        switch (RunComUtil.getGpuStatus()) {
            case 0:
                statusText.setText("整合");
                statusText.setTextFill(Color.web("#008080"));
                radioButton0.fire();
                break;
            case 1:
                statusText.setText("高性能");
                statusText.setTextFill(Color.web("#ff895c"));
                radioButton1.fire();
                break;
            case 2:
                statusText.setText("自动");
                statusText.setTextFill(Color.web("#0094cc"));
                radioButton2.fire();
                break;
            default:
                statusText.setText("错误");
                statusText.setTextFill(Color.web("#c04000"));
                break;
        }
    }

    private void showSavePwd() {
        Properties prop = new Properties();
        try {
            if (new File(AppConstant.CONFIG_FILE_NAME).exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream(AppConstant.CONFIG_FILE_NAME));
                prop.load(in);
                if (Integer.parseInt(prop.getProperty(AppConstant.CONFIG_PWD_SAVE)) == 1) {
                    rememberPassword.setSelected(true);
                    rootPassword.setText(SavePwdUtil.decrypt(prop.getProperty(AppConstant.CONFIG_PWD_CNT), RunComUtil.getHardwareUUID()));
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void aboutApp() {
        String version = CommonUtil.getAppVersion();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于");
        alert.setHeaderText("MacPro GPU Switch" + "\n" + version);
        alert.setContentText("Author:JiaXiaohei\n\nLicensed under the Apache License 2.0\nhttps://github.com/JiaXiaohei/MacProGPUSwitch");
        alert.showAndWait();
    }

    @FXML
    private void exitApp() {
        Platform.exit();
    }


}
