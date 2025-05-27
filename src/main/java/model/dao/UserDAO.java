package model.dao;

import model.beans.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


//ho chiesto a claude di generare questo codice sulla base du CUSTOMERDAO.java del professore
//il metodo doauthenticate serve per:
/*Taking a username/email and password as input
Hashing the provided password using the same SHA-1 algorithm as your User class
Checking if there's a user in the database with matching credentials
Returning the complete User object if found, or null if authentication fails*/

/*todo tradurre tutti i commenti in italiano */
public class UserDAO {

    /**
     * Finds the user with the given email.
     * Returns null if there is no match.
     */
    public User doRetrieveByEmail(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT email, telefono, password, username, nome, cognome FROM user WHERE email=?");
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
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the user with the given username.
     * Returns null if there is no match.
     */
    public User doRetrieveByUsername(String username) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT email, telefono, password, username, nome, cognome FROM user WHERE username=?");
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
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves a new user to the database.
     * Note: Password should already be hashed before creating the User object.
     */
    public void doSave(User user) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO user (email, telefono, password, username, nome, cognome) VALUES(?,?,?,?,?,?)");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getTelefono());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getNome());
            ps.setString(6, user.getCognome());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all users from the database.
     */
    public List<User> doRetrieveAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT email, telefono, password, username, nome, cognome FROM user");
            while(rs.next()) {
                User user = new User(
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing user in the database.
     */
    public void doUpdate(User user) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE user SET telefono=?, password=?, username=?, nome=?, cognome=? WHERE email=?");
            ps.setString(1, user.getTelefono());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getNome());
            ps.setString(5, user.getCognome());
            ps.setString(6, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a user from the database.
     */
    public void doDelete(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM user WHERE email=?");
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if the provided credentials are valid for login.
     * Returns the User if credentials are valid, null otherwise.
     */
    public User doAuthenticate(String emailOrUsername, String password) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT email, telefono, password, username, nome, cognome FROM user " +
                            "WHERE (email=? OR username=?) AND password=?");
            ps.setString(1, emailOrUsername);
            ps.setString(2, emailOrUsername);

            // Hash the password to compare with stored hash
            try {
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
                digest.reset();
                digest.update(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                String hashedPassword = String.format("%040x", new java.math.BigInteger(1, digest.digest()));
                ps.setString(3, hashedPassword);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

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
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doRetrieveByUsernamePassword(String username, String password) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT username FROM user WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Returns true if a matching user is found
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}