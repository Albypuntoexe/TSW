package model.beans;

public class OrderItem {
    private int id;
    private int orderId;
    private Prodotto prodotto;
    private int quantita;
    private double prezzoUnitario;  // Prezzo al momento dell'acquisto

    public OrderItem() {}

    public OrderItem(Prodotto prodotto, int quantita, double prezzoUnitario) {
        this.prodotto = prodotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    // Getter e setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }

    // Metodo per calcolare il subtotale
    public double getSubtotale() {
        return prezzoUnitario * quantita;
    }
}