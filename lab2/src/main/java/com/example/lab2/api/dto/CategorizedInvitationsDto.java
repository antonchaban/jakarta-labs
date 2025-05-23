package com.example.lab2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorizedInvitationsDto {
    private List<InvitationDto> acceptedIncoming;
    private List<InvitationDto> pendingIncoming;
    private List<InvitationDto> acceptedOutgoing;
    private List<InvitationDto> pendingOutgoing;
}