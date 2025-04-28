package model.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Date dataOrdine;
    private double prezzo;
    private boolean spedito, ricevuto;
    private Indirizzo indirizzoSpedizione; //indirizzo usa la classe indirizzo
    private List<OrderItem> items;  // Lista di OrderItem invece di Prodotto

    public Order() {
        this.items = new ArrayList<>();
        this.dataOrdine = new Date();  // Imposta data corrente
    }

    // Getter e setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Date dataOrdine) { this.dataOrdine = dataOrdine; }
    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }
    public boolean isSpedito() { return spedito; }
    public void setSpedito(boolean spedito) { this.spedito = spedito; }
    public boolean isRicevuto() { return ricevuto; }
    public void setRicevuto(boolean ricevuto) { this.ricevuto = ricevuto; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
    public Indirizzo getIndirizzoSpedizione() { return indirizzoSpedizione; }
    public void setIndirizzoSpedizione(Indirizzo indirizzoSpedizione) { this.indirizzoSpedizione = indirizzoSpedizione; }

    // Metodi di utilità
    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    public void calcolaPrezzo() {
        double totale = 0;
        for (OrderItem item : items) {
            totale += item.getSubtotale();
        }
        this.prezzo = totale;
    }
}