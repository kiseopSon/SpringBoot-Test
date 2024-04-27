package com.example.spring3_0security.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    private String email;
    private String password;
}
