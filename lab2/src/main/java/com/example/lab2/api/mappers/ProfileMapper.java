package com.example.lab2.api.mappers;

import com.example.lab2.api.dto.ProfileDto;
import com.example.lab2.entities.PrivateInfo;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.PublicInfo;

public class ProfileMapper {
    public static ProfileDto toDto(Profile p) {
        ProfileDto dto = new ProfileDto();
        dto.setId(p.getId());
        dto.setUsername(p.getUsername());
        dto.setBio(p.getPublicInfo().getBio());
        dto.setAge(p.getPublicInfo().getAge());
        dto.setEmail(p.getPrivateInfo().getEmail());
        return dto;
    }

    public static Profile fromDto(ProfileDto dto) {
        Profile p = new Profile();
        p.setUsername(dto.getUsername());
        p.setPublicInfo(new PublicInfo(dto.getBio(), dto.getAge()));
        p.setPrivateInfo(new PrivateInfo(dto.getEmail(), dto.getPassword() == null ? "" : dto.getPassword()));
        return p;
    }
}
