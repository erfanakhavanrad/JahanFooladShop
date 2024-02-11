package com.jahanfoolad.jfs.security;

/**
 * Created by Reza Radan on Feb , 2021
 */

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
public class EncyDecyECB {

    private static final String ALGO = "AES";
    private static final String SECRET = "cmV6YWJhZGFuMTIzNDU2Nw==";

    public static String encrypt(String Data) {
        try {
            Key key = generateKey(SECRET);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(Data.getBytes());
            String encryptedValue = Base64.getEncoder().encodeToString(encVal);
            return encryptedValue;
        } catch (Exception e) {
            return e.toString();
        }

    }


    public static String decrypt(String strToDecrypt) {
        try {
            Key key = generateKey(SECRET);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
            return e.toString();
        }
    }

    private static Key generateKey(String secret) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        Key key = new SecretKeySpec(decoded, ALGO);
        return key;
    }

    public static String decodeKey(String str) {
        byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encodeKey(String str) {
        byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }

}
