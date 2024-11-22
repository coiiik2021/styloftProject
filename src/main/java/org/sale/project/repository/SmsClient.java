package org.sale.project.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(name = "sms_client", url="https://9kv4mv.api.infobip.com")
public interface SmsClient {

    @PostMapping(value = "/sms/2/text/advanced", produces = MediaType.APPLICATION_JSON_VALUE)
    void sendSms();

}
