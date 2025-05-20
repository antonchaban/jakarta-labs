package com.example.lab2.dao.inmem;

import com.example.lab2.dao.inmem.ProfileDao;

public interface DaoFactory {
    ProfileDao getProfileDao();
//    InvitationDao getInvitationDao(); deprecated
}
