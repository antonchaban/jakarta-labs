package com.example.lab2.dao.impl.inmem;

import com.example.lab2.dao.InvitationDao;
import com.example.lab2.models.Invitation;
import com.example.lab2.models.Profile;

import java.util.HashMap;

//@Deprecated
//public class InMemoryInvitationDao extends InMemoryAbstractDao<Invitation> implements InvitationDao {
//    InMemoryInvitationDao(InMemoryDatabase database) {
//        super(database.invitations, Invitation::getId, Invitation::setId, database);
//    }
//
//    @Override
//    public HashMap<Long, Invitation> findBySenderId(Long senderId) {
//        return database.invitations.values().stream()
//                .filter(invitation -> invitation.getSenderId().equals(senderId))
//                .collect(HashMap::new, (map, invitation) -> map.put(invitation.getId(), invitation), HashMap::putAll);
//    }
//
//    @Override
//    public HashMap<Long, Invitation> findByReceiverId(Long receiverId) {
//        return null;
//    }
//
//    @Override
//    public HashMap<Long, Invitation> findByAcceptStatus(Boolean acceptStatus) {
//        return null;
//    }
//
//    @Override
//    public void updateAcceptStatus(Invitation invitation, Boolean acceptStatus) {
//
//    }
//
//    @Override
//    public void addInvitation(Profile sender, Profile receiver, Invitation invitation) {
//
//    }
//
//    @Override
//    public void deleteInvitation(Profile sender, Profile receiver, Invitation invitation) {
//
//    }
//}
