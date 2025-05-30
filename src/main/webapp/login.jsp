<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // Gestione dei cookie per "ricordami"
  Cookie[] cookies = request.getCookies();
  String savedUsername = "";

  if (cookies != null) {
    for (Cookie c : cookies) {
      if (c.getName().equals("saved_username")) {
        savedUsername = c.getValue();
      }
    }
  }

  // Controllo errori
  String errorMessage = "";
  String error = request.getParameter("error");
  if (error != null) {
    switch (error) {
      case "empty_fields":
        errorMessage = "Tutti i campi sono obbligatori";
        break;
      case "invalid_credentials":
        errorMessage = "Credenziali non valide";
        break;
      case "user_exists":
        errorMessage = "Username o email già esistenti";
        break;
      default:
        errorMessage = "Si è verificato un errore";
    }
  }

  String successMessage = "";
  String success = request.getParameter("success");
  if (success != null && success.equals("registered")) {
    successMessage = "Registrazione completata con successo!";
  }
  if("success".equals(request.getParameter("login"))) {
%><div class="alert alert-success">Login effettuato con successo!</div>
  <% } %>
<!-- todo aggiustare il fatto che viene coperto da navbar ^^^^^^^^^^^^^^^^-->

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
  <link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico" type="image/x-icon">
  <title>Accedi | Adopta</title>
<body>
<jsp:include page="stickynavbar.jsp" />
  <!-- Messaggi di errore/successo -->
  <% if (!errorMessage.isEmpty()) { %>
  <div class="alert alert-danger">
    <%= errorMessage %>
  </div>
  <% } %>

  <% if (!successMessage.isEmpty()) { %>
  <div class="alert alert-success">
    <%= successMessage %>
  </div>
  <% } %>

  <!-- Form di Login -->
  <div class="form-container active" id="loginForm">
    <form action="<%=request.getContextPath()%>/login" method="post">
      <h2 style="text-align: center; margin-bottom: 2rem;">ACCEDI AL TUO ACCOUNT</h2>

      <div class="form-group">
        <label for="username">Username</label>
        <input type="text" name="username" id="username" class="form-control"
               placeholder="Il tuo username" required value="<%=savedUsername%>">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" class="form-control">
      </div>

      <div class="form-check">
        <input type="checkbox" name="ricordami" id="ricordami" value="si">
        <label for="ricordami">Ricordami</label>
      </div>

      <button type="submit" class="btn-primary">ENTRA</button>
    </form>
  </div>

  <!-- Form di Registrazione -->
  <div class="form-container" id="registerForm">
    <form action="<%=request.getContextPath()%>/register" method="post">
      <h2 style="text-align: center; margin-bottom: 2rem;">CREA UN NUOVO ACCOUNT</h2>

      <div class="form-group">
        <label for="reg_username">Username</label>
        <input type="text" name="username" id="reg_username" class="form-control"
               placeholder="Scegli un username" required maxlength="30">
      </div>

      <div class="form-group">
        <label for="reg_nome">Nome</label>
        <input type="text" name="nome" id="reg_nome" class="form-control"
               placeholder="Il tuo nome" required maxlength="50">
      </div>

      <div class="form-group">
        <label for="reg_cognome">Cognome</label>
        <input type="text" name="cognome" id="reg_cognome" class="form-control"
               placeholder="Il tuo cognome" required maxlength="50">
      </div>

      <div class="form-group">
        <label for="reg_email">Email</label>
        <input type="email" name="email" id="reg_email" class="form-control"
               placeholder="email@esempio.com" required maxlength="100">
      </div>

      <div class="form-group">
        <label for="reg_telefono">Telefono</label>
        <input type="tel" name="telefono" id="reg_telefono" class="form-control"
               placeholder="3XX XXXXXXX" pattern="[0-9]{10}" maxlength="15">
      </div>

      <div class="form-group">
        <label for="reg_password">Password</label>
        <input type="password" name="password" id="reg_password" class="form-control"
               placeholder="Scegli una password sicura" required minlength="6" maxlength="30">
      </div>

      <button type="submit" class="btn-primary">REGISTRATI</button>
    </form>
  </div>
</div>

<!-- Include del footer -->
<jsp:include page="footer.jsp" />

<script>
  // Script per il switch tra login e registrazione
  document.getElementById('loginSwitch').addEventListener('click', function() {
    // Attiva il form di login
    document.getElementById('loginForm').classList.add('active');
    document.getElementById('registerForm').classList.remove('active');

    // Aggiorna i pulsanti
    this.classList.add('active');
    document.getElementById('registerSwitch').classList.remove('active');
  });

  document.getElementById('registerSwitch').addEventListener('click', function() {
    // Attiva il form di registrazione
    document.getElementById('registerForm').classList.add('active');
    document.getElementById('loginForm').classList.remove('active');

    // Aggiorna i pulsanti
    this.classList.add('active');
    document.getElementById('loginSwitch').classList.remove('active');
  });

  // Validazione form
  document.querySelector('#loginForm form').addEventListener('submit', function(e) {
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
      e.preventDefault();
      alert('Tutti i campi sono obbligatori');
    }
  });

  document.querySelector('#registerForm form').addEventListener('submit', function(e) {
    const username = document.getElementById('reg_username').value.trim();
    const nome = document.getElementById('reg_nome').value.trim();
    const email = document.getElementById('reg_email').value.trim();
    const password = document.getElementById('reg_password').value.trim();

    if (!username || !nome || !email || !password) {
      e.preventDefault();
      alert('I campi obbligatori devono essere compilati');
    }

    if (password.length < 6) {
      e.preventDefault();
      alert('La password deve essere di almeno 6 caratteri');
    }
  });
</script>
</body>
</html>