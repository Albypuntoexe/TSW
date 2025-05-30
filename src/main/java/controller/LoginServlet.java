package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.beans.User;
import model.dao.UserDAO;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostra la pagina di login
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String ricordami = request.getParameter("ricordami");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=empty_fields");
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.doRetrieveByUsernamePassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);


            System.out.println("User logged in: " + user.getUsername());
            System.out.println("Session ID: " + session.getId());
            System.out.println("Is admin: " + user.isAdmin());

            // Gestione cookie "ricordami"
            if ("si".equals(ricordami)) {
                Cookie usernameCookie = new Cookie("saved_username", username);
                Cookie passwordCookie = new Cookie("saved_password", password);

                // Cookie durano 30 giorni
                usernameCookie.setMaxAge(30 * 24 * 60 * 60);
                passwordCookie.setMaxAge(30 * 24 * 60 * 60);

                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            } else {
                // Rimuovi i cookie se "ricordami" non è selezionato
                Cookie usernameCookie = new Cookie("saved_username", "");
                Cookie passwordCookie = new Cookie("saved_password", "");

                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);

                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }
            //redirect alla home con messaggio di login riuscito
            response.sendRedirect("index.jsp?login=success");
        } else {
            response.sendRedirect("login.jsp?error=credenziali_errate");
        }
    }
}