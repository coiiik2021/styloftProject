package org.sale.project.service.email;


import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.sale.project.dto.request.EmailRequest;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.dto.request.Sender;
import org.sale.project.repository.httpClient.EmailClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailService {

    EmailClient emailClient;

    @NonFinal
    @Value("${email.sendKey}")
    protected String KEY_API;

    public void sendEmail(SendEmailRequest sendEmailRequest) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(
                        Sender.builder()
                                .name("anhdungShop")
                                .email("dungcoi2252003@gmail.com")
                                .build()
                )
                .to(
                        List.of(sendEmailRequest.getTo())
                )
                .subject(sendEmailRequest.getSubject())
                .htmlContent(sendEmailRequest.getHtmlContent())
                .build();


        try{
            emailClient.sendEmail(KEY_API, emailRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
