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
<jsp:include page="stickynavbar.jsp" />

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

<jsp:include page="footer.jsp" />

</body>
</html>