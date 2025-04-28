package model.beans;

public class SpecieAnimale {
    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;

    //private String urlimage;
    //penso anche l'url ma poi vediamo
    /*todo*/

    public SpecieAnimale(){}
    public int setId(int id){ return this.id = id; }
    public int getId(){ return this.id; }
    public void setNome(String nome){ this.nome = nome; }
    public String getNome(){ return this.nome; }
    public void setDescrizione(String descrizione){ this.descrizione = descrizione; }
    public String getDescrizione(){ return this.descrizione; }
    public void setPrezzo(double prezzo){ this.prezzo = prezzo; }
    public double getPrezzo(){ return this.prezzo; }
    }
