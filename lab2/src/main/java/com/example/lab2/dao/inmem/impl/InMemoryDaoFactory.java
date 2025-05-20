package com.example.lab2.dao.inmem.impl;

import com.example.lab2.dao.inmem.DaoFactory;
import com.example.lab2.dao.inmem.ProfileDao;

public class InMemoryDaoFactory implements DaoFactory {
    InMemoryDatabase database;

//    InvitationDao invitationDao;
    ProfileDao profileDao;

    InMemoryDaoFactory(InMemoryDatabase database) {
        this.database = database;

//        invitationDao = new InMemoryInvitationDao(database);
        profileDao = new InMemoryProfileDao(database);
    }

    @Override
    public ProfileDao getProfileDao() {
        return profileDao;
    }

//    @Override
//    public InvitationDao getInvitationDao() {
//        return invitationDao;
//    }
}
