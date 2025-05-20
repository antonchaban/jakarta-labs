package com.example.lab2.dao.impl.postgreSQL;

import com.example.lab2.entities.Invitation;
import com.example.lab2.entities.PrivateInfo;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.PublicInfo;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Singleton
@Startup
public class TestDataLoader {
    @PersistenceContext(unitName = "LabPU")
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void loadData() {
        em.createQuery("DELETE FROM Invitation").executeUpdate();
        em.createQuery("DELETE FROM Profile").executeUpdate();

        // Create mock profiles
        Profile p1 = new Profile();
        p1.setUsername("user1");
        p1.setPublicInfo(new PublicInfo("Bio1", 25));
        p1.setPrivateInfo(new PrivateInfo("user1@mail.com", "password1"));
        em.persist(p1);

        Profile p2 = new Profile();
        p2.setUsername("user2");
        p2.setPublicInfo(new PublicInfo("Bio2", 30));
        p2.setPrivateInfo(new PrivateInfo("user2@mail.com", "password2"));
        em.persist(p2);

        Profile p3 = new Profile();
        p3.setUsername("user3");
        p3.setPublicInfo(new PublicInfo("Bio3", 35));
        p3.setPrivateInfo(new PrivateInfo("user3@mail.com", "password3"));
        em.persist(p3);

        Profile p4 = new Profile();
        p4.setUsername("user4");
        p4.setPublicInfo(new PublicInfo("Bio4", 40));
        p4.setPrivateInfo(new PrivateInfo("user4@mail.com", "password4"));
        em.persist(p4);

        Profile p5 = new Profile();
        p5.setUsername("user5");
        p5.setPublicInfo(new PublicInfo("Bio5", 45));
        p5.setPrivateInfo(new PrivateInfo("user5@mail.com", "password5"));
        em.persist(p5);

        Invitation i1 = new Invitation();
        i1.setSender(p1);
        i1.setReceiver(p2);
        i1.setAcceptStatus(false);
        em.persist(i1);

        Invitation i2 = new Invitation();
        i2.setSender(p2);
        i2.setReceiver(p3);
        i2.setAcceptStatus(false);
        em.persist(i2);

        Invitation i3 = new Invitation();
        i3.setSender(p3);
        i3.setReceiver(p4);
        i3.setAcceptStatus(false);
        em.persist(i3);

        Invitation i4 = new Invitation();
        i4.setSender(p4);
        i4.setReceiver(p5);
        i4.setAcceptStatus(false);
        em.persist(i4);

        Invitation i5 = new Invitation();
        i5.setSender(p5);
        i5.setReceiver(p1);
        i5.setAcceptStatus(false);
        em.persist(i5);
    }
}