<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico" type="image/x-icon">
    <title>Adopta | Salva una specie</title>
</head>

<body>
<!-- Include della navbar sticky -->
<jsp:include page="stickynavbar.jsp" />

<header class="header-background">
    <!-- Contenuto hero della pagina -->
    <div class="hero-content">
        <h1 class="hero-title">SUPPORTA UNA SPECIE A RISCHIO</h1>
        <p class="hero-subtitle">Sostieni una specie e ricevi in regalo un kit di adozione</p>
        <!-- Statistiche in evidenza -->
        <div class="hero-stats">
            <div class="stat-item">
                <span class="stat-number">150+</span>
                <span class="stat-label">Specie protette</span>
            </div>
            <div class="stat-item">
                <span class="stat-number">50K+</span>
                <span class="stat-label">Sostenitori attivi</span>
            </div>
            <div class="stat-item">
                <span class="stat-number">25</span>
                <span class="stat-label">Paesi coinvolti</span>
            </div>
        </div>
    </div>
</header>

<!-- Sezione prodotto in evidenza -->
<section class="featured-product">
    <div class="container">
        <div class="product-showcase">
            <div class="product-image">
                <a href="#animali-container">
                    <img src="<%=request.getContextPath()%>/img/hero.png" alt="Kit Adozione Premium">
                </a>
            </div>
            <div class="product-info">
                <h2>Kit Adozione Premium</h2>
                <p>Il nostro kit più completo include peluche personalizzato, foto dell'animale adottato, bracciale e molto altro.
                    Perfetto per fare la differenza e ricevere un ricordo tangibile del tuo contributo.</p>
                <a href="#animali-container" class="cta-button">SCOPRI I KIT DISPONIBILI</a>
            </div>
        </div>
    </div>
</section>

<main class="main-content">
    <!-- Lista animali dal database -->
    <div class="animali-container" id="animali-container">
        <c:forEach var="animale" items="${specieAnimali}">
            <div class="animale-card">
                <a href="<%=request.getContextPath()%>/prodotto?id=${animale.id}">
                    <img src="<%=request.getContextPath()%>/${animale.urlImage}" alt="${animale.nome}" class="animale-img">
                </a>
                <h3>${animale.nome}</h3>
                <button onclick="location.href='<%=request.getContextPath()%>/prodotto?id=${animale.id}'">SUPPORTA ORA</button>
            </div>
        </c:forEach>
    </div>

    <!-- Sezione impatto delle donazioni -->
    <section class="impact-section">
        <div class="container">
            <h2>Il tuo impatto conta</h2>
            <p>Grazie al supporto dei nostri sostenitori, abbiamo raggiunto risultati straordinari</p>
            <div class="impact-grid">
                <div class="impact-item">
                    <div class="impact-icon">🌳</div>
                    <h3>10.000 ettari protetti</h3>
                    <p>di habitat naturale preservato</p>
                </div>
                <div class="impact-item">
                    <div class="impact-icon">🐾</div>
                    <h3>2.500 animali salvati</h3>
                    <p>attraverso programmi di recupero</p>
                </div>
                <div class="impact-item">
                    <div class="impact-icon">👥</div>
                    <h3>15.000 persone educate</h3>
                    <p>sulla conservazione ambientale</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Sezione come funziona -->
    <section class="how-it-works">
        <div class="container">
            <h2>Come funziona l'adozione</h2>
            <div class="steps-grid">
                <div class="step-item">
                    <div class="step-number">1</div>
                    <h3>Scegli la specie</h3>
                    <p>Seleziona l'animale che vuoi supportare</p>
                </div>
                <div class="step-item">
                    <div class="step-number">2</div>
                    <h3>Completa l'adozione</h3>
                    <p>Scegli il tipo di supporto</p>
                </div>
                <div class="step-item">
                    <div class="step-number">3</div>
                    <h3>Ricevi il kit</h3>
                    <p>Ti invieremo foto e certificato</p>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Sezione FAQ -->
<section class="faq-section">
    <div class="container">
        <h2>Domande Frequenti</h2>
        <div class="faq-item">
            <h3>Come posso supportare una specie?</h3>
            <p>Puoi scegliere di adottare una specie e ricevere un kit di adozione personalizzato.</p>
        </div>
        <div class="faq-item">
            <h3>Quali sono i metodi di pagamento accettati?</h3>
            <p>Accettiamo carte di credito, PayPal e bonifici bancari.</p>
        </div>
        <div class="faq-item">
            <h3>Come posso contattarvi per ulteriori informazioni?</h3>
            <p>Puoi scriverci all'indirizzo email indicato nella sezione contatti.</p>
        </div>
    </div>
</section>

<!-- Include del footer -->
<jsp:include page="footer.jsp" />
</body>
</html>