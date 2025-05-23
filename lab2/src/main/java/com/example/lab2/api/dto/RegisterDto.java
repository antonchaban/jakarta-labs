package com.example.lab2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private Long id;
    private String username;
    private String bio;
    private Integer age;
    private String email;
    private String password;
}
