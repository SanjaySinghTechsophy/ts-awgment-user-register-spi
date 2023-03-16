package com.techsophy.awgment.utils;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.techsophy.awgment.utils.PropertyConstant.PUBLIC_KEY_FILE;
import static com.techsophy.awgment.utils.PropertyConstant.KEY_FACTORY;
import static com.techsophy.awgment.utils.PropertyConstant.KEY_PREFIX;
import static com.techsophy.awgment.utils.PropertyConstant.KEY_SUFFIX;

public class Rsa4096 {

    private KeyFactory keyFactory;
    private PublicKey publicKey;

    public Rsa4096() throws Exception {
        setKeyFactory();
        setPublicKey(PUBLIC_KEY_FILE);
    }

    protected void setKeyFactory() throws Exception {
        this.keyFactory = KeyFactory.getInstance(KEY_FACTORY);
    }

    protected void setPublicKey(String classpathResource)
            throws Exception {
        InputStream is = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(classpathResource);

        String stringBefore = new String(is.readAllBytes());
        is.close();

        String stringAfter = stringBefore
                .replaceAll("\\n", "")
                .replaceAll(KEY_PREFIX, "")
                .replaceAll(KEY_SUFFIX, "")
                .trim()
                ;

        byte[] decoded = Base64
                .getDecoder()
                .decode(stringAfter);

        KeySpec keySpec
                = new X509EncodedKeySpec(decoded);

        publicKey = keyFactory.generatePublic(keySpec);
    }


    public String encryptToBase64(String plainText) {
        String encoded = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            encoded = Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }
}


