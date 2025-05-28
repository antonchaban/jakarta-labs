package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;


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

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invitation> sentInvitations = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invitation> receivedInvitations = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void hashPassword() {
        if (privateInfo != null) {
            String hashedPassword = BCrypt.hashpw(privateInfo.getPassword(), BCrypt.gensalt(12));
            privateInfo.setPassword(hashedPassword);
        }
    }

}
