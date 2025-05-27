<%@ page import="model.beans.SpecieAnimale, model.beans.Prodotto" %>
<%@ page import="model.dao.SpecieAnimaleDAO, model.dao.ProdottoDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="css/style.css">
  <title>Dettagli Animale</title>
</head>
<body>
<!-- Navbar -->
<nav class="sticky-navbar">
    <span class="logo">
        <a href="index.jsp">
            <img src="./img/logo.png" alt="Logo">
        </a>
    </span>
  <div class="nav-right">
    <a href="carrello.jsp" class="cart-icon">
      <img src="./img/carrello.png" alt="Carrello">
    </a>
    <span class="dropdown">
            <button class="dropbtn">☰</button>
            <span class="dropdown-content">
                <a href="area-personale.jsp">Area Personale</a>
                <a href="ordini.jsp">Ordini</a>
                <a href="contatti.jsp">Contatti</a>
            </span>
        </span>
  </div>
</nav>

<main class="main-content">
  <%
    int id = Integer.parseInt(request.getParameter("id"));
    SpecieAnimaleDAO animaleDAO = new SpecieAnimaleDAO();
    ProdottoDAO prodottoDAO = new ProdottoDAO();

    SpecieAnimale animale = animaleDAO.doRetrieveById(id);
    List<Prodotto> prodotti = prodottoDAO.doRetrieveBySpecieId(id);
  %>

  <!-- Dettagli dell'animale -->
  <section class="animale-dettagli">
    <h1><%= animale.getNome() %></h1>
    <p><%= animale.getDescrizione() %></p>
    <img src="<%= animale.getUrlImage() %>" alt="<%= animale.getNome() %>" class="animale-img">
  </section>

  <!-- Prodotti dell'animale -->
  <section class="prodotti-container">
    <h2>Kit Disponibili</h2>
    <c:forEach var="prodotto" items="${prodotti}">
      <div class="prodotto-card">
        <img src="${prodotto.urlImage}" alt="${prodotto.nome}" class="prodotto-img">
        <h3>${prodotto.nome}</h3>
        <p>${prodotto.descrizione}</p>
        <p>Prezzo: €${prodotto.prezzo}</p>
        <button onclick="location.href='carrello.jsp?codice=${prodotto.codice}'">Aggiungi al Carrello</button>
      </div>
    </c:forEach>
  </section>
</main>

<!-- Footer -->
<footer class="footer">
  <div class="footer-container">
    <div class="footer-content">
      <div class="footer-section">
        <h3>Chi siamo</h3>
        <p>Organizzazione dedicata alla protezione delle specie animali a rischio estinzione.</p>
      </div>
      <div class="footer-section">
        <h3>Link Utili</h3>
        <ul class="footer-links">
          <li><a href="privacy.jsp">Privacy Policy</a></li>
          <li><a href="termini.jsp">Termini e Condizioni</a></li>
          <li><a href="spedizioni.jsp">Politica di Spedizione</a></li>
          <li><a href="contatti.jsp">Contatti</a></li>
        </ul>
      </div>
      <div class="footer-section">
        <h3>Contatti</h3>
        <div class="contact-info">
          <p>📧 info@adopta.org</p>
          <p>📞 +39 341 145 3486</p>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2025 Supporta Specie a Rischio. Tutti i diritti riservati.</p>
    </div>
  </div>
</footer>
</body>
</html>