<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map, model.beans.CartItem" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <title>Carrello | Adopta</title>
</head>
<body>
<jsp:include page="stickynavbar.jsp" />

<main class="main-content">
    <h1>Il tuo Carrello</h1>
    <%
        // Ottieni il carrello dalla sessione. Se non esiste, crea una nuova mappa vuota.
        Map<Integer, CartItem> carrello = (Map<Integer, CartItem>) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new java.util.HashMap<>();
            session.setAttribute("carrello", carrello); // Assicurati che un carrello vuoto sia sempre in sessione
        }

        // Inizializza la variabile 'totale'
        double totale = 0;

        // Il blocco di codice qui sotto gestirà la visualizzazione
        if (carrello.isEmpty()) { // Usiamo .isEmpty() ora che carrello è sempre un oggetto
    %>
    <div class="empty-cart">
        <h2>Il tuo carrello è vuoto</h2>
        <p>Aggiungi qualche prodotto per iniziare!</p>
        <a href="<%=request.getContextPath()%>/home" class="cta-button">Continua lo Shopping</a>
    </div>
    <%
    } else {
    %>
    <div class="cart-container">
        <div class="cart-items">
            <%
                for (CartItem item : carrello.values()) {
                    totale += item.getSubtotale();
            %>
            <div class="cart-item">
                <img src="<%=request.getContextPath()%>/<%=item.getProdotto().getUrlImage()%>" alt="<%=item.getProdotto().getNome()%>" class="item-img">
                <div class="item-details">
                    <h3><%=item.getProdotto().getNome()%></h3>
                    <p>Prezzo: €<%=String.format("%.2f", item.getProdotto().getPrezzo())%></p>
                    <div class="quantity-controls">
                        <form method="post" action="<%=request.getContextPath()%>/cart">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="codice" value="<%=item.getProdotto().getCodice()%>">
                            <input type="number" name="quantita" value="<%=item.getQuantita()%>" min="1">
                            <button type="submit">Aggiorna</button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/cart">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="codice" value="<%=item.getProdotto().getCodice()%>">
                            <button type="submit" class="remove-btn">Rimuovi</button>
                        </form>
                    </div>
                    <p class="subtotal">Subtotale: €<%=String.format("%.2f", item.getSubtotale())%></p>
                </div>
            </div>
            <% } %>
        </div>

        <div class="cart-summary">
            <h2>Riepilogo Ordine</h2>
            <p class="total">Totale: €<%=String.format("%.2f", totale)%></p>

            <% // Controlla se l'utente è loggato per mostrare il form di checkout
                if (session.getAttribute("user") != null) { %>
            <form method="post" action="<%=request.getContextPath()%>/checkout">
                <h3>Indirizzo di Spedizione</h3>
                <input type="text" name="via" placeholder="Via e numero" required>

                <input type="text" id="provincia" name="provincia" placeholder="Provincia" list="province-list" required>
                <datalist id="province-list"></datalist>

                <input type="text" id="citta" name="citta" placeholder="Città" list="citta-list" required>
                <datalist id="citta-list"></datalist>

                <input type="number" name="cap" placeholder="CAP" required>
                <button type="submit" class="checkout-btn">Ordina e Paga</button>
            </form>
            <% } else { %>
            <p>Devi <a href="<%=request.getContextPath()%>/login">accedere</a> per completare l'ordine</p>
            <% } %>

            <form method="post" action="<%=request.getContextPath()%>/cart">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="clear-btn">Svuota Carrello</button>
            </form>
        </div>
    </div>
    <% } %>
</main>

<jsp:include page="footer.jsp" />

<script>
    let comuniData = null;

    // Carica i dati dei comuni con XMLHttpRequest a pagina caricata
    const xhttp = new XMLHttpRequest();
    // metodo, url e asincrono
    xhttp.open('GET', '<%=request.getContextPath()%>/comuni.json', true);
    // funzione asincrona e non bloccante che richiamata a ogni cambio di readystate
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            // popola la variabile comuniData con i dati JSON ricevuti facendo il parse a un oggetto JavaScript
            comuniData = JSON.parse(xhttp.responseText);

            // Popola datalist delle province con riferimento all'html
            const provinceList = document.getElementById('province-list');
            // Crea un set per evitare duplicati e popola il datalist
            const provinceSet = new Set();
            comuniData.forEach(comune => {
                provinceSet.add(comune.provincia.nome);
            });
            provinceSet.forEach(provincia => {
                const option = document.createElement('option');
                option.value = provincia;
                provinceList.appendChild(option);
            });
        }
    };
    xhttp.send();

    // Quando cambia la provincia, aggiorna le città
    document.getElementById('provincia').addEventListener('input', function() {
        const provincia = this.value;
        const cittaList = document.getElementById('citta-list');

        // Svuota lista città
        cittaList.innerHTML = '';

        if (comuniData) {
            comuniData.filter(comune => comune.provincia.nome === provincia)
                .forEach(comune => {
                    const option = document.createElement('option');
                    option.value = comune.nome;
                    cittaList.appendChild(option);
                });
        }

        // Reset campo città
        document.getElementById('citta').value = '';
    });
</script>

</body>
</html>