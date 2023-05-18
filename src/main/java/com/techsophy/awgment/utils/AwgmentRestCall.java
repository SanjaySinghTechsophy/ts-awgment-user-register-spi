package com.techsophy.awgment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.techsophy.awgment.dto.UserDataPayload;
import com.techsophy.awgment.dto.UserRequestPayload;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.Random;

import static com.techsophy.awgment.utils.PropertyConstant.ADD_USER_ENDPOINT;
import static com.techsophy.awgment.utils.PropertyConstant.API_SIGNATURE_KEY;

public class AwgmentRestCall {
    private AwgmentRestCall() {
    }

    public static BigInteger addUserToAwgment(String userName,
                                        String firstName,
                                        String lastName,
                                        String mobileNo,
                                        String email,
                                        String realm) {
        BigInteger userIdGenerated =BigInteger.ZERO;
        try {
            userIdGenerated =  idGenerator();
            Rsa4096 rsa4096 = new Rsa4096();
            if(mobileNo == null || mobileNo.isEmpty())
                mobileNo = "9892676484";
            String signature = rsa4096.generateSignature(userName);
            UserDataPayload userData = new UserDataPayload(userName, firstName, lastName, mobileNo, email, "default");
            UserRequestPayload payload = new UserRequestPayload(userData, realm);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(payload);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(ADD_USER_ENDPOINT);
            request.setHeader(API_SIGNATURE_KEY, signature);
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(result);
                return new BigInteger(jsonObject.getJSONObject("data").getString("userId"));

            } else {
                throw new HttpResponseException(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Request failed due to Server error");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userIdGenerated;
    }

    public static BigInteger idGenerator() {
        BigInteger maxLimit = BigInteger.valueOf(5000000000000L);
        BigInteger minLimit = BigInteger.valueOf(1000000000000L);
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        return res;
    }
}
