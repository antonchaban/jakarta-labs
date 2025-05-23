package com.example.lab2.dao.inmem;

import com.example.lab2.dao.AbstractDao;
import com.example.lab2.models.inmem.Invitation;
import com.example.lab2.models.inmem.Profile;

import java.util.Collection;

public interface ProfileDao extends AbstractDao<Profile> {
    Profile findByUsername(String username);
    Profile findById(Long id);
    Collection<Profile> findByText(String string);

    void newProfile(Profile profile);
    void addInvitation(Profile sender, Profile receiver, Invitation invitation);
    void deleteInvitation(Profile sender, Profile receiver, Invitation invitation);
    void acceptInvitation(Profile sender, Profile receiver, Invitation invitation);
}
