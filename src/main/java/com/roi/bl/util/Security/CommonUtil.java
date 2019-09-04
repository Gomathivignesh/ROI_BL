package com.roi.bl.util.Security;

import java.util.Base64;

public class CommonUtil {

    public static String encryptData(String input){
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decryptData(String encrypted){
        byte[] actualByte= Base64.getDecoder().decode(encrypted);
        return new String(actualByte);
    }
}
