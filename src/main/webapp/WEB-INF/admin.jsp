<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Pannello Admin - Adozione Animali</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
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

  <form action="upload" method="post" enctype="multipart/form-data">
    <fieldset>
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
      <legend>Kit Standard (€25.00)</legend>
      <p><strong>Include:</strong> foto dell'animale adottato, bracciale e certificato di adozione</p>

      <div>
        <label for="fotoStandard">Foto Kit Standard: *</label>
        <input type="file" id="fotoStandard" name="fotoStandard" accept="image/*" required>
        <small>Verrà salvata in: img/prodotti/</small>
      </div>
    </fieldset>

    <fieldset>
      <legend>Kit Premium (€45.00)</legend>
      <p><strong>Include:</strong> peluche personalizzato, foto dell'animale adottato, bracciale e certificato di adozione</p>

      <div>
        <label for="fotoPremium">Foto Kit Premium: *</label>
        <input type="file" id="fotoPremium" name="fotoPremium" accept="image/*" required>
        <small>Verrà salvata in: img/prodotti/</small>
      </div>
    </fieldset>

    <div>
      <button type="submit">Aggiungi Animale e Prodotti</button>
      <button type="reset">Reset Form</button>
    </div>
  </form>

  <hr>

  <h3>Informazioni</h3>
  <ul>
    <li>I file duplicati verranno automaticamente rinominati</li>
    <li>Le immagini verranno salvate nella cartella target/webapp/</li>
    <li>I path nel database saranno relativi per funzionare dopo il deployment</li>
    <li>Dopo l'aggiunta, la cache delle specie animali verrà aggiornata automaticamente</li>
  </ul>
</main>

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