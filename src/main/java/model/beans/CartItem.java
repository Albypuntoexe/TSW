package model.beans;

public class CartItem {
    private Prodotto prodotto;
    private int quantita;

    public CartItem() {}

    public CartItem(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    // Getter e setter
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    // Metodo per calcolare il subtotale
    public double getSubtotale() {
        return prodotto.getPrezzo() * quantita;
    }
}