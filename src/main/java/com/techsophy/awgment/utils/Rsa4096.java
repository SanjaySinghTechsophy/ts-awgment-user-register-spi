package com.techsophy.awgment.utils;

import java.io.InputStream;
import java.security.*;
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

    public String generateSignature(String value) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("NONEwithRSA");
        sign.initSign(privateKey);
        byte[] bytes = value.getBytes();
        sign.update(bytes);
        return Base64.getEncoder().encodeToString(sign.sign());
    }
}


