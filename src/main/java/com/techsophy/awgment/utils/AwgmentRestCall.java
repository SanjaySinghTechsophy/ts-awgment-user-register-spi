package com.techsophy.awgment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.techsophy.awgment.dto.UserDataPayload;
import com.techsophy.awgment.dto.UserRequestPayload;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import twitter4j.JSONObject;

import javax.json.JsonObject;
import java.util.logging.Logger;

import static com.techsophy.awgment.utils.PropertyConstant.ADD_USER_ENDPOINT;
import static com.techsophy.awgment.utils.PropertyConstant.API_SIGNATURE_KEY;

public class AwgmentRestCall {
    private static final Logger log = Logger.getLogger("AwgmentRestCall");
    public static String addUserToAwgment(String eventType,
                                        String userName,
                                        String firstName,
                                        String lastName,
                                        String mobileNo,
                                        String userId,
                                        String email,
                                        String realm,
                                        String clientId,
                                        String identityProvider) {
        try {
            log.info("addUserToAwgment 01");
            Rsa4096 rsa4096 = new Rsa4096();
            if(mobileNo.isEmpty() || mobileNo.equals(null))
            {
                mobileNo = "9892676484";
            }
            String signature = rsa4096.encryptToBase64(realm+userName);
            UserDataPayload userData = new UserDataPayload(userName, firstName, lastName, mobileNo, email, "default",signature,realm);
            UserRequestPayload payload = new UserRequestPayload(userData, realm);
            log.info("addUserToAwgment 02");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(payload);
            log.info("addUserToAwgment 03: "+json);

            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            log.info("addUserToAwgment 04"+signature);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(ADD_USER_ENDPOINT+"/?"+API_SIGNATURE_KEY+"="+signature);
            request.setEntity(entity);
//            request.setHeader("X-Tenant","tenant");
            log.info("addUserToAwgment 05"+ADD_USER_ENDPOINT+"/?"+API_SIGNATURE_KEY+"="+signature);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("addUserToAwgment 06: "+result);
                JSONObject jsonObject = new JSONObject(result);
                String id = jsonObject.getJSONObject("data").getString("userId");
                return id;
            }
            else
            {
                throw new RuntimeException("Not succeeded");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userId;
    }
}
