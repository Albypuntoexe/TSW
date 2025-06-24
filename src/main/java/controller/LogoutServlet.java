package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // usa l'attributo isAdmin per verificare se l'utente è loggato
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        if (isAdmin == null){
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        //  Rimuove e "dimentica" immediatamente tutti gli oggetti che erano stati salvati, cioè user e carrello
        // invalidando il token
        //  Quando un utente effettua il login con successo, la LoginServlet chiama request.getSession().
        //  In quel momento, se non esiste già una sessione, Tomcat genera un token
        request. getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}