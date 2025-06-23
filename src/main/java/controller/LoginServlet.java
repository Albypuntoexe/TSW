package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

        //usa userdao per verificare le credenziali
        UserDAO userDAO = new UserDAO();
        User user = userDAO.doRetrieveByUsernamePassword(username, password);

        if (user != null) {
            //se tutto ok, crea una sessione e l'associa all'utente
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isAdmin", user.isAdmin());

            System.out.println("Utente loggato: " + user.getUsername());
            System.out.println("ID sessione: " + session.getId());
            System.out.println("È admin: " + user.isAdmin());

            String ricordami = request.getParameter("ricordami");
            if (ricordami != null && ricordami.equals("si")) {
                // L'utente ha spuntato la casella "Ricordami"
                // 1. Crea un nuovo oggetto Cookie
                Cookie userCookie = new Cookie("saved_username", username);

                // 2. Imposta la durata del cookie a 7 giorni
                userCookie.setMaxAge(604800);

                // 3. Aggiungi il cookie alla risposta HTTP
                response.addCookie(userCookie);

            } else {
                // L'utente non ha spuntato la casella, potremmo voler eliminare un cookie esistente
                // 1. Crea un cookie con lo stesso nome
                Cookie userCookie = new Cookie("saved_username", null);

                // 2. Imposta la sua durata a 0 per eliminarlo dal browser
                userCookie.setMaxAge(0);

                // 3. Aggiungi il cookie "di eliminazione" alla risposta
                response.addCookie(userCookie);
            }
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
