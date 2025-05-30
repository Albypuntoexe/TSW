package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.User;
import model.dao.UserDAO;
import util.PasswordUtil;

import java.io.IOException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

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
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String telefono = request.getParameter("telefono");

        // Validazione dei campi obbligatori
        if (username == null || password == null || email == null || nome == null ||
                username.trim().isEmpty() || password.trim().isEmpty() ||
                email.trim().isEmpty() || nome.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=empty_fields");
            return;
        }

        // Creazione dell'oggetto User
        User user = new User();
        user.setUsername(username.trim());
        user.setNome(nome.trim());
        user.setCognome(cognome != null ? cognome.trim() : "");
        user.setEmail(email.trim());
        user.setTelefono(telefono != null ? telefono.trim() : "");
        user.setAdmin(false); // I nuovi utenti non sono admin per default

        // Hash della password usando PasswordUtil
        String hashedPassword = PasswordUtil.hashPassword(password);
        user.setPassword(hashedPassword);

        try {
            UserDAO userDAO = new UserDAO();

            // Verifica se l'utente esiste già
            if (userDAO.doRetrieveByEmail(email) != null) {
                response.sendRedirect("login.jsp?error=email_exists");
                return;
            }

            if (userDAO.doRetrieveByUsername(username) != null) {
                response.sendRedirect("login.jsp?error=username_exists");
                return;
            }

            // Salva l'utente (la password è già hashata)
            userDAO.doSave(user);
            response.sendRedirect("login.jsp?success=registered");

        } catch (Exception e) {
            // Log dell'eccezione per debug
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=registration_failed");
        }
    }
}