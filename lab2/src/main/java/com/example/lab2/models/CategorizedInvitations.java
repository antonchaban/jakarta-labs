package com.example.lab2.models;

import com.example.lab2.entities.Invitation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorizedInvitations {
    Collection<Invitation> acceptedIncoming;
    Collection<Invitation> pendingIncoming;
    Collection<Invitation> acceptedOutgoing;
    Collection<Invitation> pendingOutgoing;
}
