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

@WebServlet("/prodotto")
public class ProdottoDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            SpecieAnimaleDAO dao = new SpecieAnimaleDAO();
            SpecieAnimale animale = dao.doRetrieveById(id);

            if (animale != null) {
                request.setAttribute("animale", animale);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/prodotto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/?error=not_found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/?error=invalid_id");
        }
    }
}