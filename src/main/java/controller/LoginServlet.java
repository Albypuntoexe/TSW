package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=empty_fields");
            return;
        }

        username = username.trim();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.doRetrieveByUsernamePassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.isAdmin());

            System.out.println("Utente loggato: " + user.getUsername());
            System.out.println("ID sessione: " + session.getId());
            System.out.println("È admin: " + user.isAdmin());

            if (user.isAdmin()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp?login=success");
            }
        } else {
            // Reindirizza alla pagina di login con errore per credenziali errate
            response.sendRedirect("login.jsp?error=invalid_credentials");
        }
    }
}
