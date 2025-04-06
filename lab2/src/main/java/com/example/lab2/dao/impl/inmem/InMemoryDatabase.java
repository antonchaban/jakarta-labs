package com.example.lab2.dao.impl.inmem;

import com.example.lab2.dao.DaoFactory;
import com.example.lab2.models.Invitation;
import com.example.lab2.models.Profile;

import java.util.*;

public class InMemoryDatabase {
    Map<Long, Profile> profiles;
    Map<Long, Invitation> invitations;

    public InMemoryDatabase() {
        invitations = new TreeMap<>();
        profiles = new TreeMap<>();
    }

    public DaoFactory getDaoFactory() {
        return new InMemoryDaoFactory(this);
    }
}