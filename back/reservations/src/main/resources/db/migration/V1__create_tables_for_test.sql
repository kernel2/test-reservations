-- Table clients
CREATE TABLE IF NOT EXISTS clients (
                                       id BIGINT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

CREATE SEQUENCE IF NOT EXISTS clients_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE clients ALTER COLUMN id SET DEFAULT nextval('clients_seq');

-- Table buses
CREATE TABLE IF NOT EXISTS buses (
                                     bus_number VARCHAR(255) PRIMARY KEY,
    seats INT NOT NULL CHECK (seats > 0),  -- Vérification pour les places positives
    departure_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0)  -- Vérification pour un prix non négatif
    );

-- Table reservations
CREATE TABLE IF NOT EXISTS reservations (
                                            id BIGINT PRIMARY KEY,
                                            client_id BIGINT NOT NULL,
                                            FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
    );

CREATE SEQUENCE IF NOT EXISTS reservations_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE reservations ALTER COLUMN id SET DEFAULT nextval('reservations_seq');

-- Table trips
CREATE TABLE IF NOT EXISTS trips (
                                     id BIGINT PRIMARY KEY,
                                     bus_number VARCHAR(255) NOT NULL,
    date_of_travel TIMESTAMP NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    reservation_id BIGINT NOT NULL,
    FOREIGN KEY (bus_number) REFERENCES buses(bus_number) ON DELETE CASCADE,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
    );

CREATE SEQUENCE IF NOT EXISTS trips_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE trips ALTER COLUMN id SET DEFAULT nextval('trips_seq');

-- Table bills
CREATE TABLE IF NOT EXISTS bills (
                                     id BIGINT PRIMARY KEY,
                                     reservation_id BIGINT NOT NULL,
                                     payment_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE  -- Cascade suppression si la réservation est supprimée
    );

CREATE SEQUENCE IF NOT EXISTS bills_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE bills ALTER COLUMN id SET DEFAULT nextval('bills_seq');
