<%@ page import="model.beans.SpecieAnimale, model.beans.Prodotto" %>
<%@ page import="model.dao.SpecieAnimaleDAO, model.dao.ProdottoDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  <title>Dettagli Animale</title>
</head>
<body>
<jsp:include page="stickynavbar.jsp" />

<main class="main-content">
  <%
    int id = Integer.parseInt(request.getParameter("id"));
    SpecieAnimaleDAO animaleDAO = new SpecieAnimaleDAO();
    ProdottoDAO prodottoDAO = new ProdottoDAO();

    SpecieAnimale animale = animaleDAO.doRetrieveById(id);
    List<Prodotto> prodotti = prodottoDAO.doRetrieveBySpecieId(id);

    request.setAttribute("animale", animale);
    request.setAttribute("prodotti", prodotti);
  %>

  <section class="animale-dettagli">
    <h1>${animale.nome}</h1>
    <p>${animale.descrizione}</p>
    <img src="<%=request.getContextPath()%>/${animale.urlImage}" alt="${animale.nome}" class="animale-img">
  </section>

  <section class="prodotti-container">
    <h2>Kit Disponibili</h2>
    <c:forEach var="prodotto" items="${prodotti}">
      <div class="prodotto-card">
        <img src="<%=request.getContextPath()%>/${prodotto.urlImage}" alt="${prodotto.nome}" class="prodotto-img">
        <h3>${prodotto.nome}</h3>
        <p>${prodotto.descrizione}</p>
        <p>Prezzo: €${prodotto.prezzo}</p>
        <form method="post" action="<%=request.getContextPath()%>/cart">
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="codice" value="${prodotto.codice}">
          <input type="number" name="quantita" value="1" min="1" style="width:60px;">
          <button type="submit">Aggiungi al Carrello</button>
        </form>
      </div>
    </c:forEach>
  </section>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>