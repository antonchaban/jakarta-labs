package com.example.lab2.services.inmem;

import com.example.lab2.models.inmem.Invitation;
import com.example.lab2.models.inmem.Profile;

import java.util.Collection;

public interface ProfileService {
    Profile getByLogin(String login);
    Profile getById(Long profileId);
    Collection<Profile> getAllProfiles();
    boolean checkPass(Profile profile, String password);
    boolean checkProfile(Profile profile);
    void newProfile(Profile profile);
    void updateProfile(Profile profile);
    Collection<Invitation> getSentInvitations(Profile profile);
    Collection<Invitation> getReceivedInvitations(Profile profile);
    Collection<Profile> findByText(String string);
    void addInvitation(Profile sender, Profile receiver, Invitation invitation);
    void deleteInvitation(Profile sender, Profile receiver, Invitation invitation);
    void acceptInvitation(Profile sender, Profile receiver, Invitation invitation);
}
