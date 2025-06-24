package model.dao;

import model.beans.User;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User doRetrieveByEmail(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT email, telefono, password, username, nome, cognome, ruolo FROM user WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                );
                user.setAdmin(rs.getBoolean("ruolo"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero utente per email", e);
        }
    }

    public User doRetrieveByUsername(String username) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT email, telefono, password, username, nome, cognome, ruolo FROM user WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                );
                user.setAdmin(rs.getBoolean("ruolo"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero utente per username", e);
        }
    }

    public void doSave(User user) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO user (email, telefono, password, username, nome, cognome, ruolo) VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getTelefono());
            ps.setString(3, user.getPassword()); // già hashata
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getNome());
            ps.setString(6, user.getCognome());
            ps.setBoolean(7, user.isAdmin());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("Errore nell'inserimento utente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel salvataggio utente", e);
        }
    }


    public List<User> doRetrieveAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT email, telefono, password, username, nome, cognome, ruolo FROM user");
            while(rs.next()) {
                User user = new User(
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                );
                user.setAdmin(rs.getBoolean("ruolo"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero di tutti gli utenti", e);
        }
    }


    public void doUpdate(User user) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE user SET telefono=?, password=?, username=?, nome=?, cognome=?, ruolo=? WHERE email=?");
            ps.setString(1, user.getTelefono());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getNome());
            ps.setString(5, user.getCognome());
            ps.setBoolean(6, user.isAdmin());
            ps.setString(7, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'aggiornamento utente", e);
        }
    }


    public void doUpdateAdminStatus(String email, boolean isAdmin) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE user SET ruolo=? WHERE email=?");
            ps.setBoolean(1, isAdmin);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'aggiornamento stato admin", e);
        }
    }

    public void doDelete(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE email=?");
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'eliminazione utente", e);
        }
    }

    public User doRetrieveByUsernamePassword(String username, String unhashedPassword) {
        try (Connection con = ConPool.getConnection()) {
            // Prima recupera l'utente tramite username per ottenere l'hash memorizzato
            PreparedStatement ps = con.prepareStatement(
                    "SELECT email, telefono, password, username, nome, cognome, ruolo FROM user WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                // Verifica la password usando util.PasswordUtil
                if (PasswordUtil.verifyPassword(unhashedPassword, storedHash)) {
                    User user = new User(
                            rs.getString("email"),
                            rs.getString("telefono"),
                            storedHash, // Mantiene l'hash memorizzato nel bean
                            rs.getString("username"),
                            rs.getString("nome"),
                            rs.getString("cognome")
                    );
                    user.setAdmin(rs.getBoolean("ruolo"));
                    return user;
                }
            }
            return null; // Utente non trovato o password errata
        } catch (SQLException e) {
            throw new RuntimeException("Errore nell'autenticazione utente", e);
        }
    }
}