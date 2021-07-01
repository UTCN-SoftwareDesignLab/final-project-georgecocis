package com.example.demo.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SmsService {
    private final String SID = "ACc67f598d2d26f16813fc5e8a764f718e";
    private final String authToken = "a786d0877a022729e79a4ec815c34b4b";
    private final String sender = "+12182766942";

    public void sendSms(SmsModel sms){
        Twilio.init(SID, authToken);

        Message message = Message.creator(new PhoneNumber(sms.getRecipient()), new PhoneNumber(sender), sms.getMessage())
                .create();
    }
}
