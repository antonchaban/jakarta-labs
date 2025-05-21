package com.example.lab2.services;

import com.example.lab2.dao.DaoFactory;
import com.example.lab2.entities.Invitation;
import com.example.lab2.entities.Profile;
import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.NotFoundException;

import java.util.Collection;
import java.util.function.UnaryOperator;


@Stateless
@Local(ProfileService.class)
public class ProfileServiceImpl implements ProfileService{
    @EJB
    DaoFactory daoFactory;
    UnaryOperator<String> passHasher = UnaryOperator.identity();

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
        return profile.getPrivateInfo().getPassword().equals(passHasher.apply(password));
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
            throw new NotFoundException("Project with id=" + profile.getId() + " not found");
        }
        daoFactory.getProfileDao().delete(p);
    }

    @Override
    public void addInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().addInvitation(sender, receiver, invitation);
    }

    @Override
    public void deleteInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().deleteInvitation(sender, receiver, invitation);
    }

    @Override
    public void acceptInvitation(Profile sender, Profile receiver, Invitation invitation) {
        daoFactory.getProfileDao().acceptInvitation(sender, receiver, invitation);
    }
}
