package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PrivateInfo {
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
