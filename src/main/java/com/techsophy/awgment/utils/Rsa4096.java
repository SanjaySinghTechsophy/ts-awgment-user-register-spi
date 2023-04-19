package com.techsophy.awgment.utils;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static com.techsophy.awgment.utils.PropertyConstant.PRIVATE_KEY_FILE;

public class Rsa4096 {
    private KeyFactory keyFactory;
    private PrivateKey privateKey;

    public Rsa4096() throws Exception {
        setKeyFactory();
        setPrivateKey(PRIVATE_KEY_FILE);
    }

    protected void setKeyFactory() throws Exception {
        this.keyFactory = KeyFactory.getInstance("RSA");
    }

    protected void setPrivateKey(String classpathResource)
            throws Exception {

        InputStream is = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(classpathResource);

        String stringBefore
                = new String(is.readAllBytes());
        is.close();
        String stringAfter = stringBefore
                .replaceAll("\\n", "")
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .trim();

        byte[] decoded = Base64
                .getDecoder()
                .decode(stringAfter);
        KeySpec keySpec
                = new PKCS8EncodedKeySpec(decoded);
        privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String encryptToBase64(String plainText) {
        String encoded = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            encoded = Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoded;
    }
}


