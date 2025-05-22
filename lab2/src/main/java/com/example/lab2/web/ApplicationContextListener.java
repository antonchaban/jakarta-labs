package com.example.lab2.web;

import com.example.lab2.dao.inmem.DaoFactory;
import com.example.lab2.dao.inmem.impl.InMemoryDatabase;
import com.example.lab2.dao.inmem.impl.InMemoryTestData;
import com.example.lab2.services.inmem.ProfileService;
import com.example.lab2.services.inmem.ProfileServiceInMemImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.function.UnaryOperator;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        InMemoryDatabase database = new InMemoryDatabase();
        InMemoryTestData.generateTo(database);
        DaoFactory daoFactory = database.getDaoFactory();

        ProfileService profileService = new ProfileServiceInMemImpl(daoFactory, UnaryOperator.identity());
        sce.getServletContext().setAttribute("profileService", profileService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
