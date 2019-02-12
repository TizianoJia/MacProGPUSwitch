package cn.jxh.switcher.util;

import cn.jxh.switcher.constant.AppConstant;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;

import java.util.Properties;


public class SavePwdUtil {

    private static final String KEY_ALGORITHM = "AES";

    private static final String KEY_METHOD = "AES/ECB/PKCS5Padding";

    public static boolean savePwd(Boolean save, String pwd) {
        boolean isSuccess = false;
        if (save) {
            Properties prop = new Properties();
            try {
                FileOutputStream oFile = new FileOutputStream(AppConstant.CONFIG_FILE_NAME, false);
                prop.setProperty(AppConstant.CONFIG_PWD_SAVE, "1");
                prop.setProperty(AppConstant.CONFIG_PWD_CNT, encrypt(pwd,RunComUtil.getHardwareUUID()));
                prop.store(oFile, null);
                oFile.close();
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Properties prop = new Properties();
            try {
                FileOutputStream oFile = new FileOutputStream(AppConstant.CONFIG_FILE_NAME, false);
                prop.setProperty(AppConstant.CONFIG_PWD_SAVE, "0");
                prop.setProperty(AppConstant.CONFIG_PWD_CNT, "");
                prop.store(oFile, null);
                oFile.close();
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_METHOD);
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64URLSafe(crypted));
    }

    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(KEY_METHOD);
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decodeBase64(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(output);
    }

}
