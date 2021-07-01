package com.example.demo.user.controller;

import com.example.demo.mail.MailGenerator;
import com.example.demo.mail.MailService;
import com.example.demo.sms.SmsGenerator;
import com.example.demo.sms.SmsModel;
import com.example.demo.sms.SmsService;
import com.example.demo.user.model.dto.UserListDTO;
import com.example.demo.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {
    private final String defaultRecipient = "+40745045359";
    private final String defaultMailRecipient = "ccsgeorge001@gmail.com";
    private final String defaultMailSubject = "Account details";
    private final UserService userService;
    private final SmsService smsService;
    private final SmsGenerator smsGenerator;
    private final MailService mailService;
    private final MailGenerator mailGenerator;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO createUser(@RequestBody UserListDTO userListDTO) {
        SmsModel sms = SmsModel.builder()
                .recipient(defaultRecipient)
                .message(smsGenerator.build(userListDTO)).build();
        smsService.sendSms(sms);

        mailService.sendSimpleMessage(defaultMailRecipient, defaultMailSubject, mailGenerator.build(userListDTO));
        return userService.create(userListDTO);
    }

    @PatchMapping(USER_ENTITY)
    public UserListDTO updateUser(@PathVariable Long userId, @RequestBody UserListDTO userListDTO) {
        return userService.updateUser(userId, userListDTO);
    }

    @DeleteMapping(USER_ENTITY)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping(USER_ENTITY)
    public UserListDTO getUser(@PathVariable Long userId) {
        return userService.get(userId);
    }
}
