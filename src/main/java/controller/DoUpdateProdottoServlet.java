package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.beans.Prodotto;
import model.beans.User;
import model.dao.ProdottoDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/doUpdateProdotto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 15   // 15 MB
)
public class DoUpdateProdottoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica se l'utente è admin
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/?error=access_denied");
            return;
        }

        try {
            // Recupera i parametri dal form
            int codice = Integer.parseInt(request.getParameter("codice"));
            int specieId = Integer.parseInt(request.getParameter("specieId"));
            int tipo = Integer.parseInt(request.getParameter("tipo"));
            String nome = request.getParameter("nome_" + codice);
            double prezzo = Double.parseDouble(request.getParameter("prezzo_" + codice));
            String descrizione = request.getParameter("descrizione_" + codice);
            Part nuovaImmagine = request.getPart("nuovaImmagine_" + codice);

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto prodotto = prodottoDAO.doRetrieveByCodice(codice);

            if (prodotto == null) {
                request.setAttribute("error", "Prodotto non trovato");
                request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
                return;
            }

            // Aggiorna i dati del prodotto
            prodotto.setNome(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setDescrizione(descrizione);
            prodotto.setSpecieId(specieId);
            prodotto.setTipo(tipo);

            // Gestisce l'aggiornamento dell'immagine se fornita
            if (nuovaImmagine != null && nuovaImmagine.getSize() > 0) {
                String pathProdotti = getServletContext().getRealPath("/img/prodotti");
                createDirectoryIfNotExists(pathProdotti);

                String fileName = extractFileName(nuovaImmagine);
                Path destinationPath = Paths.get(pathProdotti, fileName);

                try (InputStream inputStream = nuovaImmagine.getInputStream()) {
                    Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                }

                prodotto.setUrlImage("img/prodotti/" + fileName);
            }

            // Salva le modifiche nel database
            prodottoDAO.doUpdate(prodotto);


            request.setAttribute("success", "prodotto_updated");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nei dati numerici forniti");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore durante l'aggiornamento del prodotto: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                // Rimuove eventuali caratteri problematici dal nome file
                return fileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            }
        }
        return "unnamed_file";
    }

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}