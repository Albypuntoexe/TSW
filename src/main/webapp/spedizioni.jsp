<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/style.css">
  <title>Politica di Spedizione | Adopta</title>
</head>

<body>

<jsp:include page="WEB-INF/stickynavbar.jsp" />


<main class="main-content">
  <div class="animale-dettagli">
    <h1>Politica di Spedizione</h1>
    <p>Scopri come e quando riceverai il tuo kit di adozione dopo aver effettuato una donazione.</p>

    <div class="info-section">
      <div class="container">
        <div class="impact-grid">
          <div class="impact-item">
            <div class="impact-icon">🚚</div>
            <h3>Spedizione Standard</h3>
            <p>Spedizione gratuita in tutta Italia. Tempi di consegna: 5-7 giorni lavorativi.</p>
          </div>
          <div class="impact-item">
            <div class="impact-icon">🌍</div>
            <h3>Spedizione Internazionale</h3>
            <p>Disponibile per la maggior parte dei paesi. I costi e i tempi variano in base alla destinazione.</p>
          </div>
          <div class="impact-item">
            <div class="impact-icon">📦</div>
            <h3>Tracciamento dell'Ordine</h3>
            <p>Riceverai un'email con il codice di tracciamento non appena il tuo kit verrà spedito.</p>
          </div>
        </div>
      </div>
    </div>

    <div class="faq-section" style="padding-top: 20px;">
      <div class="container" style="text-align: left;">
        <div class="faq-item">
          <h3>Elaborazione degli Ordini</h3>
          <p>Tutti gli ordini vengono elaborati entro 1-2 giorni lavorativi. Gli ordini non vengono spediti o consegnati nei fine settimana o durante le festività.</p>
        </div>
        <div class="faq-item">
          <h3>Dazi e Tasse Doganali</h3>
          <p>Adopta non è responsabile per eventuali dazi doganali o tasse applicate al tuo ordine. Tutte le commissioni imposte durante o dopo la spedizione sono a carico del cliente (tariffe, tasse, ecc.).</p>
        </div>
      </div>
    </div>
  </div>
</main>
<jsp:include page="WEB-INF/footer.jsp" />
</body>
</html>