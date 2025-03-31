package com.example.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Invitation {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Boolean acceptStatus;
}
