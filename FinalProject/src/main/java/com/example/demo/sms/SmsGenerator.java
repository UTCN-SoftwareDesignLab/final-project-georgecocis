package com.example.demo.sms;

import com.example.demo.user.model.dto.UserListDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SmsGenerator {

    public String build(UserListDTO userListDTO){
        StringBuilder sb = new StringBuilder();
        sb.append("Hello there, ").append(userListDTO.getName()).append("!").append("\n");
        sb.append("Your account has been created. An email containing your user information has been sent to you.");

        return sb.toString();
    }

}
