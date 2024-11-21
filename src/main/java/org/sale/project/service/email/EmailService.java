package org.sale.project.service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.sale.project.dto.request.EmailRequest;
import org.sale.project.dto.request.SendEmailRequest;
import org.sale.project.dto.request.Sender;
import org.sale.project.repository.httpClient.EmailClient;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {




    JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        // Tạo một MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Sử dụng MimeMessageHelper để thêm nội dung HTML
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true để kích hoạt nội dung HTML
        helper.setFrom("tancong22110306@gmail.com");

        // Gửi email
        mailSender.send(mimeMessage);
    }
//    EmailClient emailClient;

//    @NonFinal
//    @Value("${email.sendKey}")
//    protected String KEY_API;
//
//    public void sendEmail(SendEmailRequest sendEmailRequest) {
//        EmailRequest emailRequest = EmailRequest.builder()
//                .sender(
//                        Sender.builder()
//                                .name("anhdungShop")
//                                .email("dungcoi2252003@gmail.com")
//                                .build()
//                )
//                .to(
//                        List.of(sendEmailRequest.getTo())
//                )
//                .subject(sendEmailRequest.getSubject())
//                .htmlContent(sendEmailRequest.getHtmlContent())
//                .build();
//
//
//        try{
//            emailClient.sendEmail(KEY_API, emailRequest);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }


}
