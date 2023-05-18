package com.techsophy.awgment.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static com.techsophy.awgment.utils.PropertyConstant.*;

public class Rsa4096 {
    private KeyFactory keyFactory;
    private PrivateKey privateKey;

    public Rsa4096() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        setKeyFactory();
        setPrivateKey(PRIVATE_KEY_FILE);
    }

    protected void setKeyFactory() throws NoSuchAlgorithmException {
        this.keyFactory = KeyFactory.getInstance(KEY_FACTORY);
    }

    protected void setPrivateKey(String classpathResource)
            throws IOException, InvalidKeySpecException {

        InputStream is = this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(classpathResource);

        String stringBefore
                = new String(is.readAllBytes());
        is.close();
        String stringAfter = stringBefore
                .replace("\\n", "")
                .replace(KEY_PREFIX, "")
                .replace(KEY_SUFFIX, "")
                .trim();

        byte[] decoded = Base64
                .getDecoder()
                .decode(stringAfter);
        KeySpec keySpec
                = new PKCS8EncodedKeySpec(decoded);
        privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String generateSignature(String value) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance(ALGORITHM);
        sign.initSign(privateKey);
        byte[] bytes = value.getBytes();
        sign.update(bytes);
        return Base64.getEncoder().encodeToString(sign.sign());
    }
}


