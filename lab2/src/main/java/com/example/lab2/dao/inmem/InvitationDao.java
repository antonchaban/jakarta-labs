package com.example.lab2.dao.inmem;

import com.example.lab2.dao.AbstractDao;
import com.example.lab2.models.inmem.Invitation;
import com.example.lab2.models.inmem.Profile;

import java.util.HashMap;

@Deprecated
public interface InvitationDao extends AbstractDao<Invitation> {
    HashMap<Long, Invitation> findBySenderId(Long senderId);
    HashMap<Long, Invitation> findByReceiverId(Long receiverId);
    HashMap<Long, Invitation> findByAcceptStatus(Boolean acceptStatus);

    void updateAcceptStatus(Invitation invitation, Boolean acceptStatus);
    void addInvitation(Profile sender, Profile receiver, Invitation invitation);
    void deleteInvitation(Profile sender, Profile receiver, Invitation invitation);
}
