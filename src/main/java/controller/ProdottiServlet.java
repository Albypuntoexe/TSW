package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.beans.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/show-prodotti")
public class ProdottiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();

        if (prodotti == null || prodotti.isEmpty()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/error.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("prodotti", prodotti);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/prodotti.jsp");
            dispatcher.forward(request, response);
        }
    }
}
