package com.example.lab2.dao.inmem.impl;

import com.example.lab2.dao.inmem.DaoFactory;
import com.example.lab2.models.inmem.Profile;

import java.util.*;

public class InMemoryDatabase {
    Map<Long, Profile> profiles;
//    Map<Long, Invitation> invitations;

    public InMemoryDatabase() {
//        invitations = new TreeMap<>();
        profiles = new TreeMap<>();
    }

    public DaoFactory getDaoFactory() {
        return new InMemoryDaoFactory(this);
    }
}