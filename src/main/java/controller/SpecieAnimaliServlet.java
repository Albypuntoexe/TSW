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

@WebServlet("/specie-animali")
public class SpecieAnimaliServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera dal ServletContext o direttamente dal database
        List<SpecieAnimale> specieAnimali = (List<SpecieAnimale>) getServletContext().getAttribute("specieAnimali");

        // Se non ci sono nel context, recupera dal database
        if (specieAnimali == null) {
            SpecieAnimaleDAO dao = new SpecieAnimaleDAO();
            specieAnimali = dao.doRetrieveAll();
        }

        request.setAttribute("specieAnimali", specieAnimali);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/results/result.jsp");
        dispatcher.forward(request, response);
    }
}
