package com.example.lab2.web;

import com.example.lab2.dao.impl.TestDataLoader;
import com.example.lab2.entities.Invitation;
import com.example.lab2.entities.PrivateInfo;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.PublicInfo;
import com.example.lab2.models.CategorizedInvitations;
import com.example.lab2.services.ProfileService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;


@WebServlet(name = "FrontController", urlPatterns = {"/date-app/*"})
public class FrontController extends HttpServlet {
    @EJB
    ProfileService profileService;
    public enum InvitationStatus {
        NONE, PENDING, ACCEPTED
    }


    @Override
    public void init() {
        //profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/logout":
                    logout(request, response);
                    break;
                case "/login":
                    login(request, response);
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
                case "/invitations":
                    invitationView(request, response);
                    break;
                case "/invitation/send":
                    invitationSend(request, response);
                    break;
                case "/invitation/accept":
                    invitationAccept(request, response);
                    break;
                case "/invitation/delete":
                    invitationDelete(request, response);
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
        HttpSession session = req.getSession(false);
        Profile sessionUser = session == null ? null : (Profile) session.getAttribute("user");

        Profile currentUser = null;
        if (sessionUser != null) {
            currentUser = profileService.getById(sessionUser.getId());
            session.setAttribute("user", currentUser);
        }

        Long id = Long.valueOf(req.getParameter("id"));
        Profile profile = profileService.getById(id);
        req.setAttribute("profile", profile);

        boolean isOwner = currentUser != null && Objects.equals(currentUser.getId(), profile.getId());
        InvitationStatus status = InvitationStatus.NONE;

        if (currentUser != null && !isOwner) {
            for (Invitation inv : profileService.getReceivedInvitations(currentUser)) {
                if (Objects.equals(inv.getSender().getId(), profile.getId())) {
                    status = inv.getAcceptStatus() ? InvitationStatus.ACCEPTED : InvitationStatus.PENDING;
                    break;
                }
            }
            if (status == InvitationStatus.NONE) {
                for (Invitation inv : profileService.getSentInvitations(currentUser)) {
                    if (Objects.equals(inv.getReceiver().getId(), profile.getId())) {
                        status = inv.getAcceptStatus() ? InvitationStatus.ACCEPTED : InvitationStatus.PENDING;
                        break;
                    }
                }
            }
        }

        req.setAttribute("invStatus", status);
        req.setAttribute("isOwner", isOwner);

        req.setAttribute("invitations", profileService.getReceivedInvitations(profile));
        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }

    protected void profileEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Profile p = profileService.getById(id);
        req.setAttribute("profile", p);
        req.getRequestDispatcher("/WEB-INF/jsp/profile-edit.jsp")
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

        HttpSession session = req.getSession(false);
        Profile sender = (Profile) session.getAttribute("user");
        Long toId = Long.valueOf(req.getParameter("toId"));
        Profile receiver = profileService.getById(toId);

        try {
            profileService.sendInvitation(sender, receiver);
        } catch (IllegalStateException ex) {
            req.setAttribute("error", ex.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/date-app/profile?id=" + toId);
    }

    protected void invitationAccept(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Profile receiver = (Profile) req.getSession().getAttribute("user");
        Long    fromId   = Long.valueOf(req.getParameter("invId"));

        profileService.acceptInvitationFromUser(receiver, fromId);
        resp.sendRedirect(req.getContextPath() + "/date-app/invitations");
    }

    protected void invitationView(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Profile user = (Profile) req.getSession().getAttribute("user");
        CategorizedInvitations invitations = profileService.getCategorizedInvitation(user);

        req.setAttribute("acceptedIncoming", invitations.getAcceptedIncoming());
        req.setAttribute("pendingIncoming", invitations.getPendingIncoming());
        req.setAttribute("acceptedOutgoing", invitations.getAcceptedOutgoing());
        req.setAttribute("pendingOutgoing", invitations.getPendingOutgoing());

        req.getRequestDispatcher("/WEB-INF/jsp/invitations.jsp").forward(req, resp);
    }

    protected void invitationDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/date-app/login");
            return;
        }
        Profile user = (Profile) session.getAttribute("user");

        String invIdStr = req.getParameter("invId");
        if (invIdStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invitation ID is required");
            return;
        }
        Long invId = Long.valueOf(invIdStr);

        profileService.deleteUserInvitation(user, invId);

        resp.sendRedirect(req.getContextPath() + "/date-app/invitations");
    }


    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Profile user = profileService.getByLogin(login);

        if (login == null || password == null) {
            request.setAttribute("error", "Please provide login and password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (user == null || !profileService.checkPass(user, password)) {
            error(request, response, "Sorry, wrong password");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/date-app/");
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String bio = request.getParameter("bio");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username == null || email == null || password == null || request.getParameter("age") == null) {
            request.setAttribute("error","All fields are required");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        Integer age = Integer.parseInt(request.getParameter("age"));
        Profile profile = profileService.register(username, password, email, bio, age);

        request.getSession().setAttribute("user", profile);
        response.sendRedirect(request.getContextPath() + "/date-app/");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(".");
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null || "/".equals(path) || "/search".equals(path) || "/main".equals(path)) {
            main(request, response);
        } else if ("/login".equals(path)) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("/login".equals(path)) {
            login(request, response);
        } else if ("/register".equals(path)) {
            register(request, response);
        } else {
            processRequest(request, response);
        }
    }
}
