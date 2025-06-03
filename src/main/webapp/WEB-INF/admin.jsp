<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="userDAO" class="model.dao.UserDAO" scope="page" />
<!DOCTYPE html>
<html>
<head>
  <title>Pannello Admin - Adozione Animali</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<%@ include file="/stickynavbar.jsp" %>

<header>
  <h1>Pannello Amministratore</h1>
  <nav>
    <a href="/">Home</a>
    <a href="logout">Logout</a>
  </nav>
  <p>Benvenuto, ${sessionScope.user.nome}!</p>
</header>

<main>
  <h2>Aggiungi Nuovo Animale e Prodotti</h2>

  <!-- Messaggi di successo/errore -->
  <c:if test="${param.success == '1'}">
    <div style="color: green; margin: 10px 0;">
      <strong>Successo:</strong> Animale e prodotti aggiunti correttamente!
    </div>
  </c:if>
  <c:if test="${param.error != null && param.error != ''}">
    <div style="color: red; margin: 10px 0;">
      <strong>Errore:</strong> ${param.error}
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">    <fieldset>
      <legend>Dati Animale</legend>

      <div>
        <label for="nomeAnimale">Nome Animale: *</label>
        <input type="text" id="nomeAnimale" name="nomeAnimale" required>
      </div>

      <div>
        <label for="descrizioneAnimale">Descrizione Animale: *</label>
        <textarea id="descrizioneAnimale" name="descrizioneAnimale" rows="4" required></textarea>
      </div>

      <div>
        <label for="fotoAnimale">Foto Animale: *</label>
        <input type="file" id="fotoAnimale" name="fotoAnimale" accept="image/*" required>
        <small>Verrà salvata in: img/animali/</small>
      </div>
    </fieldset>

    <fieldset>
      <legend>Kit Standard</legend>
      <!-- Aggiungi questo campo per la descrizione -->
      <div>
        <label for="descrizioneStandard">Descrizione Kit Standard: *</label>
        <textarea id="descrizioneStandard" name="descrizioneStandard" rows="3" required>Foto dell'animale adottato, bracciale e certificato di adozione</textarea>
      </div>

      <div>
        <label for="prezzoStandard">Prezzo Kit Standard (€): *</label>
        <input type="number" id="prezzoStandard" name="prezzoStandard" value="25.00" min="0" step="0.01" required>
      </div>
      <div>
        <label for="fotoStandard">Foto Kit Standard: *</label>
        <input type="file" id="fotoStandard" name="fotoStandard" accept="image/*" required>
      </div>
    </fieldset>

    <fieldset>
      <legend>Kit Premium</legend>
      <!-- Aggiungi questo campo per la descrizione -->
      <div>
        <label for="descrizionePremium">Descrizione Kit Premium: *</label>
        <textarea id="descrizionePremium" name="descrizionePremium" rows="3" required>Peluche personalizzato, foto dell'animale adottato, bracciale e certificato di adozione</textarea>
      </div>

      <div>
        <label for="prezzoPremium">Prezzo Kit Premium (€): *</label>
        <input type="number" id="prezzoPremium" name="prezzoPremium" value="45.00" min="0" step="0.01" required>
      </div>
      <div>
        <label for="fotoPremium">Foto Kit Premium: *</label>
        <input type="file" id="fotoPremium" name="fotoPremium" accept="image/*" required>
      </div>
    </fieldset>


    <div>
      <button type="submit">Aggiungi Animale e Prodotti</button>
      <button type="reset">Reset Form</button>
    </div>
  </form>

  <h2>Gestione Animali Esistenti</h2>
  <table class="admin-table">
    <tr>
      <th>ID</th>
      <th>Nome</th>
      <th>Descrizione</th>
      <th>Immagine</th>
      <th>Azioni</th>
    </tr>
    <c:forEach items="${specieAnimali}" var="specie">
      <tr>
        <td>${specie.id}</td>
        <td>
          <input type="text" name="nome_${specie.id}" value="${specie.nome}"
                 form="updateForm_${specie.id}" required>
        </td>
        <td>
                <textarea name="descrizione_${specie.id}"
                          form="updateForm_${specie.id}"
                          rows="2">${specie.descrizione}</textarea>
        </td>
        <td>
          <img src="${pageContext.request.contextPath}/${specie.urlImage}" height="50">
          <input type="file" name="nuovaImmagine_${specie.id}"
                 form="updateForm_${specie.id}" accept="image/*">
        </td>
        <td>
          <!-- Form per l'update -->
          <form id="updateForm_${specie.id}" action="doUpdateSpecie" method="post"
                enctype="multipart/form-data" style="display:inline;">
            <input type="hidden" name="id" value="${specie.id}">
            <button type="submit">Aggiorna</button>
          </form>

          <!-- Form per la delete -->
          <form action="deleteSpecie" method="post" style="display:inline;">
            <input type="hidden" name="id" value="${specie.id}">
            <button type="submit" onclick="return confirm('Sei sicuro di voler eliminare questa specie?')">
              Elimina
            </button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>

  <!-- NUOVA SEZIONE GESTIONE PRODOTTI -->
  <h2>Gestione Prodotti delle Specie</h2>
  <p>Qui puoi modificare i prodotti Standard e Premium associati a ogni specie animale.</p>

  <!-- Recupero dei prodotti direttamente nella JSP -->
  <jsp:useBean id="prodottoDAO" class="model.dao.ProdottoDAO" scope="page" />

  <c:forEach items="${specieAnimali}" var="specie">
    <h3>Prodotti per: ${specie.nome}</h3>

    <!-- Recupera i prodotti per questa specie -->
    <c:set var="prodottiSpecie" value="${prodottoDAO.doRetrieveBySpecieId(specie.id)}" />

    <c:if test="${not empty prodottiSpecie}">
      <table class="admin-table">
        <tr>
          <th>Tipo</th>
          <th>Nome</th>
          <th>Prezzo (€)</th>
          <th>Descrizione</th>
          <th>Immagine</th>
          <th>Azioni</th>
        </tr>

        <c:forEach items="${prodottiSpecie}" var="prodotto">
          <tr>
            <td>
              <c:choose>
                <c:when test="${prodotto.tipo == 1}">Standard</c:when>
                <c:when test="${prodotto.tipo == 2}">Premium</c:when>
                <c:otherwise>Altro</c:otherwise>
              </c:choose>
            </td>
            <td>
              <input type="text" name="nome_${prodotto.codice}" value="${prodotto.nome}"
                     form="updateProdottoForm_${prodotto.codice}" required>
            </td>
            <td>
              <input type="number" name="prezzo_${prodotto.codice}" value="${prodotto.prezzo}"
                     form="updateProdottoForm_${prodotto.codice}" min="0" step="0.01" required>
            </td>
            <td>
            <textarea name="descrizione_${prodotto.codice}"
                      form="updateProdottoForm_${prodotto.codice}"
                      rows="3" cols="30">${prodotto.descrizione}</textarea>
            </td>
            <td>
              <img src="${pageContext.request.contextPath}/${prodotto.urlImage}" height="50" alt="Immagine prodotto">
              <br>
              <input type="file" name="nuovaImmagine_${prodotto.codice}"
                     form="updateProdottoForm_${prodotto.codice}" accept="image/*">
              <small>Lascia vuoto per mantenere l'immagine attuale</small>
            </td>
            <td>
              <!-- Form per l'update del prodotto -->
              <form id="updateProdottoForm_${prodotto.codice}" action="doUpdateProdotto" method="post"
                    enctype="multipart/form-data">
                <input type="hidden" name="codice" value="${prodotto.codice}">
                <input type="hidden" name="specieId" value="${prodotto.specieId}">
                <input type="hidden" name="tipo" value="${prodotto.tipo}">
                <button type="submit">Aggiorna Prodotto</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:if>

    <c:if test="${empty prodottiSpecie}">
      <p>Nessun prodotto trovato per questa specie.</p>
    </c:if>

    <hr>
    <hr>
  </c:forEach>
  <!-- FINE NUOVA SEZIONE GESTIONE PRODOTTI -->


  <!-- NUOVA SEZIONE GESTIONE PERMESSI ADMIN -->
  <h2>Gestione Permessi Admin</h2>
  <p>Qui puoi promuovere o retrocedere gli utenti a amministratori.</p>

  <!-- Recupero degli utenti direttamente nella JSP -->
  <c:set var="users" value="${userDAO.doRetrieveAll()}" />

  <!-- Messaggi di stato -->
  <c:if test="${not empty success}">
    <c:choose>
      <c:when test="${success == 'upload'}">
        <div style="color: green; margin: 10px 0;">
          <strong>Successo:</strong> Animale e prodotti aggiunti correttamente!
        </div>
      </c:when>
      <c:when test="${success == 'specie_deleted'}">
        <div style="color: green; margin: 10px 0;">
          <strong>Successo:</strong> Specie eliminata correttamente!
        </div>
      </c:when>
      <c:when test="${success == 'specie_updated'}">
        <div style="color: green; margin: 10px 0;">
          <strong>Successo:</strong> Specie aggiornata correttamente!
        </div>
      </c:when>
    </c:choose>
  </c:if>

  <table>
    <tr>
      <th>Email</th>
      <th>Attualmente Admin</th>
      <th>Azioni</th>
    </tr>
    <c:forEach items="${users}" var="user">
      <tr>
        <td>${user.email}</td>
        <td>${user.admin ? 'Sì' : 'No'}</td>
        <td>
          <c:choose>
            <c:when test="${user.admin}">
              <c:if test="${user.email != sessionScope.user.email}">
                <form action="${pageContext.request.contextPath}/admin/permissions" method="post">
                  <input type="hidden" name="action" value="demote">
                  <input type="hidden" name="userEmail" value="${user.email}">
                  <button type="submit">Rimuovi Admin</button>
                </form>
              </c:if>
            </c:when>
            <c:otherwise>
              <form action="${pageContext.request.contextPath}/admin/permissions" method="post">
                <input type="hidden" name="action" value="promote">
                <input type="hidden" name="userEmail" value="${user.email}">
                <button type="submit">Rendi Admin</button>
              </form>
            </c:otherwise>
          </c:choose>
        </td>
      </tr>
    </c:forEach>
  </table>
  <!-- FINE NUOVA SEZIONE -->

  <!-- NUOVA SEZIONE GESTIONE ORDINI -->
  <h2>Gestione Ordini</h2>
  <p>Qui puoi visualizzare tutti gli ordini effettuati e gestire le spedizioni.</p>

  <!-- Recupero degli ordini direttamente nella JSP -->
  <jsp:useBean id="orderDAO" class="model.dao.OrderDAO" scope="page" />

  <c:set var="allOrders" value="${orderDAO.doRetrieveAll()}" />

  <c:if test="${not empty allOrders}">
    <table class="admin-table">
      <tr>
        <th>ID Ordine</th>
        <th>Utente</th>
        <th>Data</th>
        <th>Totale</th>
        <th>Stato</th>
        <th>Indirizzo</th>
        <th>Azioni</th>
      </tr>
      <c:forEach items="${allOrders}" var="order">
        <tr>
          <td>${order.id}</td>
          <td>${order.userId}</td>
          <td>${order.dataOrdine}</td>
          <td>€${order.prezzo}</td>
          <td>
            <c:choose>
              <c:when test="${order.ricevuto}">✓ Ricevuto</c:when>
              <c:when test="${order.spedito}">📦 Spedito</c:when>
              <c:otherwise>⏳ In elaborazione</c:otherwise>
            </c:choose>
          </td>
          <td>
              ${order.indirizzoSpedizione.via}<br>
              ${order.indirizzoSpedizione.citta}, ${order.indirizzoSpedizione.provincia} ${order.indirizzoSpedizione.cap}
          </td>
          <td>
            <c:if test="${!order.spedito && !order.ricevuto}">
              <form action="${pageContext.request.contextPath}/admin/orders" method="post" style="display:inline;">
                <input type="hidden" name="action" value="ship">
                <input type="hidden" name="orderId" value="${order.id}">
                <button type="submit">Spedisci</button>
              </form>
            </c:if>
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:if>

  <c:if test="${empty allOrders}">
    <p>Nessun ordine presente nel sistema.</p>
  </c:if>
  <!-- FINE NUOVA SEZIONE GESTIONE ORDINI -->

  <hr>

  <h3>Informazioni</h3>
  <ul>
    <li>I file duplicati verranno automaticamente rinominati</li>
    <li>Le immagini verranno salvate nella cartella target/webapp/</li>
    <li>I path nel database saranno relativi per funzionare dopo il deployment</li>
    <li>Dopo l'aggiunta, la cache delle specie animali verrà aggiornata automaticamente</li>
  </ul>
</main>

<%@ include file="/footer.jsp" %>

<script>
  // Validazione client-side per migliorare UX
  document.querySelector('form').addEventListener('submit', function(e) {
    const requiredFiles = ['fotoAnimale', 'fotoStandard', 'fotoPremium'];
    const requiredTexts = ['nomeAnimale', 'descrizioneAnimale'];

    let isValid = true;

    // Verifica file
    requiredFiles.forEach(fieldName => {
      const field = document.getElementById(fieldName);
      if (!field.files || field.files.length === 0) {
        alert('Devi selezionare tutti i file richiesti!');
        isValid = false;
        return;
      }
    });

    // Verifica testi
    requiredTexts.forEach(fieldName => {
      const field = document.getElementById(fieldName);
      if (!field.value.trim()) {
        alert('Devi compilare tutti i campi richiesti!');
        isValid = false;
        return;
      }
    });

    if (!isValid) {
      e.preventDefault();
    }
  });
</script>
</body>
</html>