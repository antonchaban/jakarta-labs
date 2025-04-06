package com.example.lab2.dao.impl.inmem;

import com.example.lab2.dao.ProfileDao;
import com.example.lab2.models.Invitation;
import com.example.lab2.models.Profile;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryProfileDao extends InMemoryAbstractDao<Profile> implements ProfileDao {
    InMemoryProfileDao(InMemoryDatabase database) {
        super(database.profiles, Profile::getId, Profile::setId, database);
    }

    @Override
    public Profile findByUsername(String login) {
        return database.profiles.values()
                .stream()
                .filter(user -> user.getUsername().equals(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Profile findById(Long id) {
        return database.profiles.get(id);
    }

    @Override
    public Collection<Profile> findByText(String string) {
        String[] words = string.toLowerCase().split(" ");
        return database.profiles.values().stream()
                .filter(profile -> containsAllWords(profile, words))
                .collect(Collectors.toList());
    }

    private static boolean containsAllWords(Profile profile, String[] words) {
        String string = profile.getUsername() + " " + profile.getPublicInfo().getBio() + " " +
                profile.getPublicInfo().getAge();
        string = string.toLowerCase();
        return Stream.of(words).allMatch(string::contains);
    }

    @Override
    public void addInvitation(Profile sender, Profile receiver, Invitation invitation) {
        sender.getSentInvitations().put(invitation.getId(), invitation);
        receiver.getReceivedInvitations().put(invitation.getId(), invitation);
    }

    @Override
    public void deleteInvitation(Profile sender, Profile receiver, Invitation invitation) {
        sender.getSentInvitations().remove(invitation.getId());
        receiver.getReceivedInvitations().remove(invitation.getId());
    }

    @Override
    public void acceptInvitation(Profile sender, Profile receiver, Invitation invitation) {
        sender.getSentInvitations().get(invitation.getId()).setAcceptStatus(true);
        receiver.getReceivedInvitations().get(invitation.getId()).setAcceptStatus(true);
    }

    @Override
    public void newProfile(Profile profile) {
        this.insert(profile, true);
        profile.setId(this.idGetter.apply(profile));
    }
}