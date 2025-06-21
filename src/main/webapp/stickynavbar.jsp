<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Verifica lo stato di login dell'utente
    model.beans.User user = (model.beans.User) session.getAttribute("user");
    boolean isLoggedIn = (user != null);
    boolean isAdmin = (isLoggedIn && user.isAdmin());
%>
<link rel="icon" href="<%=request.getContextPath()%>/img/logo.png" type="image/x-icon">
<!-- Navbar sticky con logo, menu e carrello -->
<nav class="sticky-navbar">
    <span class="logo">
        <a href="<%=request.getContextPath()%>/home">
            <img src="<%=request.getContextPath()%>/img/logo.png" alt="Logo">
        </a>
    </span>

    <!-- Menu a tendina e carrello -->
    <div class="nav-right">
        <!-- Carrello sempre visibile per tutti gli utenti -->
        <a href="<%=request.getContextPath()%>/carrello.jsp" class="cart-icon">
            <img src="<%=request.getContextPath()%>/img/carrello.png" alt="Carrello">
        </a>

        <span class="dropdown">
            <button class="dropbtn">☰</button>
            <span class="dropdown-content">
                <% if (isLoggedIn) { %>
                <!-- Menu per utenti loggati -->
                    <a href="<%=request.getContextPath()%>/ordini.jsp">I miei Ordini</a>
                    <a href="<%=request.getContextPath()%>/contatti.jsp">Contatti</a>
                    <% if (isAdmin) { %>
                         <a href="<%=request.getContextPath()%>/admin/permissions">Pannello Amministratore</a>
                    <% } %>
                    <a href="<%=request.getContextPath()%>/logout">Logout</a>
                <% } else { %>
                <!-- Menu per utenti non loggati -->
                    <a href="<%=request.getContextPath()%>/login">Accedi/Registrati</a>
                    <a href="<%=request.getContextPath()%>/contatti.jsp">Contatti</a>
                    <a href="<%=request.getContextPath()%>/termini.jsp">Termini e Condizioni</a>
                <% } %>
            </span>
        </span>
    </div>
</nav>

<script>
    document.querySelector('.dropbtn').addEventListener('click', function () {
        const dropdown = document.querySelector('.dropdown-content');
        dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
    });

    // Chiudi il dropdown quando si clicca fuori
    document.addEventListener('click', function(event) {
        if (!event.target.matches('.dropbtn')) {
            const dropdown = document.querySelector('.dropdown-content');
            if (dropdown.style.display === 'block') {
                dropdown.style.display = 'none';
            }
        }
    });
</script>