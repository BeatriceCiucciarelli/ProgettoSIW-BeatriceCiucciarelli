-- =====================
-- TORNEI
-- =====================
insert into torneo (id, nome, anno, descrizione)
values (nextval('torneo_seq'), 'Torneo Giovanile Under 18', 2025, 'Torneo di calcio giovanile per ragazzi under 18');

insert into torneo (id, nome, anno, descrizione)
values (nextval('torneo_seq'), 'Campionato Amatori', 2025, 'Campionato amatoriale per squadre locali');

insert into torneo (id, nome, anno, descrizione)
values (nextval('torneo_seq'), 'Coppa Città di Terni', 2026, 'Torneo estivo organizzato dal comune di Terni');

-- =====================
-- ARBITRI
-- =====================
insert into arbitro (id, nome, cognome, codice_arbitrale)
values (nextval('arbitro_seq'), 'Luca', 'Bianchi', 'ARB001');

insert into arbitro (id, nome, cognome, codice_arbitrale)
values (nextval('arbitro_seq'), 'Marco', 'Ferretti', 'ARB002');

insert into arbitro (id, nome, cognome, codice_arbitrale)
values (nextval('arbitro_seq'), 'Giorgio', 'Mancini', 'ARB003');

-- =====================
-- SQUADRE
-- =====================
insert into squadra (id, nome, citta, anno_fondazione)
values (nextval('squadra_seq'), 'ASD Terni Calcio', 'Terni', 1995);

insert into squadra (id, nome, citta, anno_fondazione)
values (nextval('squadra_seq'), 'Perugia FC', 'Perugia', 1980);

insert into squadra (id, nome, citta, anno_fondazione)
values (nextval('squadra_seq'), 'Orvieto Sport', 'Orvieto', 2001);

insert into squadra (id, nome, citta, anno_fondazione)
values (nextval('squadra_seq'), 'Foligno United', 'Foligno', 1973);

-- =====================
-- GIOCATORI
-- (squadra_id: 1=Terni, 2=Perugia, 3=Orvieto, 4=Foligno)
-- =====================
insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Mario', 'Rossi', '2000-03-15', 180, 'ATTACCANTE', 1);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Andrea', 'Neri', '1998-07-22', 175, 'CENTROCAMPISTA', 1);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Luca', 'Verdi', '2001-11-05', 185, 'DIFENSORE', 1);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Paolo', 'Esposito', '1999-04-18', 182, 'PORTIERE', 2);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Simone', 'Gallo', '2002-09-30', 178, 'ATTACCANTE', 2);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Filippo', 'Conti', '1997-01-12', 176, 'DIFENSORE', 3);

insert into giocatore (id, nome, cognome, data_di_nascita, altezza, ruolo, squadra_id)
values (nextval('giocatore_seq'), 'Davide', 'Martini', '2000-06-08', 183, 'CENTROCAMPISTA', 4);

-- =====================
-- PARTITE
-- (torneo_id, squadra_home_id, squadra_away_id, arbitro_id)
-- =====================
insert into partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id)
values (nextval('partita_seq'), '2025-09-10 15:00:00', 'Stadio Libero Liberati', 2, 1, 'PLAYED', 1, 1, 2, 1);

insert into partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id)
values (nextval('partita_seq'), '2025-09-17 16:00:00', 'Stadio Orvieto', 0, 0, 'PLAYED', 1, 3, 4, 2);

insert into partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id)
values (nextval('partita_seq'), '2025-10-05 15:30:00', 'Campo Comunale Foligno', null, null, 'SCHEDULED', 2, 4, 1, 3);

insert into partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id)
values (nextval('partita_seq'), '2026-06-15 18:00:00', 'Stadio Libero Liberati', null, null, 'SCHEDULED', 3, 1, 3, 1);

-- =====================
-- UTENTI
-- (password: da hashare con BCrypt, qui metto un hash di esempio per 'password123')
-- =====================
insert into utente (id, username, password, ruolo)
values (nextval('utente_seq'), 'admin', '$2a$10$7QJ8G1z3K5v2X9mN4pL6ueO3YwRtBkZdHsVcFiMjAnDoPqEuGlWxy', 'ADMIN');

insert into utente (id, username, password, ruolo)
values (nextval('utente_seq'), 'mario.rossi', '$2a$10$7QJ8G1z3K5v2X9mN4pL6ueO3YwRtBkZdHsVcFiMjAnDoPqEuGlWxy', 'USER');

insert into utente (id, username, password, ruolo)
values (nextval('utente_seq'), 'andrea.neri', '$2a$10$7QJ8G1z3K5v2X9mN4pL6ueO3YwRtBkZdHsVcFiMjAnDoPqEuGlWxy', 'USER');

-- =====================
-- COMMENTI
-- (autore_id, partita_id)
-- =====================
insert into commento (id, testo, data_creazione, data_ultima_modifica, autore_id, partita_id)
values (nextval('commento_seq'), 'Bella partita, ottimo risultato!', '2025-09-10 17:30:00', '2025-09-10 17:30:00', 2, 1);

insert into commento (id, testo, data_creazione, data_ultima_modifica, autore_id, partita_id)
values (nextval('commento_seq'), 'Peccato per il pareggio, potevamo fare meglio.', '2025-09-17 18:00:00', '2025-09-17 18:00:00', 3, 2);

-- =====================
-- TORNEO_SQUADRA (tabella di join ManyToMany)
-- (torneo_id, squadra_id) - nomi colonne da verificare su pgAdmin
-- =====================
insert into torneo_squadra (tornei_id, squadre_id) values (1, 1);
insert into torneo_squadra (tornei_id, squadre_id) values (1, 2);
insert into torneo_squadra (tornei_id, squadre_id) values (1, 3);
insert into torneo_squadra (tornei_id, squadre_id) values (1, 4);
insert into torneo_squadra (tornei_id, squadre_id) values (2, 1);
insert into torneo_squadra (tornei_id, squadre_id) values (2, 4);
insert into torneo_squadra (tornei_id, squadre_id) values (3, 1);
insert into torneo_squadra (tornei_id, squadre_id) values (3, 3);