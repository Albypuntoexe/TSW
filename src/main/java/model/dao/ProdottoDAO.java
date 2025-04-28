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
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
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
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
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
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Ricerca prodotti per nome (pattern matching)
    public List<Prodotto> doRetrieveByNome(String nome) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE nome LIKE ?");
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera prodotti con prezzo in un certo range
    public List<Prodotto> doRetrieveByPrezzoRange(double min, double max) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE prezzo BETWEEN ? AND ?");
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            ResultSet rs = ps.executeQuery();
            List<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setCodice(rs.getInt("codice"));
                p.setNome(rs.getString("nome"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setTipo(rs.getInt("tipo"));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Salva un nuovo prodotto
    public void doSave(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotto (nome, prezzo, tipo) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setInt(3, prodotto.getTipo());
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

    // Aggiorna un prodotto esistente
    public void doUpdate(Prodotto prodotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotto SET nome=?, prezzo=?, tipo=? WHERE codice=?"
            );
            ps.setString(1, prodotto.getNome());
            ps.setDouble(2, prodotto.getPrezzo());
            ps.setInt(3, prodotto.getTipo());
            ps.setInt(4, prodotto.getCodice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Elimina un prodotto
    public void doDelete(int codice) {
        try (Connection con = ConPool.getConnection()) {
            // Prima eliminiamo tutti i riferimenti al prodotto nei carrelli
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM cartitem WHERE prodotto_codice=?");
            ps1.setInt(1, codice);
            ps1.executeUpdate();

            // Poi eliminiamo il prodotto
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM prodotto WHERE codice=?");
            ps2.setInt(1, codice);
            ps2.executeUpdate();
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
