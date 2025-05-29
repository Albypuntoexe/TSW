package model.beans;

public class Prodotto {
    private String nome;
    private double prezzo;
    private int tipo; //true==bracciale false==peluche
    private int codice,specieId;
    private String urlImage,descrizione;

    public Prodotto(String nome, double prezzo, int tipo, int codice, String urlImage,String descrizione, int specieId) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.codice = codice;
        this.urlImage = urlImage;
        this.descrizione = descrizione;
        this.specieId = specieId;
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
    public String getUrlImage() { return urlImage; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione;}
    public void setSpecieId(int id) { }
    public int getSpecieId() { return specieId;}
}
