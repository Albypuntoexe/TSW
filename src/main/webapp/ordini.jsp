<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List, model.beans.*, model.dao.*" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  <title>I miei Ordini | Adopta</title>
</head>
<body>
<jsp:include page="stickynavbar.jsp" />

<main class="main-content">
  <h1>I miei Ordini</h1>

  <%
    User user = (User) session.getAttribute("user");
    if (user == null) {
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    }

    String success = request.getParameter("success");
    if ("order_placed".equals(success)) {
  %>
  <div class="success-message">
    <p>✓ Ordine completato con successo!</p>
  </div>
  <% } %>

  <%
    // Utilizza direttamente l'email dell'utente
    OrderDAO orderDAO = new OrderDAO();
    List<Order> ordini = orderDAO.doRetrieveByUserId(user.getEmail()); // Passa l'email

    if (ordini == null || ordini.isEmpty()) {
  %>
  <div class="no-orders">
    <h2>Non hai ancora effettuato ordini</h2>
    <p>Inizia ad adottare le specie a rischio!</p>
    <a href="<%=request.getContextPath()%>/home" class="cta-button">Vai al Catalogo</a>
  </div>
  <%
  } else {
  %>
  <div class="orders-container">
    <%
      for (Order ordine : ordini) {
    %>
    <div class="order-card">
      <div class="order-header">
        <h3>Ordine #<%=ordine.getId()%></h3>
        <p class="order-date">Data: <%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(ordine.getDataOrdine())%></p>
        <p class="order-status">
          <% if (ordine.isRicevuto()) { %>
          <span class="status-received">✓ Ricevuto</span>
          <% } else if (ordine.isSpedito()) { %>
          <span class="status-shipped">📦 Spedito</span>
          <% } else { %>
          <span class="status-processing">⏳ In elaborazione</span>
          <% } %>
        </p>
      </div>

      <div class="order-items">
        <%
          for (OrderItem item : ordine.getItems()) {
        %>
        <div class="order-item">
          <img src="<%=request.getContextPath()%>/<%=item.getProdotto().getUrlImage()%>" alt="<%=item.getProdotto().getNome()%>" class="item-img">
          <div class="item-info">
            <h4><%=item.getProdotto().getNome()%></h4>
            <p>Quantità: <%=item.getQuantita()%></p>
            <p>Prezzo unitario: €<%=String.format("%.2f", item.getPrezzoUnitario())%></p>
          </div>
        </div>
        <% } %>
      </div>

      <div class="order-shipping">
        <h4>Indirizzo di spedizione:</h4>
        <p><%=ordine.getIndirizzoSpedizione().getVia()%></p>
        <p><%=ordine.getIndirizzoSpedizione().getCitta()%>, <%=ordine.getIndirizzoSpedizione().getProvincia()%> <%=ordine.getIndirizzoSpedizione().getCap()%></p>
      </div>

      <div class="order-total">
        <h3>Totale: €<%=String.format("%.2f", ordine.getPrezzo())%></h3>
      </div>
    </div>
    <!-- Aggiungi dopo il div del totale -->
    <div>
      <% if (!ordine.isSpedito() && !ordine.isRicevuto()) { %>
      <form action="<%=request.getContextPath()%>/user/orders" method="post" style="display:inline;">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="orderId" value="<%=ordine.getId()%>">
        <button type="submit" onclick="return confirm('Sei sicuro di voler eliminare questo ordine?')">
          Elimina Ordine
        </button>
      </form>
      <% } else if (ordine.isSpedito() && !ordine.isRicevuto()) { %>
      <form action="<%=request.getContextPath()%>/user/orders" method="post" style="display:inline;">
        <input type="hidden" name="action" value="receive">
        <input type="hidden" name="orderId" value="<%=ordine.getId()%>">
        <button type="submit">Conferma Ricezione</button>
      </form>
      <% } %>
    </div>
    <% } %>
  </div>
  <%
      }
  %>

</main>

<jsp:include page="footer.jsp" />
</body>
</html>