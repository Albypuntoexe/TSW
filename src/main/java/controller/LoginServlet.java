package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.beans.User;
import model.dao.UserDAO;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.doRetrieveByUsernamePassword(username, password);

        if (user != null) {
            // Utente trovato, salva in sessione
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // Reindirizza alla home page (che ora è gestita da servlet)
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            // Utente non trovato, torna alla home con errore
            response.sendRedirect(request.getContextPath() + "/?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}