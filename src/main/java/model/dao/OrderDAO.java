package model.dao;

import model.beans.Order;
import model.beans.OrderItem;
import model.beans.Indirizzo;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAO {

    // Recupera tutti gli ordini
    public List<Order> doRetrieveAll() {
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM orders");
            List<Order> ordini = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getString("user_id"));
                o.setDataOrdine(new Date(rs.getTimestamp("data_ordine").getTime()));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setSpedito(rs.getBoolean("spedito"));
                o.setRicevuto(rs.getBoolean("ricevuto"));

                // Recupera l'indirizzo di spedizione
                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(rs.getString("indirizzo_via"));
                indirizzo.setCitta(rs.getString("indirizzo_citta"));
                indirizzo.setProvincia(rs.getString("indirizzo_provincia"));
                indirizzo.setCap(rs.getInt("indirizzo_cap"));
                o.setIndirizzoSpedizione(indirizzo);

                // Recupera gli items dell'ordine
                o.setItems(doRetrieveOrderItems(o.getId()));

                ordini.add(o);
            }
            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera un ordine per ID
    public Order doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getString("user_id"));
                o.setDataOrdine(new Date(rs.getTimestamp("data_ordine").getTime()));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setSpedito(rs.getBoolean("spedito"));
                o.setRicevuto(rs.getBoolean("ricevuto"));

                // Recupera l'indirizzo di spedizione
                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(rs.getString("indirizzo_via"));
                indirizzo.setCitta(rs.getString("indirizzo_citta"));
                indirizzo.setProvincia(rs.getString("indirizzo_provincia"));
                indirizzo.setCap(rs.getInt("indirizzo_cap"));
                o.setIndirizzoSpedizione(indirizzo);

                // Recupera gli items dell'ordine
                o.setItems(doRetrieveOrderItems(o.getId()));

                return o;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera ordini per userId
    public List<Order> doRetrieveByUserId(String userId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE user_id=?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Order> ordini = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getString("user_id"));
                o.setDataOrdine(new Date(rs.getTimestamp("data_ordine").getTime()));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setSpedito(rs.getBoolean("spedito"));
                o.setRicevuto(rs.getBoolean("ricevuto"));

                // Recupera l'indirizzo di spedizione
                Indirizzo indirizzo = new Indirizzo();
                indirizzo.setVia(rs.getString("indirizzo_via"));
                indirizzo.setCitta(rs.getString("indirizzo_citta"));
                indirizzo.setProvincia(rs.getString("indirizzo_provincia"));
                indirizzo.setCap(rs.getInt("indirizzo_cap"));
                o.setIndirizzoSpedizione(indirizzo);

                // Recupera gli items dell'ordine
                o.setItems(doRetrieveOrderItems(o.getId()));

                ordini.add(o);
            }
            return ordini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Recupera gli OrderItem di un ordine specifico
    private List<OrderItem> doRetrieveOrderItems(int orderId) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT oi.*, p.nome, p.prezzo, p.tipo FROM orderitem oi " +
                            "JOIN prodotto p ON oi.prodotto_codice = p.codice " +
                            "WHERE oi.order_id=?"
            );
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            List<OrderItem> items = new ArrayList<>();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));

                // Creiamo l'oggetto prodotto
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                item.setProdotto(prodottoDAO.doRetrieveByCodice(rs.getInt("prodotto_codice")));

                item.setQuantita(rs.getInt("quantita"));
                item.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Salva un nuovo ordine e i suoi items
    public void doSave(Order order) {
        Connection con = null;
        try {
            con = ConPool.getConnection();
            // Disabilita l'autocommit per gestire la transazione
            con.setAutoCommit(false);

            // Inserisci l'ordine
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO orders (user_id, data_ordine, prezzo, spedito, ricevuto, " +
                            "indirizzo_via, indirizzo_citta, indirizzo_provincia, indirizzo_cap) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, order.getUserId());
            ps.setTimestamp(2, new Timestamp(order.getDataOrdine().getTime()));
            ps.setDouble(3, order.getPrezzo());
            ps.setBoolean(4, order.isSpedito());
            ps.setBoolean(5, order.isRicevuto());

            // Indirizzo di spedizione
            Indirizzo indirizzo = order.getIndirizzoSpedizione();
            ps.setString(6, indirizzo.getVia());
            ps.setString(7, indirizzo.getCitta());
            ps.setString(8, indirizzo.getProvincia());
            ps.setInt(9, indirizzo.getCap());

            ps.executeUpdate();

            // Recupera l'ID generato
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
            }

            // Inserisci gli OrderItem
            for (OrderItem item : order.getItems()) {
                PreparedStatement psItem = con.prepareStatement(
                        "INSERT INTO orderitem (order_id, prodotto_codice, quantita, prezzo_unitario) " +
                                "VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                psItem.setInt(1, order.getId());
                psItem.setInt(2, item.getProdotto().getCodice());
                psItem.setInt(3, item.getQuantita());
                psItem.setDouble(4, item.getPrezzoUnitario());

                psItem.executeUpdate();

                // Recupera l'ID generato per l'item
                ResultSet rsItem = psItem.getGeneratedKeys();
                if (rsItem.next()) {
                    item.setId(rsItem.getInt(1));
                }
                item.setOrderId(order.getId());
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Aggiorna lo stato di spedizione di un ordine
    public void doUpdateShippingStatus(int orderId, boolean spedito) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE orders SET spedito=? WHERE id=?"
            );
            ps.setBoolean(1, spedito);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Aggiorna lo stato di ricezione di un ordine
    public void doUpdateReceivingStatus(int orderId, boolean ricevuto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE orders SET ricevuto=? WHERE id=?"
            );
            ps.setBoolean(1, ricevuto);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Elimina un ordine e i relativi items
    public void doDelete(int orderId) {
        Connection con = null;
        try {
            con = ConPool.getConnection();
            con.setAutoCommit(false);

            // Prima elimina tutti gli OrderItem associati
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM orderitem WHERE order_id=?");
            ps1.setInt(1, orderId);
            ps1.executeUpdate();

            // Poi elimina l'ordine
            PreparedStatement ps2 = con.prepareStatement("DELETE FROM orders WHERE id=?");
            ps2.setInt(1, orderId);
            ps2.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}