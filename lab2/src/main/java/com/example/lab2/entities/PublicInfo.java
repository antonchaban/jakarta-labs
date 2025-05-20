package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PublicInfo {
    @Column(length = 500)
    private String bio;

    private Integer age;
}
