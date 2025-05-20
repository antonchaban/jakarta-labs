package com.example.lab2.web;

import com.example.lab2.models.Invitation;
import com.example.lab2.models.PrivateInfo;
import com.example.lab2.models.Profile;
import com.example.lab2.models.PublicInfo;
import com.example.lab2.services.ProfileService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@WebServlet(name = "FrontController", urlPatterns = {"/date-app/*"})
public class FrontController extends HttpServlet {
    @EJB
    ProfileService profileService;

//    @Override
//    public void init() {
//        profileService = (ProfileService) getServletContext().getAttribute("profileService");
//    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        try {
            switch (pathInfo) {
                case "/login":
                    login(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/register":
                    register(request, response);
                    break;
                case "/main":
                    main(request, response);
                    break;
                case "/":
                case "/search":
                    main(request, response);
                    break;
                case "/profile":
                    profileView(request, response);
                    break;
                case "/profile/edit":
                    profileEdit(request, response);
                    break;
                case "/profile/update":
                    profileUpdate(request, response);
                    break;
                case "/invitation/send":
                    invitationSend(request, response);
                    break;
                case "/invitation/view":
                    invitationView(request, response);
                    break;
                case "/invitation/accept":
                    invitationAccept(request, response);
                    break;
                default:
                    main(request, response);
            }
        } catch (RuntimeException ex) {
            error(request, response, "Oops, " + ex.getMessage());
        }

    }

    protected void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchText = request.getParameter("search");
        Collection<Profile> profiles;
        if (searchText != null) {
            profiles = profileService.findByText(searchText);
        } else
            profiles = profileService.getAllProfiles();
        request.setAttribute("profiles", profiles);
        request.setAttribute("text", searchText);
        request.getRequestDispatcher("/WEB-INF/jsp/profiles.jsp").forward(request, response);
    }

    protected void profileView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Profile p = profileService.getById(id);
        req.setAttribute("profile", p);

        req.setAttribute("invitations",
                profileService.getReceivedInvitations(p));

        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp")
                .forward(req, resp);
    }

    protected void profileEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Profile p = profileService.getById(id);
        req.setAttribute("profile", p);
        req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp")
                .forward(req, resp);
    }

    protected void profileUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Profile p = profileService.getById(id);

        p.setUsername(req.getParameter("username"));
        p.getPublicInfo().setAge(Integer.parseInt(req.getParameter("age")));
        p.getPublicInfo().setBio(req.getParameter("bio"));
        profileService.updateProfile(p);

        resp.sendRedirect(req.getContextPath() + "/date-app/profile?id=" + id);
    }

    protected void invitationSend(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Profile sender   = (Profile) req.getSession().getAttribute("user");
        Long    toId     = Long.valueOf(req.getParameter("toId"));
        Profile receiver = profileService.getById(toId);
        long invId = sender.getId() + receiver.getId();

        Invitation inv = new Invitation(invId, sender.getId(), receiver.getId(), false);
        profileService.addInvitation(sender, receiver, inv);

        resp.sendRedirect(req.getContextPath() + "/date-app/profile?id=" + toId);
    }

    protected void invitationAccept(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Profile receiver = (Profile) req.getSession().getAttribute("user");
        Long    fromId   = Long.valueOf(req.getParameter("fromId"));
        Profile sender   = profileService.getById(fromId);
        Invitation inv = profileService.getReceivedInvitations(receiver)
                .stream()
                .filter(item -> Objects.equals(item.getSenderId(), fromId))
                .findFirst()
                .get();

        profileService.acceptInvitation(sender, receiver, inv);

        resp.sendRedirect(req.getContextPath() + "/date-app/invitation/inbox");
    }

    protected void invitationView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Profile user = (Profile) req.getSession().getAttribute("user");

        Collection<Invitation> incoming = profileService.getReceivedInvitations(user);
        Collection<Invitation> outgoing = profileService.getSentInvitations(user);

        req.setAttribute("incomingInvitations", incoming);
        req.setAttribute("outgoingInvitations", outgoing);
        req.getRequestDispatcher("/WEB-INF/jsp/invitations.jsp").forward(req, resp);
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        String login = request.getParameter("login");
        Profile user = profileService.getByLogin(login);
        String password = request.getParameter("password");

        if (!profileService.checkPass(user, password)) {
            error(request, response, "Sorry, wrong password");
            return;
        }

        request.getSession().setAttribute("user", user);
        response.sendRedirect(".");
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        String username = request.getParameter("username");
        String bio = request.getParameter("bio");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Integer age = Integer.parseInt(request.getParameter("age"));
        Profile user = new Profile(6L, username, new PublicInfo(bio, age), new PrivateInfo(email, password));

        profileService.newProfile(user);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(".");

    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("in", vacService.getAllVacancies());
        request.getSession().invalidate();
        response.sendRedirect(".");
    }


    protected void error(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
