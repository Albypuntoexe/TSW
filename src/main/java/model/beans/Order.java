package model.beans;

public class Order {
    private int id,tipokit; //0 per bracciale 1 per peluche
    private double prezzo;
    private boolean spedito,ricevuto;

    public Order() {}
    public int getId() { return id; }
    public int setId(int id) { this.id = id; return this.id; }
    public int getTipokit() { return tipokit; }
    public void setTipokit(int tipokit) { this.tipokit = tipokit; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public boolean isSpedito() { return spedito; }
    public void setSpedito(boolean spedito) { this.spedito = spedito; }
    public boolean isRicevuto() { return ricevuto; }
    public  void setRicevuto(boolean ricevuto) { this.ricevuto = ricevuto; }
}
