package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.User;
import model.dao.UserDAO;

import java.io.IOException;

// Servlet per la registrazione di un nuovo utente
@WebServlet(name = "RegistrationServlet", value = "/process_registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nome = request.getParameter("name");
        boolean admin = request.getParameter("admin") != null;

        User user = new User();
        user.setUsername(username);
        user.setPasswordSHA(password);
        user.setNome(nome);
        user.setEmail(email);
        user.setAdmin(admin);

        UserDAO userDAO = new UserDAO();
        userDAO.doSave(user);

        // Salva l'utente in sessione
        request.getSession().setAttribute("user", user);

        response.sendRedirect("show-prodotti");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

