package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.User;
import model.dao.UserDAO;

import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/process_registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nome = request.getParameter("name");

        User User = new User();
        User.setUsername(username);
        User.setPasswordSHA(password);
        User.setNome(nome);
        User.setEmail(email);

        UserDAO UserDAO = new UserDAO();
        UserDAO.doSave(User);

        response.sendRedirect("show-prodotti");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
