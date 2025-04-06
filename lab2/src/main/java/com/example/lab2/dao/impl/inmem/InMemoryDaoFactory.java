package com.example.lab2.dao.impl.inmem;

import com.example.lab2.dao.DaoFactory;
import com.example.lab2.dao.InvitationDao;
import com.example.lab2.dao.ProfileDao;
import com.example.lab2.models.Profile;

public class InMemoryDaoFactory implements DaoFactory {
    InMemoryDatabase database;

    InvitationDao invitationDao;
    ProfileDao profileDao;

    InMemoryDaoFactory(InMemoryDatabase database) {
        this.database = database;

        invitationDao = new InMemoryInvitationDao(database);
        profileDao = new InMemoryProfileDao(database);
    }

    @Override
    public ProfileDao getProfileDao() {
        return profileDao;
    }

    @Override
    public InvitationDao getInvitationDao() {
        return invitationDao;
    }
}
