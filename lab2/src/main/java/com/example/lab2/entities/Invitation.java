package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invitations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Invitation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "sender_id", nullable = false)
    private Profile sender;

    @ManyToOne @JoinColumn(name = "receiver_id", nullable = false)
    private Profile receiver;

    @Column(name = "accept_status")
    private Boolean acceptStatus;
}
