package model.beans;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.dao.UserDAO;

public class User {
    private String email,telefono;
    private String password; //non so come cifrarla per ora
    private String username;
    private String nome;
    private String cognome;
    private boolean admin = false;

    public User() {}
    public User(String email, String telefono, String password, String username, String nome, String cognome) {
        this.email = email;
        this.telefono = telefono;
        this.password=password;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
    }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getTelefono() { return telefono; }
    public String getPassword() { return password; } //a quanto pare anche se è cifrata funziona secondo claude
    public boolean isAdmin() {return admin;}
    public void setAdmin(boolean admin) {this.admin = admin;}
    public void setPassword(String password) {this.password = password;} //l'hash
}
