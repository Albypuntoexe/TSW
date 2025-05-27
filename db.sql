
-- Tabella per gli utenti
CREATE TABLE user (
    email VARCHAR(255) PRIMARY KEY,
    telefono VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    ruolo ENUM('utente', 'admin') DEFAULT 'utente' -- Aggiunto ruolo per distinguere utenti e amministratori
);

-- Tabella per i prodotti
CREATE TABLE prodotto (
    codice INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    prezzo DOUBLE NOT NULL,
    tipo INT NOT NULL,
    descrizione TEXT NOT NULL, -- Aggiunta descrizione per i prodotti
    url_image VARCHAR(255) NOT NULL -- Aggiunto URL immagine per i prodotti
);

-- Tabella per gli elementi del carrello
CREATE TABLE cartitem (
    user_id VARCHAR(255) NOT NULL,
    prodotto_codice INT NOT NULL,
    quantita INT NOT NULL,
    PRIMARY KEY (user_id, prodotto_codice),
    FOREIGN KEY (user_id) REFERENCES user(email) ON DELETE CASCADE,
    FOREIGN KEY (prodotto_codice) REFERENCES prodotto(codice) ON DELETE CASCADE
);

-- Tabella per gli ordini
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    data_ordine TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    prezzo DOUBLE NOT NULL,
    spedito BOOLEAN NOT NULL DEFAULT FALSE,
    ricevuto BOOLEAN NOT NULL DEFAULT FALSE,
    indirizzo_via VARCHAR(255) NOT NULL,
    indirizzo_citta VARCHAR(100) NOT NULL,
    indirizzo_provincia VARCHAR(100) NOT NULL,
    indirizzo_cap INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(email) ON DELETE CASCADE
);

-- Tabella per gli elementi degli ordini
CREATE TABLE orderitem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    prodotto_codice INT NOT NULL,
    quantita INT NOT NULL,
    prezzo_unitario DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (prodotto_codice) REFERENCES prodotto(codice) ON DELETE CASCADE
);

-- Tabella per le specie animali
CREATE TABLE specie_animale (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT NOT NULL,
    prezzo DOUBLE NOT NULL,
    url_image VARCHAR(255) NOT NULL,
    categoria VARCHAR(50) NOT NULL -- Aggiunta categoria per classificare le specie animali
);

-- Tabella per le donazioni
CREATE TABLE donazione (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    specie_id INT NOT NULL,
    importo DOUBLE NOT NULL,
    data_donazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(email) ON DELETE CASCADE,
    FOREIGN KEY (specie_id) REFERENCES specie_animale(id) ON DELETE CASCADE
);
