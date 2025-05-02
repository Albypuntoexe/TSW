package model.dao;

import model.beans.SpecieAnimale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecieAnimaleDAO {

    // Recupera tutte le specie animali
    public List<SpecieAnimale> doRetrieveAll() {
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM specie_animale");
            List<SpecieAnimale> specieAnimali = new ArrayList<>();
            while (rs.next()) {
                SpecieAnimale s = new SpecieAnimale();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setPrezzo(rs.getDouble("prezzo"));
                s.setUrlImage(rs.getString("url_image"));
                specieAnimali.add(s);
            }
            return specieAnimali;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera una specie animale dal suo ID
    public SpecieAnimale doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM specie_animale WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                SpecieAnimale s = new SpecieAnimale();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setPrezzo(rs.getDouble("prezzo"));
                s.setUrlImage(rs.getString("url_image"));
                return s;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Ricerca specie animali per nome (pattern matching)
    public List<SpecieAnimale> doRetrieveByNome(String nome) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM specie_animale WHERE nome LIKE ?");
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            List<SpecieAnimale> specieAnimali = new ArrayList<>();
            while (rs.next()) {
                SpecieAnimale s = new SpecieAnimale();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescrizione(rs.getString("descrizione"));
                s.setPrezzo(rs.getDouble("prezzo"));
                s.setUrlImage(rs.getString("url_image"));
                specieAnimali.add(s);
            }
            return specieAnimali;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Salva una nuova specie animale
    public void doSave(SpecieAnimale specieAnimale) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO specie_animale (nome, descrizione, prezzo, url_image) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, specieAnimale.getNome());
            ps.setString(2, specieAnimale.getDescrizione());
            ps.setDouble(3, specieAnimale.getPrezzo());
            ps.setString(4, specieAnimale.getUrlImage());
            ps.executeUpdate();

            // Recupera l'ID generato automaticamente
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                specieAnimale.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Aggiorna una specie animale esistente
    public void doUpdate(SpecieAnimale specieAnimale) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE specie_animale SET nome=?, descrizione=?, prezzo=?, url_image=? WHERE id=?"
            );
            ps.setString(1, specieAnimale.getNome());
            ps.setString(2, specieAnimale.getDescrizione());
            ps.setDouble(3, specieAnimale.getPrezzo());
            ps.setString(4, specieAnimale.getUrlImage());
            ps.setInt(5, specieAnimale.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Elimina una specie animale
    public void doDelete(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM specie_animale WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
