package model.beans;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int userId;  // ID dell'utente proprietario del carrello
    private List<CartItem> items;
    private double totale;

    public Cart() {
        this.items = new ArrayList<>();
        this.totale = 0.0;
    }

    public Cart(int userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.totale = 0.0;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public double getTotale() { return totale; }

    // Metodi utili
    public void addItem(Prodotto prodotto, int quantita) {
        // Verifica se il prodotto è già nel carrello
        for (CartItem item : items) {
            if (item.getProdotto().getCodice() == prodotto.getCodice()) {
                item.setQuantita(item.getQuantita() + quantita);
                calcolaTotale();
                return;
            }
        }
        // Se non esiste, aggiungi nuovo item
        CartItem nuovoItem = new CartItem(prodotto, quantita);
        items.add(nuovoItem);
        calcolaTotale();
    }

    public void rimuoviItem(int prodottoCodice) {
        items.removeIf(item -> item.getProdotto().getCodice() == prodottoCodice);
        calcolaTotale();
    }

    public void aggiornaQuantita(int prodottoCodice, int nuovaQuantita) {
        for (CartItem item : items) {
            if (item.getProdotto().getCodice() == prodottoCodice) {
                item.setQuantita(nuovaQuantita);
                calcolaTotale();
                return;
            }
        }
    }

    public void svuota() {
        items.clear();
        totale = 0.0;
    }

    private void calcolaTotale() {
        totale = 0.0;
        for (CartItem item : items) {
            totale += item.getSubtotale();
        }
    }

    // Metodo per convertire il carrello in un ordine ("confermandolo")
    public Order creaOrdine() {
        Order ordine = new Order();
        for (CartItem item : items) {
            OrderItem orderItem = new OrderItem(
                    item.getProdotto(),
                    item.getQuantita(),
                    item.getProdotto().getPrezzo()  // Salva il prezzo attuale
            );
            ordine.addItem(orderItem);
        }
        ordine.calcolaPrezzo();
        return ordine;
    }
}