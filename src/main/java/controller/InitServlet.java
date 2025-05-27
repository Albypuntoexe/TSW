package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.CategoriaDAO;

@WebServlet(name = "MyInit", urlPatterns = "/MyInit", loadOnStartup = 0)
public class InitServlet extends HttpServlet {

    public void init() throws ServletException {
        super.init();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        getServletContext().setAttribute("categorie", categoriaDAO.doRetrieveAll());
    }
}
