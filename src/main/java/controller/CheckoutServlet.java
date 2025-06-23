package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.beans.*;
import model.dao.*;
import java.io.IOException;
import model.dao.IncassiDAO;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // l'utente è loggato?
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // il carrello è vuoto?
        Map<Integer, CartItem> carrello = (Map<Integer, CartItem>) session.getAttribute("carrello");
        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello.jsp?error=empty");
            return;
        }

        // Crea l'ordine
        Order ordine = new Order();
        ordine.setUserId(user.getEmail());

        // Crea indirizzo di spedizione
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(request.getParameter("via"));
        indirizzo.setCitta(request.getParameter("citta"));
        indirizzo.setProvincia(request.getParameter("provincia"));
        indirizzo.setCap(Integer.parseInt(request.getParameter("cap")));
        ordine.setIndirizzoSpedizione(indirizzo);

        // Converti items del carrello in OrderItems
        double totale = 0;
        for (CartItem item : carrello.values()) {
            OrderItem orderItem = new OrderItem(item.getProdotto(), item.getQuantita(), item.getProdotto().getPrezzo());
            ordine.addItem(orderItem);
            totale += item.getSubtotale();
        }
        ordine.setPrezzo(totale);

        // Salva l'ordine
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.doSave(ordine);

        // Aggiorna incassi
        IncassiDAO incassiDAO = new IncassiDAO();
        incassiDAO.aggiornaIncassi(totale);

        // Svuota il carrello
        carrello.clear();

        response.sendRedirect(request.getContextPath() + "/ordini.jsp?success=order_placed");
    }
}