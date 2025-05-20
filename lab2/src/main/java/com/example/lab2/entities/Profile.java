package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Profile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Embedded
    private PublicInfo publicInfo;

    @Embedded
    private PrivateInfo privateInfo;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invitation> sentInvitations = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Invitation> receivedInvitations = new HashSet<>();
}
