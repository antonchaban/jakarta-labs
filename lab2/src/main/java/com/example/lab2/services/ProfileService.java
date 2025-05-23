package com.example.lab2.services;

import com.example.lab2.entities.Invitation;
import com.example.lab2.entities.Profile;
import com.example.lab2.models.CategorizedInvitations;

import java.util.Collection;

public interface ProfileService {
    Profile getByLogin(String login);
    Profile getById(Long profileId);
    Collection<Profile> getAllProfiles();
    boolean checkPass(Profile profile, String password);
    boolean checkProfile(Profile profile);
    void newProfile(Profile profile);
    Profile register(String username, String password, String email, String bio, Integer age);
    void updateProfile(Profile profile);
    void deleteProfile(Profile profile);
    Collection<Invitation> getSentInvitations(Profile profile);
    Collection<Invitation> getReceivedInvitations(Profile profile);
    CategorizedInvitations getCategorizedInvitation(Profile profile);
    Collection<Profile> findByText(String string);
    void addInvitation(Profile sender, Profile receiver, Invitation invitation);
    void sendInvitation(Profile sender, Profile receiver);
    void deleteInvitation(Profile sender, Profile receiver, Invitation invitation);
    void acceptInvitation(Profile sender, Profile receiver, Invitation invitation);
    void acceptInvitationFromUser(Profile receiver, Long senderId);
    void deleteUserInvitation (Profile user, Long invitationId);
}
