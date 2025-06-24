package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncassiDAO {
    public void aggiornaIncassi(double importo) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE incassi SET totale_incassato = totale_incassato + ? WHERE id = 1"
            );
            ps.setDouble(1, importo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public double getTotaleIncassi() {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT totale_incassato FROM incassi WHERE id = 1"
            );
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("totale_incassato");
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setIncassiIniziali(double totale) {
        // La query aggiorna la colonna 'totale_incassato'.
        // La clausola WHERE id = 1 assicura che venga aggiornato solo il record corretto.
        String query = "UPDATE incassi SET totale_incassato = ? WHERE id = 1";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // Imposta il parametro della query (?) con il valore del totale
            ps.setDouble(1, totale);

            // Esegue l'aggiornamento
            ps.executeUpdate();

        } catch (SQLException e) {
            // In caso di errore SQL, stampa lo stack trace per il debug
            e.printStackTrace();
        }
    }

}
