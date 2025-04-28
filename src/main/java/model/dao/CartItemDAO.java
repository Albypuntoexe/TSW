package model.dao;

import model.beans.CartItem;
import model.beans.Prodotto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    // Recupera tutti gli item del carrello di un utente
    public List<CartItem> doRetrieveByUserId(int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT ci.*, p.nome, p.prezzo, p.tipo FROM cartitem ci " +
                            "JOIN prodotto p ON ci.prodotto_codice = p.codice " +
                            "WHERE ci.user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<CartItem> items = new ArrayList<>();
            while (rs.next()) {
                CartItem item = new CartItem();

                // Creiamo l'oggetto prodotto
                Prodotto prodotto = new Prodotto();
                prodotto.setCodice(rs.getInt("prodotto_codice"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setTipo(rs.getInt("tipo"));

                item.setProdotto(prodotto);
                item.setQuantita(rs.getInt("quantita"));

                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera un CartItem specifico tramite userId e prodottoCodice
    public CartItem doRetrieveByUserIdAndProdottoCodice(int userId, int prodottoCodice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT ci.*, p.nome, p.prezzo, p.tipo FROM cartitem ci " +
                            "JOIN prodotto p ON ci.prodotto_codice = p.codice " +
                            "WHERE ci.user_id=? AND ci.prodotto_codice=?"
            );
            ps.setInt(1, userId);
            ps.setInt(2, prodottoCodice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CartItem item = new CartItem();

                // Creiamo l'oggetto prodotto
                Prodotto prodotto = new Prodotto();
                prodotto.setCodice(rs.getInt("prodotto_codice"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setTipo(rs.getInt("tipo"));

                item.setProdotto(prodotto);
                item.setQuantita(rs.getInt("quantita"));

                return item;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Aggiunge un item al carrello
    public void doSave(CartItem item, int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO cartitem (user_id, prodotto_codice, quantita) VALUES (?, ?, ?)"
            );
            ps.setInt(1, userId);
            ps.setInt(2, item.getProdotto().getCodice());
            ps.setInt(3, item.getQuantita());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Aggiorna la quantità di un prodotto nel carrello
    public void doUpdate(CartItem item, int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE cartitem SET quantita=? WHERE user_id=? AND prodotto_codice=?"
            );
            ps.setInt(1, item.getQuantita());
            ps.setInt(2, userId);
            ps.setInt(3, item.getProdotto().getCodice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Rimuove un prodotto dal carrello
    public void doDelete(int userId, int prodottoCodice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM cartitem WHERE user_id=? AND prodotto_codice=?"
            );
            ps.setInt(1, userId);
            ps.setInt(2, prodottoCodice);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Svuota completamente il carrello di un utente
    public void doDeleteAllByUserId(int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM cartitem WHERE user_id=?"
            );
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica se un prodotto è già presente nel carrello di un utente
    public boolean doCheckProductExists(int userId, int prodottoCodice) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) FROM cartitem WHERE user_id=? AND prodotto_codice=?"
            );
            ps.setInt(1, userId);
            ps.setInt(2, prodottoCodice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Conta il numero di prodotti nel carrello di un utente
    public int doCountItemsByUserId(int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) FROM cartitem WHERE user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Calcola il prezzo totale di tutti gli elementi nel carrello
    public double doCalculateTotalPrice(int userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT SUM(p.prezzo * ci.quantita) as total " +
                            "FROM cartitem ci " +
                            "JOIN prodotto p ON ci.prodotto_codice = p.codice " +
                            "WHERE ci.user_id=?"
            );
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}