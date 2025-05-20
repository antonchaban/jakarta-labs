package com.example.lab2.dao.impl;

import com.example.lab2.dao.DaoFactory;
import com.example.lab2.dao.ProfileDao;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class DaoFactoryImpl implements DaoFactory {

    @EJB
    private ProfileDao profileDao;

    @Override
    public ProfileDao getProfileDao() {
        return profileDao;
    }
}
