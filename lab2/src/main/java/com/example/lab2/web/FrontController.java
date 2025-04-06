package com.example.lab2.web;

import com.example.lab2.models.Profile;
import com.example.lab2.services.ProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "FrontController", urlPatterns = {"/date-app/*"})
public class FrontController extends HttpServlet {
    ProfileService profileService;

    @Override
    public void init() {
        profileService = (ProfileService) getServletContext().getAttribute("profileService");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }
        try {
            switch (pathInfo) {
//                case "/login":
//                    login(request, response);
//                    break;
//                case "/logout":
//                    logout(request, response);
//                    break;
//                case "/main":
//                    main(request, response);
//                    break;
//                case "/vacancy":
//                    vacancy(request, response);
//                    break;
//                case "/vacedit":
//                    vacedit(request, response);
//                    break;
//                case "/vacnew":
//                    vacnew(request, response);
//                    break;
//                case "/vacdel":
//                    vacdel(request, response);
//                    break;
//                case "/vacsave":
//                    vacsave(request, response);
//                    break;
//                case "/vacupdate":
//                    vacupdate(request, response);
//                    break;
//                case "/sign-up":
//                    signup(request, response);
//                    break;
                case "/":
                case "/search":
                default:
                    main(request, response);
                    break;
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
