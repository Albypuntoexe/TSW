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
import java.util.List;

@WebServlet("/admin/permissions")
public class AdminPermissionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se l'utente è loggato e se è admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }
        // se non è admin, reindirizza alla home con errore
        User currentUser = (User) session.getAttribute("user");
        if (!currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=access_denied");
            return;
        }
        // Se l'utente è admin, carica la lista degli utenti
        try {
            UserDAO userDAO = new UserDAO();
            List<User> allUsers = userDAO.doRetrieveAll();
            // aggiungi la lista degli utenti alla richiesta per permettere alla JSP di visualizzarli
            request.setAttribute("users", allUsers);
            // Inoltra alla JSP per la visualizzazione
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/WEB-INF/admin.jsp?error=load_users_failed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se l'utente è loggato e se è admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=not_logged_in");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!currentUser.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=access_denied");
            return;
        }

        String action = request.getParameter("action");
        String userEmail = request.getParameter("userEmail");

        if (action == null || userEmail == null) {
            response.sendRedirect(request.getContextPath() + "/admin/permissions?error=invalid_parameters");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();

            switch (action) {
                case "promote":
                    // Promuovi utente ad admin
                    userDAO.doUpdateAdminStatus(userEmail, true);
                    response.sendRedirect(request.getContextPath() + "/admin/permissions?success=user_promoted");
                    break;

                case "demote":
                    // Rimuovi privilegi admin (ma non permettere di rimuovere se stesso)
                    if (userEmail.equals(currentUser.getEmail())) {
                        response.sendRedirect(request.getContextPath() + "/admin/permissions?error=cannot_demote_self");
                        return;
                    }
                    userDAO.doUpdateAdminStatus(userEmail, false);
                    response.sendRedirect(request.getContextPath() + "/admin/permissions?success=user_demoted");
                    break;

                default:
                    response.sendRedirect(request.getContextPath() + "/admin/permissions?error=invalid_action");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/permissions?error=update_failed");
        }
    }
}