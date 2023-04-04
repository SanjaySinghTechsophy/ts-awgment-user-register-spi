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
import java.util.logging.Logger;

import static com.techsophy.awgment.utils.PropertyConstant.ADD_USER_ENDPOINT;
import static com.techsophy.awgment.utils.PropertyConstant.API_SIGNATURE_KEY;

public class AwgmentRestCall {
    private static final Logger log = Logger.getLogger("AwgmentRestCall");
    public static void addUserToAwgment(String eventType,
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
            UserDataPayload userData = new UserDataPayload(userName, firstName, lastName, mobileNo, email, "default");
            UserRequestPayload payload = new UserRequestPayload(userData, realm);
            log.info("addUserToAwgment 02");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(payload);
            log.info("addUserToAwgment 03: "+json);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            Rsa4096 rsa4096 = new Rsa4096();
            String signature = rsa4096.encryptToBase64(realm+userName);
            log.info("addUserToAwgment 04"+signature);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(ADD_USER_ENDPOINT+"/?"+API_SIGNATURE_KEY+"="+signature);
            request.setEntity(entity);
            log.info("addUserToAwgment 05"+ADD_USER_ENDPOINT+"/?"+API_SIGNATURE_KEY+"="+signature);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
