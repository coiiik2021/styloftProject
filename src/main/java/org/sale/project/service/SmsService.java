package org.sale.project.service;

import java.util.List;
import com.infobip.ApiClient;
import com.infobip.ApiException;
import com.infobip.ApiKey;
import com.infobip.BaseUrl;
import com.infobip.api.SmsApi;
import com.infobip.model.SmsAdvancedTextualRequest;
import com.infobip.model.SmsDestination;
import com.infobip.model.SmsTextualMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.sale.project.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SmsService {

    @Value("${sms.api_key}")
    private String api_key;
    @Value("${sms.base_url}")
    private String base_url;

    public void sendSms(User user){
        var client = ApiClient.forApiKey(ApiKey.from(api_key))
                .withBaseUrl(BaseUrl.from(base_url))
                .build();

        var api = new SmsApi(client);

        var message = new SmsTextualMessage()
                .from("InfoSMS")

                .destinations(List.of(new SmsDestination().to(user.getPhoneNumber())))
                .text("Hello World from infobip-api-java-client!");

        var request = new SmsAdvancedTextualRequest()
                .messages(List.of(message));

        try {
            var response = api.sendSmsMessage(request).execute();
            System.out.println(response);
        } catch (ApiException exception) {
            System.out.printf("Received status %d when calling the Infobip API.%n", exception.responseStatusCode());
            if (exception.details() != null) {
                System.out.printf("Error details: %s%n", exception.details().getText());
            }
        }
    }
}
