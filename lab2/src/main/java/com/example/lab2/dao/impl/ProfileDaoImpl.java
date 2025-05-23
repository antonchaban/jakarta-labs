package com.example.lab2.dao.impl;

import com.example.lab2.dao.ProfileDao;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.Invitation;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

import java.util.Collection;

@Stateless
@Local(ProfileDao.class)
public class ProfileDaoImpl extends AbstractDaoImpl<Profile> implements ProfileDao {
    public ProfileDaoImpl() { super(Profile.class); }

    @Override
    public Profile findByUsername(String username) {
        TypedQuery<Profile> q = em.createQuery(
                "SELECT p FROM Profile p WHERE p.username = :u", Profile.class);
        q.setParameter("u", username);
        return q.getResultStream().findFirst().orElse(null);
    }

    @Override
    public Profile findById(Long id) {
        return get(id);
    }

    @Override
    public Collection<Profile> findByText(String text) {
        TypedQuery<Profile> q = em.createQuery(
                "SELECT p FROM Profile p WHERE LOWER(p.username) LIKE :t " +
                        "OR LOWER(p.publicInfo.bio) LIKE :t", Profile.class);
        q.setParameter("t", "%" + text.toLowerCase() + "%");
        return q.getResultList();
    }

    @Override
    public void newProfile(Profile profile) {
        insert(profile, true);
    }

    @Override
    public boolean isInvitationExists(Long senderId, Long receiverId) {
        Long count = em.createQuery(
                        "SELECT count(i) FROM Invitation i " +
                                " WHERE i.sender.id = :s AND i.receiver.id = :r", Long.class)
                .setParameter("s", senderId)
                .setParameter("r", receiverId)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public void addInvitation(Profile sender, Profile receiver, Invitation invitation) {
        if (isInvitationExists(sender.getId(), receiver.getId())) {
            throw new IllegalStateException("Invitation already exists");
        }

        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        sender.getSentInvitations().add(invitation);
        receiver.getReceivedInvitations().add(invitation);
        em.persist(invitation);
        em.merge(sender);
        em.merge(receiver);
    }

    @Override
    public void deleteInvitation(Profile sender, Profile receiver, Invitation invitation) {
        sender.getSentInvitations().removeIf(inv -> inv.getId().equals(invitation.getId()));
        receiver.getReceivedInvitations().removeIf(inv -> inv.getId().equals(invitation.getId()));
        Invitation inv = em.find(Invitation.class, invitation.getId());
        if (inv != null) {
            em.remove(inv);
        }
    }

    @Override
    public void acceptInvitation(Profile sender, Profile receiver, Invitation invitation) {
        Invitation inv = em.find(Invitation.class, invitation.getId());
        if (inv != null) {
            inv.setAcceptStatus(true);
            em.merge(inv);
            invitation.setAcceptStatus(true);
        }
    }
}
