package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.SpecieAnimale;
import model.dao.SpecieAnimaleDAO;

import java.io.IOException;
import java.util.List;

// Servlet principale per la home page - mostra le specie animali
@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera le specie animali dal ServletContext (caricate all'avvio)
        List<SpecieAnimale> specieAnimali = (List<SpecieAnimale>)
                getServletContext().getAttribute("specieAnimali");

        // Se non ci sono nel context, recupera dal database come fallback
        if (specieAnimali == null || specieAnimali.isEmpty()) {
            SpecieAnimaleDAO dao = new SpecieAnimaleDAO();
            specieAnimali = dao.doRetrieveAll();
        }

        // Mette i dati nel request per la JSP
        request.setAttribute("specieAnimali", specieAnimali);

        // Inoltra alla JSP principale
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}