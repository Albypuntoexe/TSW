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
import model.beans.SpecieAnimale;
import model.beans.User;
import model.dao.ProdottoDAO;
import model.dao.SpecieAnimaleDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@WebServlet("/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 15   // 15 MB
)
public class UploadServlet extends HttpServlet {

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
            // Recupera i dati del form
            String nomeAnimale = request.getParameter("nomeAnimale");
            String descrizioneAnimale = request.getParameter("descrizioneAnimale");
            String descrizioneStandard = request.getParameter("descrizioneStandard");
            String descrizionePremium = request.getParameter("descrizionePremium");
            double prezzoStandard = Double.parseDouble(request.getParameter("prezzoStandard"));
            double prezzoPremium = Double.parseDouble(request.getParameter("prezzoPremium"));

            // Recupera i file
            Part immaginePrincipale = request.getPart("fotoAnimale");
            Part immagineProdottoStandard = request.getPart("fotoStandard");
            Part immagineProdottoPremium = request.getPart("fotoPremium");

            // Percorsi per le immagini
            // getRealPath(String path): Questo metodo del ServletContext prende un percorso relativo
            // e lo converte nel percorso assoluto (filesystem) sul server.
            String pathAnimali = getServletContext().getRealPath("/img/animali");
            String pathProdotti = getServletContext().getRealPath("/img/prodotti");

            // Crea le directory se non esistono
            createDirectoryIfNotExists(pathAnimali);
            createDirectoryIfNotExists(pathProdotti);

            // Salva immagine principale dell'animale
            String nomeFileAnimale = saveFile(immaginePrincipale, pathAnimali);
            String urlImageAnimale = "img/animali/" + nomeFileAnimale;

            // Salva immagini prodotti
            String nomeFileProdottoStandard = saveFile(immagineProdottoStandard, pathProdotti);
            String urlImageProdottoStandard = "img/prodotti/" + nomeFileProdottoStandard;

            String nomeFileProdottoPremium = saveFile(immagineProdottoPremium, pathProdotti);
            String urlImageProdottoPremium = "img/prodotti/" + nomeFileProdottoPremium;

            // Salva nel database
            SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
            ProdottoDAO prodottoDAO = new ProdottoDAO();

            // Crea e salva la specie animale
            SpecieAnimale specie = new SpecieAnimale();
            specie.setNome(nomeAnimale);
            specie.setDescrizione(descrizioneAnimale);
            specie.setUrlImage(urlImageAnimale);
            specieDAO.doSave(specie);

            // Crea prodotto Standard
            Prodotto prodottoStandard = new Prodotto();
            prodottoStandard.setSpecieId(specie.getId());
            prodottoStandard.setNome("Kit Standard - " + nomeAnimale);
            prodottoStandard.setPrezzo(prezzoStandard);
            prodottoStandard.setTipo(1); // 1 = Standard
            prodottoStandard.setDescrizione(descrizioneStandard);
            prodottoStandard.setUrlImage(urlImageProdottoStandard);
            prodottoDAO.doSave(prodottoStandard);

            // Crea prodotto Premium
            Prodotto prodottoPremium = new Prodotto();
            prodottoPremium.setSpecieId(specie.getId());
            prodottoPremium.setNome("Kit Premium - " + nomeAnimale);
            prodottoPremium.setPrezzo(prezzoPremium);
            prodottoPremium.setTipo(2); // 2 = Premium
            prodottoPremium.setDescrizione(descrizionePremium);
            prodottoPremium.setUrlImage(urlImageProdottoPremium);
            prodottoDAO.doSave(prodottoPremium);

            // Aggiorna il ServletContext con le nuove specie
            refreshSpecieAnimaliContext();

            // Imposta un attributo di successo e inoltra alla pagina admin
            request.setAttribute("success", "upload");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "upload");
            request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);        }
    }

    private String saveFile(Part filePart, String uploadPath) throws IOException {
        String fileName = extractFileName(filePart);

        Path destinationPath = Paths.get(uploadPath, fileName);

        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
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

    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private void refreshSpecieAnimaliContext() {
        SpecieAnimaleDAO specieDAO = new SpecieAnimaleDAO();
        List<SpecieAnimale> specie = specieDAO.doRetrieveAll();
        getServletContext().setAttribute("specieAnimali", specie);
    }
}