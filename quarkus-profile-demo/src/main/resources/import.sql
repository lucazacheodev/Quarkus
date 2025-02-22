-- Crea la tabella se non esiste (compatibile con H2)
CREATE TABLE IF NOT EXISTS myentity (
    id BIGINT PRIMARY KEY,
    field VARCHAR(255)
);

-- Popola la tabella con dati di esempio
INSERT INTO myentity (id, field) VALUES (1, 'Primo record');
INSERT INTO myentity (id, field) VALUES (2, 'Secondo record');
INSERT INTO myentity (id, field) VALUES (3, 'Terzo record');