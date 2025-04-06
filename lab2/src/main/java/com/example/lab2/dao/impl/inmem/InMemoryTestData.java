package com.example.lab2.dao.impl.inmem;

import com.example.lab2.models.PrivateInfo;
import com.example.lab2.models.Profile;
import com.example.lab2.models.PublicInfo;

import java.util.HashMap;

public class InMemoryTestData {
    public static void generateTo(InMemoryDatabase database) {
        database.profiles.clear();

        Profile admin = new Profile(1L, "admin", new PublicInfo("Big boss", 21),
                new PrivateInfo("admin@mail.com", "admin"));
    }

}
