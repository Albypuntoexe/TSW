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
        // Gestione aggiunta prodotto da URL
        String codiceParam = request.getParameter("codice");
        if (codiceParam != null) {
            try {
                int codice = Integer.parseInt(codiceParam);
                model.dao.ProdottoDAO dao = new model.dao.ProdottoDAO();
                model.beans.Prodotto prodotto = dao.doRetrieveByCodice(codice);

                if (prodotto != null) {
                    Map<Integer, CartItem> carrello = (Map<Integer, CartItem>) session.getAttribute("carrello");
                    if (carrello == null) {
                        carrello = new java.util.HashMap<>();
                        session.setAttribute("carrello", carrello);
                    }

                    if (carrello.containsKey(codice)) {
                        carrello.get(codice).setQuantita(carrello.get(codice).getQuantita() + 1);
                    } else {
                        carrello.put(codice, new CartItem(prodotto, 1));
                    }
                }
            } catch (NumberFormatException e) {}
        }

        Map<Integer, CartItem> carrello = (Map<Integer, CartItem>) session.getAttribute("carrello");
        double totale = 0;

        if (carrello == null || carrello.isEmpty()) {
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

            <% if (session.getAttribute("user") != null) { %>
            <form method="post" action="<%=request.getContextPath()%>/checkout">
                <h3>Indirizzo di Spedizione</h3>
                <input type="text" name="via" placeholder="Via e numero" required>
                <input type="text" name="citta" placeholder="Città" required>
                <input type="text" name="provincia" placeholder="Provincia" required>
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
</body>
</html>