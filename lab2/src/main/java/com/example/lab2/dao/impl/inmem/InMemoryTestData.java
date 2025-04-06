package com.example.lab2.dao.impl.inmem;

import com.example.lab2.models.Invitation;
import com.example.lab2.models.PrivateInfo;
import com.example.lab2.models.Profile;
import com.example.lab2.models.PublicInfo;

import java.util.Arrays;
import java.util.List;

public class InMemoryTestData {
    public static void generateTo(InMemoryDatabase database) {
        database.profiles.clear();
//        database.invitations.clear();

        Profile profile1 = new Profile(1L, "user1", new PublicInfo("Bio1", 25), new PrivateInfo("user1@mail.com", "password1"));
        Profile profile2 = new Profile(2L, "user2", new PublicInfo("Bio2", 30), new PrivateInfo("user2@mail.com", "password2"));
        Profile profile3 = new Profile(3L, "user3", new PublicInfo("Bio3", 35), new PrivateInfo("user3@mail.com", "password3"));
        Profile profile4 = new Profile(4L, "user4", new PublicInfo("Bio4", 40), new PrivateInfo("user4@mail.com", "password4"));
        Profile profile5 = new Profile(5L, "user5", new PublicInfo("Bio5", 45), new PrivateInfo("user5@mail.com", "password5"));

        List<Profile> profiles = Arrays.asList(profile1, profile2, profile3, profile4, profile5);
        profiles.forEach(profile -> database.profiles.put(profile.getId(), profile));

        Invitation invitation1 = new Invitation(1L, 1L, 2L, false);
        Invitation invitation2 = new Invitation(2L, 2L, 3L, false);
        Invitation invitation3 = new Invitation(3L, 3L, 4L, false);
        Invitation invitation4 = new Invitation(4L, 4L, 5L, false);
        Invitation invitation5 = new Invitation(5L, 5L, 1L, false);


        profile1.getSentInvitations().put(invitation1.getId(), invitation1);
        profile2.getReceivedInvitations().put(invitation1.getId(), invitation1);

        profile2.getSentInvitations().put(invitation2.getId(), invitation2);
        profile3.getReceivedInvitations().put(invitation2.getId(), invitation2);

        profile3.getSentInvitations().put(invitation3.getId(), invitation3);
        profile4.getReceivedInvitations().put(invitation3.getId(), invitation3);

        profile4.getSentInvitations().put(invitation4.getId(), invitation4);
        profile5.getReceivedInvitations().put(invitation4.getId(), invitation4);

        profile5.getSentInvitations().put(invitation5.getId(), invitation5);
        profile1.getReceivedInvitations().put(invitation5.getId(), invitation5);

        database.profiles.put(profile1.getId(), profile1);
        database.profiles.put(profile2.getId(), profile2);
        database.profiles.put(profile3.getId(), profile3);
        database.profiles.put(profile4.getId(), profile4);
        database.profiles.put(profile5.getId(), profile5);

//        database.invitations.put(invitation1.getId(), invitation1);
//        database.invitations.put(invitation2.getId(), invitation2);
//        database.invitations.put(invitation3.getId(), invitation3);
//        database.invitations.put(invitation4.getId(), invitation4);
//        database.invitations.put(invitation5.getId(), invitation5);
    }
}