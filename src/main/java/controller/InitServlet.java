package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.beans.SpecieAnimale;
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
    }
}

