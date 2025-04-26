# Esame Finale

## Obiettivo
Lo scopo di questo progetto è sviluppare un'applicazione backend per la gestione di una **scuola post-diploma** che offre corsi di formazione tecnica.  
Il sistema permette di gestire **studenti**, **docenti**, **corsi**, **iscrizioni**, **esami**, **voto finale** e **materiali didattici**, con funzionalità avanzate di **autenticazione JWT**, **esportazione dati** in diversi formati (CSV, PDF, Excel) e **gestione sicura delle API**.

---

## Tecnologie
- **Java 21**
- **Spring Boot 3.2.4**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **JWT (JSON Web Token)**
- **Swagger / OpenAPI** (`springdoc-openapi-starter`)
- **MapStruct** (per il mapping DTO ↔️ Entity)
- **Lombok**
- **Apache POI** (per generazione file Excel)
- **iText 7** (per generazione file PDF)
- **OpenCSV** (per esportazione CSV)
- **Postman** (file JSON per test API)
- **GitHub** (versionamento del codice)

---

## Architettura
[ Browser / Postman ]
         |
         v
[ API Gateway - Spring Boot Controller ]
         |
         v
[ Security Filter - JwtFilter ]
 (Verifica token JWT)
         |
         v
[ Service Layer ]
 (Business logic)
         |
         v
[ Repository Layer ]
 (Spring Data JPA)
         |
         v
[ Database PostgreSQL ]

- Accesso protetto tramite **JWT**
- Struttura **MVC** (Model - View - Controller)
- **DTOs** per input/output sicuro
- **Mapper** per conversione DTO ↔️ Entity
- **Factory** per creazione oggetti di test (usate nel `DataLoader`)
- **DataLoader** per caricamento dati iniziali
- **Logging con @Slf4j** per tracciare gli eventi significativi del sistema

---

## Sicurezza e Autenticazione
- **Spring Security** configurato con sessioni **stateless**.
- **JWT Authentication**:
  - Al login viene generato un token JWT.
  - Tutte le chiamate protette richiedono il token nell'header `Authorization: Bearer <token>`.
- **JwtFilter custom**:
  - Intercetta tutte le richieste e autentica usando il JWT.
- **BCrypt**:
  - Password salvate cifrate in database.
- **Eccezioni personalizzate**:
  - Gestione errori come registrazione con email già esistente.
- **Logging degli errori**:
  - Errori e situazione anomale loggate per facilitare il debug e il monitoraggio del sistema.

---

## Funzionalità principali
- Registrazione **studente** e **docente**.
- Login e generazione **token JWT**.
- Gestione corsi, iscrizioni, esami, votazioni finali.
- Esportazione dei dati:
  - **CSV**: elenco anagrafica studenti.
  - **PDF**: profilo completo di uno studente.
  - **Excel**: elenco percorsi di studio e relativi esami.

---

## Funzionalità aggiuntive
- **Swagger UI** disponibile per esplorare l'API.
- **Postman Collection** pronta all'uso per testare tutte le rotte.
- **DataLoader** intelligente:
  - Se il database è vuoto, **il `DataLoader` popolerà** automaticamente corsi, studenti, esami, insegnanti e relazioni tramite **Factory** casuali.
- **Enum** usati per gestire stati (es: `EnrollmentStatus`).

---

## Avvio del progetto

1. **Configurare il file `application.properties.example`**:
   - Rinominalo in `application.properties`.
   - Inserisci:
     - Connessione database PostgreSQL (`url`, `username`, `password`).
     - Chiave segreta JWT (`jwt.secret`) per firmare i token.

2. **Comandi da eseguire**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. **Caricamento dati iniziali**:
   - Se il database è vuoto, **il `DataLoader` popolerà** automaticamente corsi, studenti, esami, docenti e relazioni.

---

## Dataset iniziale
Popolazione tramite `DataLoader`:
- **5 corsi**
- **10 studenti** casuali con iscrizioni e voti
- **4 esami** associati ai corsi
- **4 insegnanti** associati ai corsi
- **Associazioni studente ↔️ corso ↔️ esame**

---

## Esportazione file

Rotte accessibili direttamente da browser per scaricare i file:

- 📄 CSV Studenti:
  - (http://localhost:8080/api/export/students/csv)

- 📄 PDF Profilo studente:
  - (http://localhost:8080/api/export/students/{id studente}/pdf)

- 📄 Excel Percorsi di studio:
  - (http://localhost:8080/api/export/study-paths/excel)

> ℹ️ **Nota**: le rotte di esportazione sono lasciate **aperte** per permettere il download diretto dei file dal browser.

---

## Testing
- Tutte le rotte sono documentate anche tramite:
  - **Swagger UI**:
    - (http://localhost:8080/swagger-ui/index.html) -> (Sono presenti anche i DTO)
  - **OpenAPI JSON Docs**:
    - (http://localhost:8080/v3/api-docs)
- **Postman Collection** inclusa nel progetto per testare ogni endpoint.

---

## Design Pattern utilizzati
- **MVC (Model-View-Controller)**
- **DTO Pattern** (Request e Response separati)
- **Factory Pattern** (per generazione istanze nel DataLoader)
- **Mapper Pattern** (tramite MapStruct)
- **Custom Exceptions** (gestione errori pulita)
- **JWT Authentication** (stateless)

---

# 🚀 Fine!

