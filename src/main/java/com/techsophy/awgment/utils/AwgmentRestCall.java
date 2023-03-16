package com.techsophy.awgment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
            String url = ADD_USER_ENDPOINT+eventType+"/"+realm+"/"+clientId+"/"+identityProvider;
            UserRequestPayload payload = new UserRequestPayload(userName, firstName, lastName, mobileNo, email, "default", userId);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(payload);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            Rsa4096 rsa4096 = new Rsa4096();
            request.setHeader(API_SIGNATURE_KEY, rsa4096.encryptToBase64(userId));
            request.setEntity(entity);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
        } catch (Exception ex) {
            // Handle errors
        }
    }
}
