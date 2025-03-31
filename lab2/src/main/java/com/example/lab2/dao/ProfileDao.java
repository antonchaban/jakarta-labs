package com.example.lab2.dao;

import com.example.lab2.models.Invitation;
import com.example.lab2.models.Profile;

import java.util.Collection;

public interface ProfileDao extends AbstractDao<Profile> {
    Profile findByUsername(String username);
    Profile findById(Long id);
    Collection<Profile> findByText(String string);

    void newProfile(Profile profile);
    void addInvitation(Profile profile, Invitation invitation);
    void deleteInvitation(Profile profile, Invitation invitation);
}
