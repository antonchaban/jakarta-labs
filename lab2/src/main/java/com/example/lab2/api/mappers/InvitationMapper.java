package com.example.lab2.api.mappers;

import com.example.lab2.api.dto.InvitationDto;
import com.example.lab2.entities.Invitation;

public class InvitationMapper {

    public static InvitationDto toDto(Invitation i) {
        return new InvitationDto(
                i.getId(),
                i.getSender().getId(),
                i.getReceiver().getId(),
                i.getAcceptStatus()
        );
    }
}
