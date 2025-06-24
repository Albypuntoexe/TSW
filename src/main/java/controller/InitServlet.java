package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.beans.SpecieAnimale;
import model.dao.IncassiDAO;
import model.dao.OrderDAO;
import model.dao.SpecieAnimaleDAO;

import java.util.List;

// Servlet di inizializzazione per caricare le specie animali all'avvio
@WebServlet(name = "InitServlet", urlPatterns = "/InitServlet", loadOnStartup = 0)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        List<SpecieAnimale> specie = specieDAO.doRetrieveAll();
        getServletContext().setAttribute("specieAnimali", specie);

        // 1. Istanzia i DAO necessari
        OrderDAO orderDAO = new OrderDAO();
        IncassiDAO incassiDAO = new IncassiDAO();

        // 2. Calcola il totale degli ordini pre-esistenti
        double totaleOrdiniPreesistenti = orderDAO.getTotalePrezziOrdini(); // Metodo creato al Passo 1

        // 3. Imposta questo totale come valore iniziale nella tabella incassi
        incassiDAO.setIncassiIniziali(totaleOrdiniPreesistenti); // Metodo creato al Passo 2
    }
}

