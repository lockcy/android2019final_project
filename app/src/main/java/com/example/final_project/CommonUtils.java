package com.example.final_project;

import java.security.MessageDigest;
import java.math.BigInteger;
/**
 * Created by lockcy
 */
public class CommonUtils {


    public static String hash(String algo, String data, int hex) {

        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            return new BigInteger(1, md.digest(data.getBytes())).toString(hex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

