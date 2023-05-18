package com.techsophy.awgment.utils;

public class PropertyConstant {
    private PropertyConstant() {
    }

    public static final String ADD_USER_ENDPOINT = "http://account-app:8080/internal/v1/users/create";
    public static final String API_SIGNATURE_KEY = "X-Signature";
    public static final String ALGORITHM = "NONEwithRSA";
    public static final String PRIVATE_KEY_FILE = "keys/private_key_rsa_4096_pkcs8-generated.pem";
    public static final String KEY_FACTORY = "RSA";
    public static final String KEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    public static final String KEY_SUFFIX = "-----END PRIVATE KEY-----";
}
