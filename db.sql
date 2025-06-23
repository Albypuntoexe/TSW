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

-- Tabella per gli ordini
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL, -- email
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
CREATE TABLE incassi (
    id INT PRIMARY KEY AUTO_INCREMENT,
    totale_incassato DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    ultimo_aggiornamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inserisci il record iniziale
INSERT INTO incassi (totale_incassato) VALUES (0.00);

INSERT INTO specie_animale (nome, descrizione, url_image) VALUES
('Tigre', 'La tigre è il più grande felino vivente, nota per le sue strisce nere distintive e la sua forza.','img/animali/tigre.jpg'),
('Rinoceronte', 'Il rinoceronte è un grande mammifero perissodattilo, noto per il suo o i suoi corni sul naso.','img/animali/rinoceronte.jpg'),
('Leone', 'Il leone, spesso chiamato "re della giungla", è un grande felino caratterizzato dalla criniera nei maschi.', 'img/animali/leone.jpg'),
('Tartaruga', 'La tartaruga è un rettile con un guscio osseo o cartilagineo, noto per la sua longevità.', 'img/animali/tartaruga.jpg'),
('Elefante', 'L''elefante è il più grande animale terrestre, caratterizzato dalla sua proboscide e dalle grandi orecchie.', 'img/animali/elefante.jpg');

insert into user (email,telefono,password,username,nome,cognome,ruolo) values
('alby@gmail.com','3483795240','2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb','alby','alberto','russo',true);

-- Inserimento prodotti
INSERT INTO prodotto (specie_id, nome, prezzo, tipo, descrizione, url_image) VALUES
(1, 'Prodotto Standard Tigre', 25.00, 1, 'Foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/tigre-standard.jpg'),
(1, 'Prodotto Premium Tigre', 45.00, 2, 'Peluche personalizzato, foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/tigre-premium.jpg'),
(2, 'Prodotto Standard Rinoceronte', 25.00, 1, 'Foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/rinoceronte-standard.jpg'),
(2, 'Prodotto Premium Rinoceronte', 45.00, 2, 'Peluche personalizzato, foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/rinoceronte-premium.jpg'),
(3, 'Prodotto Standard Leone', 25.00, 1, 'Foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/leone-standard.jpg'),
(3, 'Prodotto Premium Leone', 45.00, 2, 'Peluche personalizzato, foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/leone-premium.jpg'),
(4, 'Prodotto Standard Tartaruga', 25.00, 1, 'Foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/tartaruga-standard.jpg'),
(4, 'Prodotto Premium Tartaruga', 45.00, 2, 'Peluche personalizzato, foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/tartaruga-premium.jpg'),
(5, 'Prodotto Standard Elefante', 25.00, 1, 'Foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/elefante-standard.jpg'),
(5, 'Prodotto Premium Elefante', 45.00, 2, 'Peluche personalizzato, foto dell''animale adottato, bracciale e certificato di adozione', 'img/prodotti/elefante-premium.jpg');

-- Inserimento di 20 utenti
INSERT INTO user (email, telefono, password, username, nome, cognome, ruolo) VALUES
('mario.rossi@gmail.com', '3481234567', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'mario_rossi', 'Mario', 'Rossi', false),
('giulia.verdi@gmail.com', '3492345678', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'giulia_verdi', 'Giulia', 'Verdi', false),
('luca.bianchi@gmail.com', '3503456789', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'luca_bianchi', 'Luca', 'Bianchi', false),
('anna.ferrari@gmail.com', '3514567890', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'anna_ferrari', 'Anna', 'Ferrari', false),
('marco.romano@gmail.com', '3525678901', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'marco_romano', 'Marco', 'Romano', false),
('sara.colombo@gmail.com', '3536789012', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'sara_colombo', 'Sara', 'Colombo', false),
('alessandro.conti@gmail.com', '3547890123', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'alex_conti', 'Alessandro', 'Conti', false),
('francesca.ricci@gmail.com', '3558901234', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'francy_ricci', 'Francesca', 'Ricci', false),
('giovanni.marino@gmail.com', '3569012345', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'giovanni_marino', 'Giovanni', 'Marino', false),
('elena.greco@gmail.com', '3570123456', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'elena_greco', 'Elena', 'Greco', false),
('davide.bruno@gmail.com', '3581234567', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'davide_bruno', 'Davide', 'Bruno', false),
('chiara.galli@gmail.com', '3592345678', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'chiara_galli', 'Chiara', 'Galli', false),
('matteo.costa@gmail.com', '3603456789', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'matteo_costa', 'Matteo', 'Costa', false),
('valentina.fontana@gmail.com', '3614567890', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'vale_fontana', 'Valentina', 'Fontana', false),
('simone.barbieri@gmail.com', '3625678901', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'simone_barbieri', 'Simone', 'Barbieri', false),
('laura.villa@gmail.com', '3636789012', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'laura_villa', 'Laura', 'Villa', false),
('andrea.longo@gmail.com', '3647890123', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'andrea_longo', 'Andrea', 'Longo', false),
('roberta.mancini@gmail.com', '3658901234', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'roberta_mancini', 'Roberta', 'Mancini', false),
('federico.martini@gmail.com', '3669012345', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'fede_martini', 'Federico', 'Martini', false),
('silvia.lombardi@gmail.com', '3670123456', '2bc6531dcdf39d407454e9d995d897571aca980d20a46aa832d608ea126576ec57344c29b377d2714a7d6e54e08544a08b601158fff7d2aef042583680f37beb', 'silvia_lombardi', 'Silvia', 'Lombardi', false);

-- Inserimento di 40 ordini (2 per ogni utente)
INSERT INTO orders (user_id, prezzo, spedito, ricevuto, indirizzo_via, indirizzo_citta, indirizzo_provincia, indirizzo_cap) VALUES
-- Ordini di mario.rossi@gmail.com
('mario.rossi@gmail.com', 70.00, true, true, 'Via Roma 123', 'Milano', 'MI', 20100),
('mario.rossi@gmail.com', 25.00, true, false, 'Via Roma 123', 'Milano', 'MI', 20100),
-- Ordini di giulia.verdi@gmail.com
('giulia.verdi@gmail.com', 45.00, true, true, 'Via Napoli 456', 'Roma', 'RM', 161),
('giulia.verdi@gmail.com', 90.00, false, false, 'Via Napoli 456', 'Roma', 'RM', 161),
-- Ordini di luca.bianchi@gmail.com
('luca.bianchi@gmail.com', 50.00, true, true, 'Via Torino 789', 'Torino', 'TO', 10100),
('luca.bianchi@gmail.com', 25.00, true, false, 'Via Torino 789', 'Torino', 'TO', 10100),
-- Ordini di anna.ferrari@gmail.com
('anna.ferrari@gmail.com', 45.00, true, true, 'Via Genova 321', 'Genova', 'GE', 16100),
('anna.ferrari@gmail.com', 70.00, false, false, 'Via Genova 321', 'Genova', 'GE', 16100),
-- Ordini di marco.romano@gmail.com
('marco.romano@gmail.com', 25.00, true, true, 'Via Firenze 654', 'Firenze', 'FI', 50100),
('marco.romano@gmail.com', 90.00, true, false, 'Via Firenze 654', 'Firenze', 'FI', 50100),
-- Ordini di sara.colombo@gmail.com
('sara.colombo@gmail.com', 45.00, true, true, 'Via Venezia 987', 'Venezia', 'VE', 30100),
('sara.colombo@gmail.com', 25.00, false, false, 'Via Venezia 987', 'Venezia', 'VE', 30100),
-- Ordini di alessandro.conti@gmail.com
('alessandro.conti@gmail.com', 70.00, true, true, 'Via Bologna 147', 'Bologna', 'BO', 40100),
('alessandro.conti@gmail.com', 45.00, true, false, 'Via Bologna 147', 'Bologna', 'BO', 40100),
-- Ordini di francesca.ricci@gmail.com
('francesca.ricci@gmail.com', 25.00, true, true, 'Via Palermo 258', 'Palermo', 'PA', 90100),
('francesca.ricci@gmail.com', 90.00, false, false, 'Via Palermo 258', 'Palermo', 'PA', 90100),
-- Ordini di giovanni.marino@gmail.com
('giovanni.marino@gmail.com', 45.00, true, true, 'Via Napoli 369', 'Napoli', 'NA', 80100),
('giovanni.marino@gmail.com', 25.00, true, false, 'Via Napoli 369', 'Napoli', 'NA', 80100),
-- Ordini di elena.greco@gmail.com
('elena.greco@gmail.com', 70.00, true, true, 'Via Bari 741', 'Bari', 'BA', 70100),
('elena.greco@gmail.com', 45.00, false, false, 'Via Bari 741', 'Bari', 'BA', 70100),
-- Ordini di davide.bruno@gmail.com
('davide.bruno@gmail.com', 25.00, true, true, 'Via Catania 852', 'Catania', 'CT', 95100),
('davide.bruno@gmail.com', 90.00, true, false, 'Via Catania 852', 'Catania', 'CT', 95100),
-- Ordini di chiara.galli@gmail.com
('chiara.galli@gmail.com', 45.00, true, true, 'Via Verona 963', 'Verona', 'VR', 37100),
('chiara.galli@gmail.com', 25.00, false, false, 'Via Verona 963', 'Verona', 'VR', 37100),
-- Ordini di matteo.costa@gmail.com
('matteo.costa@gmail.com', 70.00, true, true, 'Via Trieste 159', 'Trieste', 'TS', 34100),
('matteo.costa@gmail.com', 45.00, true, false, 'Via Trieste 159', 'Trieste', 'TS', 34100),
-- Ordini di valentina.fontana@gmail.com
('valentina.fontana@gmail.com', 25.00, true, true, 'Via Perugia 357', 'Perugia', 'PG', 6100),
('valentina.fontana@gmail.com', 90.00, false, false, 'Via Perugia 357', 'Perugia', 'PG', 6100),
-- Ordini di simone.barbieri@gmail.com
('simone.barbieri@gmail.com', 45.00, true, true, 'Via Ancona 468', 'Ancona', 'AN', 60100),
('simone.barbieri@gmail.com', 25.00, true, false, 'Via Ancona 468', 'Ancona', 'AN', 60100),
-- Ordini di laura.villa@gmail.com
('laura.villa@gmail.com', 70.00, true, true, 'Via Aosta 579', 'Aosta', 'AO', 11100),
('laura.villa@gmail.com', 45.00, false, false, 'Via Aosta 579', 'Aosta', 'AO', 11100),
-- Ordini di andrea.longo@gmail.com
('andrea.longo@gmail.com', 25.00, true, true, 'Via Trento 681', 'Trento', 'TN', 38100),
('andrea.longo@gmail.com', 90.00, true, false, 'Via Trento 681', 'Trento', 'TN', 38100),
-- Ordini di roberta.mancini@gmail.com
('roberta.mancini@gmail.com', 45.00, true, true, 'Via Potenza 792', 'Potenza', 'PZ', 85100),
('roberta.mancini@gmail.com', 25.00, false, false, 'Via Potenza 792', 'Potenza', 'PZ', 85100),
-- Ordini di federico.martini@gmail.com
('federico.martini@gmail.com', 70.00, true, true, 'Via Campobasso 893', 'Campobasso', 'CB', 86100),
('federico.martini@gmail.com', 45.00, true, false, 'Via Campobasso 893', 'Campobasso', 'CB', 86100),
-- Ordini di silvia.lombardi@gmail.com
('silvia.lombardi@gmail.com', 25.00, true, true, 'Via Cagliari 904', 'Cagliari', 'CA', 9100),
('silvia.lombardi@gmail.com', 90.00, false, false, 'Via Cagliari 904', 'Cagliari', 'CA', 9100);


UPDATE incassi
SET totale_incassato = (SELECT SUM(prezzo) FROM orders)
WHERE id = 1;
select * from incassi;
use modellomvcdb;