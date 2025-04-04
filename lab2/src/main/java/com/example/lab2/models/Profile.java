package com.example.lab2.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter @AllArgsConstructor
public class Profile {
    private Long id;
    private String username;
    private PublicInfo publicInfo;
    private PrivateInfo privateInfo;
    private HashMap<Long, Invitation> receivedInvitations;
    private HashMap<Long, Invitation> sentInvitations;
}
