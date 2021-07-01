package com.example.demo.mail;

import com.example.demo.user.model.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MailGenerator {

    public String build(UserListDTO userListDTO){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(userListDTO.getName()).append("\n")
                .append("Role: ").append("EMPLOYEE").append("\n");
        sb.append("Thank you for being part of our team!");
        return sb.toString();
    }

}
