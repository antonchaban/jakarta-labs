package com.example.lab2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDto implements Serializable {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Boolean acceptStatus;
}