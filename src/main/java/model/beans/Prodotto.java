package model.beans;

public class Prodotto {
    private String nome;
    private double prezzo;
    private int tipo; //true==bracciale false==peluche
    private int codice;
    /*todo probabilmente ANCHE L'URL IMG*/

    public Prodotto(String nome, double prezzo, int tipo, int codice) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.codice = codice;
    }
    public Prodotto() {}
    public String getNome() { return nome; }
    public double getPrezzo() { return prezzo; }
    public int getTipo() { return tipo; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public void setTipo(int tipo) { this.tipo = tipo; }
    public void setCodice(int codice) { this.codice = codice; }
    public int getCodice() { return codice; }
}
