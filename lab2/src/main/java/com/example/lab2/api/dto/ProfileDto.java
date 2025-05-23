package com.example.lab2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private Integer age;

    @JsonbTransient
    private String password;
}