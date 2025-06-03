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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/carrello.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        // Ottieni o crea il carrello dalla sessione
        Map<Integer, CartItem> carrello = (Map<Integer, CartItem>) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new HashMap<>();
            session.setAttribute("carrello", carrello);
        }

        if ("add".equals(action)) {
            aggiungiProdotto(request, carrello);
        } else if ("update".equals(action)) {
            aggiornaProdotto(request, carrello);
        } else if ("remove".equals(action)) {
            rimuoviProdotto(request, carrello);
        } else if ("clear".equals(action)) {
            carrello.clear();
        }

        response.sendRedirect(request.getContextPath() + "/carrello.jsp");
    }

    private void aggiungiProdotto(HttpServletRequest request, Map<Integer, CartItem> carrello) {
        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto prodotto = dao.doRetrieveByCodice(codice);

            if (prodotto != null) {
                if (carrello.containsKey(codice)) {
                    CartItem existing = carrello.get(codice);
                    existing.setQuantita(existing.getQuantita() + quantita);
                } else {
                    carrello.put(codice, new CartItem(prodotto, quantita));
                }
            }
        } catch (NumberFormatException e) {
            // Ignora errori di parsing
        }
    }

    private void aggiornaProdotto(HttpServletRequest request, Map<Integer, CartItem> carrello) {
        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            if (quantita <= 0) {
                carrello.remove(codice);
            } else if (carrello.containsKey(codice)) {
                carrello.get(codice).setQuantita(quantita);
            }
        } catch (NumberFormatException e) {
            // Ignora errori di parsing
        }
    }

    private void rimuoviProdotto(HttpServletRequest request, Map<Integer, CartItem> carrello) {
        try {
            int codice = Integer.parseInt(request.getParameter("codice"));
            carrello.remove(codice);
        } catch (NumberFormatException e) {
            // Ignora errori di parsing
        }
    }
}