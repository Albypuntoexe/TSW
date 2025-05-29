package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.User;
import model.dao.UserDAO;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect alla pagina login.jsp che contiene anche la registrazione
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String telefono = request.getParameter("telefono");

        // Validazione base
        if (username == null || password == null || email == null || nome == null ||
                username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty() || nome.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=empty_fields");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordSHA(password);
        user.setNome(nome);
        user.setCognome(cognome != null ? cognome : "");
        user.setEmail(email);
        user.setTelefono(telefono != null ? telefono : "");
        user.setAdmin(false); // Gli utenti normali non sono admin

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.doSave(user);

            // Redirect con messaggio di successo
            response.sendRedirect("login.jsp?success=registered");

        } catch (Exception e) {
            response.sendRedirect("login.jsp?error=user_exists");
        }
    }
}