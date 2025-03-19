package com.example.lab1;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/chaban")
public class TeamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html lang=\"uk\">");
            out.println("<head>");
            out.println("<title>Чабан А. Є.</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Чабан А. Є.</h1>");
            out.println("<p>Група: ІА-42мп</p>");
            out.println("<p>Телеграм: <a href=\"https://t.me/anton_the_f\">@anton_the_f</a></p>");
            out.println("<a href=\"/\">На головну</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
