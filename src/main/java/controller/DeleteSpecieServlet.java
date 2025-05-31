package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.Prodotto;
import model.beans.SpecieAnimale;
import model.dao.ProdottoDAO;
import model.dao.SpecieAnimaleDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/deleteSpecie")
public class DeleteSpecieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        // Elimina prima i prodotti associati
        List<Prodotto> prodotti = prodottoDAO.doRetrieveBySpecieId(id);
        for (Prodotto p : prodotti) {
            prodottoDAO.doDelete(p.getCodice());
        }

        // Poi elimina la specie
        specieDAO.doDelete(id);

        // Aggiorna la lista nel contesto
        refreshSpecieAnimaliContext();

        request.setAttribute("success", "specie_deleted");
        request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    private void refreshSpecieAnimaliContext() {
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        List<SpecieAnimale> specie = specieDAO.doRetrieveAll();
        getServletContext().setAttribute("specieAnimali", specie);
    }
}