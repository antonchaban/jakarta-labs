package com.example.lab2.services;

import com.example.lab2.dao.DaoFactory;
import com.example.lab2.entities.Invitation;
import com.example.lab2.entities.PrivateInfo;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.PublicInfo;
import com.example.lab2.models.CategorizedInvitations;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.NotFoundException;
import org.mindrot.jbcrypt.BCrypt;


import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Stateless
@Local(ProfileService.class)
public class ProfileServiceImpl implements ProfileService{
    @EJB
    DaoFactory daoFactory;

    @Override
    public Profile getByLogin(String login) {
        return daoFactory.getProfileDao().findByUsername(login);
    }

    @Override
    public Profile getById(Long profileId) {
        return daoFactory.getProfileDao().findById(profileId);
    }

    @Override
    public Collection<Profile> getAllProfiles() {
        return daoFactory.getProfileDao().findAll();
    }

    @Override
    public boolean checkPass(Profile profile, String password) {
        String hashed = profile.getPrivateInfo().getPassword();
        return BCrypt.checkpw(password, hashed);
    }

    @Override
    public boolean checkProfile(Profile profile) {
        return daoFactory.getProfileDao().findByUsername(profile.getUsername()) != null;
    }

    @Override
    public void newProfile(Profile profile) {
        daoFactory.getProfileDao().newProfile(profile);
    }

    @Override
    public Profile register(String username, String password, String email, String bio, Integer age) {
        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setPublicInfo(new PublicInfo(bio, age));
        profile.setPrivateInfo(new PrivateInfo(email, password));

        newProfile(profile);
        return profile;
    }

    @Override
    public void updateProfile(Profile profile) {
        daoFactory.getProfileDao().update(profile);
    }

    @Override
    public Collection<Invitation> getSentInvitations(Profile profile) {
        return daoFactory.getProfileDao().findById(profile.getId()).getSentInvitations();
    }

    @Override
    public Collection<Invitation> getReceivedInvitations(Profile profile) {
        return daoFactory.getProfileDao().findById(profile.getId()).getReceivedInvitations();
    }

    @Override
    public CategorizedInvitations getCategorizedInvitation(Profile profile) {
        Collection<Invitation> allIncoming = getReceivedInvitations(profile);
        Collection<Invitation> allOutgoing = getSentInvitations(profile);

        List<Invitation> acceptedIncoming = allIncoming.stream()
                .filter(Invitation::getAcceptStatus)
                .toList();
        List<Invitation> pendingIncoming = allIncoming.stream()
                .filter(inv -> !inv.getAcceptStatus())
                .toList();

        List<Invitation> acceptedOutgoing = allOutgoing.stream()
                .filter(Invitation::getAcceptStatus)
                .toList();
        List<Invitation> pendingOutgoing = allOutgoing.stream()
                .filter(inv -> !inv.getAcceptStatus())
                .toList();

        return new CategorizedInvitations(acceptedIncoming, pendingIncoming, acceptedOutgoing, pendingOutgoing);
    }

    @Override
    public Collection<Profile> findByText(String string) {
        if (string == null || string.isEmpty()) {
            return getAllProfiles();
        }
        return daoFactory.getProfileDao().findByText(string);
    }

    @Override
    public void deleteProfile(Profile profile) {
        Profile p = daoFactory.getProfileDao().findById(profile.getId());
        if (p == null) {
            throw new NotFoundException("Profile with id=" + profile.getId() + " not found");
        }
        daoFactory.getProfileDao().delete(p);
    }

    @Override
    public void sendInvitation(Profile sender, Profile receiver) {
        if (daoFactory.getProfileDao().isInvitationExists(sender.getId(), receiver.getId())) {
            throw new IllegalStateException("Invitation already sent");
        }
        Invitation inv = new Invitation();
        inv.setSender(sender);
        inv.setReceiver(receiver);
        inv.setAcceptStatus(false);
        addInvitation(sender, receiver, inv);
    }

    @Override
    public void addInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().addInvitation(sender, receiver, invitation);
    }

    @Override
    public void acceptInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().acceptInvitation(sender, receiver, invitation);
    }

    @Override
    public void acceptInvitationFromUser(Profile receiver, Long senderId) {
        Invitation inv = getReceivedInvitations(receiver)
                .stream()
                .filter(item -> Objects.equals(item.getSender().getId(), senderId))
                .filter(item -> !item.getAcceptStatus())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No pending invitation from this user"));

        acceptInvitation(inv.getSender(), receiver, inv);
    }


    @Override
    public void deleteInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().deleteInvitation(sender, receiver, invitation);
    }

    @Override
    public void deleteUserInvitation(Profile user, Long invitationId) {
        Invitation invitation = getSentInvitations(user).stream()
                .filter(inv -> Objects.equals(inv.getId(), invitationId))
                .findFirst()
                .orElseGet(() -> getReceivedInvitations(user).stream()
                        .filter(inv -> Objects.equals(inv.getId(), invitationId))
                        .findFirst()
                        .orElse(null));

        if (invitation == null) {
            throw new NotFoundException("Invitation with id " + invitationId + " not found");
        }

        deleteInvitation(invitation.getSender(), invitation.getReceiver(), invitation);
    }
}
