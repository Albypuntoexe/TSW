package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.beans.SpecieAnimale;
import model.dao.SpecieAnimaleDAO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@WebServlet("/doUpdateSpecie")
@MultipartConfig
public class DoUpdateSpecieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // getta gli attributi dalla richiesta
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome_" + id);
        String descrizione = request.getParameter("descrizione_" + id);
        Part nuovaImmagine = request.getPart("nuovaImmagine_" + id);

        // crea un istanza per poi modificare la specie con lo specifico id
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        SpecieAnimale specie = specieDAO.doRetrieveById(id);

        //setta i nuovi valori
        specie.setNome(nome);
        specie.setDescrizione(descrizione);

        // Aggiorna immagine solo se ne è stata fornita una nuova
        if (nuovaImmagine != null && nuovaImmagine.getSize() > 0) {
            String pathAnimali = getServletContext().getRealPath("/img/animali");
            String fileName = extractFileName(nuovaImmagine);
            Path destinationPath = Paths.get(pathAnimali, fileName);

            try (InputStream inputStream = nuovaImmagine.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }

            specie.setUrlImage("img/animali/" + fileName);
        }

        // Aggiorna la specie nel database
        specieDAO.doUpdate(specie);

        // Aggiorna il contesto
        refreshSpecieAnimaliContext();
        request.setAttribute("success", "specie_updated");
        request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    private void refreshSpecieAnimaliContext() {
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        List<SpecieAnimale> specie = specieDAO.doRetrieveAll();
        getServletContext().setAttribute("specieAnimali", specie);
    }
}