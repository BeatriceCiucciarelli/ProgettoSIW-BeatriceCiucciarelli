insert into torneo (nome, anno, descrizione) values ('Torneo Giovanile Under 18', 2025, 'Torneo di calcio giovanile per ragazzi under 18');
insert into torneo (nome, anno, descrizione) values ('Campionato Amatori', 2025, 'Campionato amatoriale per squadre locali');
insert into torneo (nome, anno, descrizione) values ('Coppa Città di Terni', 2026, 'Torneo estivo organizzato dal comune di Terni');

insert into squadra (nome, citta, anno_fondazione) values ('ASD Terni Calcio', 'Terni', 1995);
insert into squadra (nome, citta, anno_fondazione) values ('Perugia FC', 'Perugia', 1980);
insert into squadra (nome, citta, anno_fondazione) values ('Orvieto Sport', 'Orvieto', 2001);
insert into squadra (nome, citta, anno_fondazione) values ('Foligno United', 'Foligno', 1973);

insert into torneo_squadra (torneo_id, squadra_id) values (1, 1);
insert into torneo_squadra (torneo_id, squadra_id) values (1, 2);
insert into torneo_squadra (torneo_id, squadra_id) values (1, 3);
insert into torneo_squadra (torneo_id, squadra_id) values (1, 4);
insert into torneo_squadra (torneo_id, squadra_id) values (2, 1);
insert into torneo_squadra (torneo_id, squadra_id) values (2, 4);
insert into torneo_squadra (torneo_id, squadra_id) values (3, 1);
insert into torneo_squadra (torneo_id, squadra_id) values (3, 3);

insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Mario', 'Rossi', '2000-03-15', 180, 'ATTACCANTE', 1);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Andrea', 'Neri', '1998-07-22', 175, 'CENTROCAMPISTA', 1);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Luca', 'Verdi', '2001-11-05', 185, 'DIFENSORE', 1);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Paolo', 'Esposito', '1999-04-18', 182, 'PORTIERE', 2);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Simone', 'Gallo', '2002-09-30', 178, 'ATTACCANTE', 2);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Filippo', 'Conti', '1997-01-12', 176, 'DIFENSORE', 3);
insert into giocatore (nome, cognome, data_di_nascita, altezza, ruolo, squadra_id) values ('Davide', 'Martini', '2000-06-08', 183, 'CENTROCAMPISTA', 4);

insert into arbitro (nome, cognome, codice_arbitrale) values ('Luca', 'Bianchi', 'ARB001');
insert into arbitro (nome, cognome, codice_arbitrale) values ('Marco', 'Ferretti', 'ARB002');
insert into arbitro (nome, cognome, codice_arbitrale) values ('Giorgio', 'Mancini', 'ARB003');

insert into partita (data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) values ('2025-09-10 15:00:00', 'Stadio Libero Liberati', 2, 1, 'PLAYED', 1, 1, 2, 1);
insert into partita (data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) values ('2025-09-17 16:00:00', 'Stadio Orvieto', 0, 0, 'PLAYED', 1, 3, 4, 2);
insert into partita (data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) values ('2025-10-05 15:30:00', 'Campo Comunale Foligno', null, null, 'SCHEDULED', 2, 4, 1, 3);
insert into partita (data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) values ('2026-06-15 18:00:00', 'Stadio Libero Liberati', null, null, 'SCHEDULED', 3, 1, 3, 1);

insert into utente (username, password, ruolo) values ('admin', '$2b$12$mBQU7R/BUi2THZPCpPYZaeAOJz8RJh8jlzbe1clb077T0SYnEbscK', 'ADMIN');
insert into utente (username, password, ruolo) values ('mario.rossi', '$2b$12$mBQU7R/BUi2THZPCpPYZaeAOJz8RJh8jlzbe1clb077T0SYnEbscK', 'USER');
insert into utente (username, password, ruolo) values ('andrea.neri', '$2b$12$mBQU7R/BUi2THZPCpPYZaeAOJz8RJh8jlzbe1clb077T0SYnEbscK', 'USER');

insert into commento (testo, data_creazione, data_ultima_modifica, utente_id, partita_id) values ('Bella partita, ottimo risultato!', '2025-09-10 17:30:00', '2025-09-10 17:30:00', 2, 1);
insert into commento (testo, data_creazione, data_ultima_modifica, utente_id, partita_id) values ('Peccato per il pareggio, potevamo fare meglio.', '2025-09-17 18:00:00', '2025-09-17 18:00:00', 3, 2);