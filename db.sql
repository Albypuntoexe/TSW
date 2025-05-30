DROP DATABASE IF EXISTS modellomvcdb;
CREATE DATABASE IF NOT EXISTS modellomvcdb;
USE modellomvcdb;

-- Tabella per gli utenti
CREATE TABLE user (
    email VARCHAR(255) PRIMARY KEY,
    telefono VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    ruolo BOOLEAN NOT NULL DEFAULT false 
);

-- Tabella per le specie animali
CREATE TABLE specie_animale (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descrizione TEXT NOT NULL,
    url_image VARCHAR(255) NOT NULL
);

-- Tabella per i prodotti
CREATE TABLE prodotto (
    codice INT AUTO_INCREMENT,
    specie_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    prezzo DOUBLE NOT NULL,
    tipo INT NOT NULL,
    descrizione TEXT NOT NULL,
    url_image VARCHAR(255) NOT NULL, 
    PRIMARY KEY (codice, specie_id),
    FOREIGN KEY (specie_id) REFERENCES specie_animale(id) ON DELETE CASCADE
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

INSERT INTO specie_animale (nome, descrizione, url_image) VALUES
('Tigre', 'La tigre è il più grande felino vivente, nota per le sue strisce nere distintive e la sua forza.','img/animali/tigre.jpg'),
('Rinoceronte', 'Il rinoceronte è un grande mammifero perissodattilo, noto per il suo o i suoi corni sul naso.','img/animali/rinoceronte.jpg'),
('Leone', 'Il leone, spesso chiamato "re della giungla", è un grande felino caratterizzato dalla criniera nei maschi.', 'img/animali/leone.jpg'),
('Tartaruga', 'La tartaruga è un rettile con un guscio osseo o cartilagineo, noto per la sua longevità.', 'img/animali/tartaruga.jpg'),
('Elefante', 'L''elefante è il più grande animale terrestre, caratterizzato dalla sua proboscide e dalle grandi orecchie.', 'img/animali/elefante.jpg');

insert into user (email,telefono,password,username,nome,cognome,ruolo) values
('alby@gmail.com','3483795240','2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb','alby','alberto','russo',true);

select * from user;