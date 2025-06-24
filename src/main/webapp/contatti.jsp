<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/style.css">
  <title>Contatti | Adopta</title>
</head>

<body>

<jsp:include page="stickynavbar.jsp" />


<main class="main-content">
  <div class="contatti-container">
    <h1>Contatti</h1>
    <p>Benvenuto nella pagina dei contatti. Qui troverai tutte le informazioni utili per metterti in contatto con noi.</p>
    
    <h2>Contatti Diretti</h2>
    <p><strong>Email:</strong> info@adopta.org</p>
    <p><strong>Telefono:</strong> +39 341 145 3486</p>
    
    <h2>Sede Legale</h2>
    <p><strong>Indirizzo:</strong> Via Esempio 123, 00100 Roma, Italia</p>
    <p><strong>P. IVA:</strong> 01234567890</p>
    
    <h2>Orari di Apertura</h2>
    <p>Dal lunedì al venerdì: 9:00 - 18:00</p>
    
    <h2>Assistenza Clienti</h2>
    <p>Per ulteriori informazioni, non esitare a contattarci tramite i recapiti sopra indicati.</p>
  </div>
</main>

<jsp:include page="footer.jsp" />

</body>
</html>
