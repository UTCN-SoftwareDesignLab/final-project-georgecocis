package com.example.demo.sms;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsModel {
    private String message;
    private String recipient;
}
