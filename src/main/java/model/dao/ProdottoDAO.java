package model.dao;

import model.beans.Prodotto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    // Recupera tutti i prodotti
    public List<Prodotto> doRetrieveAll() {
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM prodotto");
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setUrlImage(rs.getString("url_image"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera un prodotto in base all'associazione con la specie animale
    public List<Prodotto> doRetrieveBySpecieId(int specieId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE specie_id = ?");
            ps.setInt(1, specieId);
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setUrlImage(rs.getString("url_image"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera un prodotto dal suo codice
    public Prodotto doRetrieveByCodice(int codice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE codice=?");
            ps.setInt(1, codice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setUrlImage(rs.getString("url_image"));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera prodotti per tipo
    public List<Prodotto> doRetrieveByTipo(int tipo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE tipo=?");
            ps.setInt(1, tipo);
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setUrlImage(rs.getString("url_image"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Ricerca prodotti per nome
    public List<Prodotto> doRetrieveByNome(String nome) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE nome LIKE ?");
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setSpecieId(rs.getInt("specie_id"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setUrlImage(rs.getString("url_image"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Salva un nuovo prodotto
// Correzione del metodo doSave() in ProdottoDAO.java
    public void doSave(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (specie_id, nome, prezzo, tipo, descrizione, url_image) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, prodotto.getSpecieId());        // AGGIUNTO: specie_id
            ps.setString(2, prodotto.getNome());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getTipo());
            ps.setString(5, prodotto.getDescrizione());
            ps.setString(6, prodotto.getUrlImage());
            ps.executeUpdate();

            // Recupera il codice generato automaticamente
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                prodotto.setCodice(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Correzione del metodo doUpdate()
    public void doUpdate(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotto SET specie_id=?, nome=?, prezzo=?, tipo=?, descrizione=?, url_image=? WHERE codice=?"
            );
            ps.setInt(1, prodotto.getSpecieId());     // AGGIUNTO: specie_id
            ps.setString(2, prodotto.getNome());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getTipo());
            ps.setString(5, prodotto.getDescrizione());
            ps.setString(6, prodotto.getUrlImage());
            ps.setInt(7, prodotto.getCodice());       // CORRETTO: posizione del codice
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Elimina un prodotto
    public void doDelete(int codice) {
        String query = "DELETE FROM prodotto WHERE codice = ?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, codice);

            // Esegue la cancellazione.
            // non serve altro codice perchè on delete cascade è già impostato nella tabella
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Ottieni l'ultimo codice inserito
    public int doRetrieveLastCodice() {
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(codice) as max_codice FROM prodotto");
            if (rs.next()) {
                return rs.getInt("max_codice");
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
