CREATE TABLE IF NOT EXISTS clients (
                                       id BIGINT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

CREATE SEQUENCE clients_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE clients ALTER COLUMN id SET DEFAULT nextval('clients_seq');

CREATE TABLE IF NOT EXISTS buses (
                                     bus_number VARCHAR(255) PRIMARY KEY,
    seats INT NOT NULL,
    departure_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS reservations (
                                            id BIGINT PRIMARY KEY,
                                            bus_number VARCHAR(255),
    client_id BIGINT,
    date_of_travel TIMESTAMP NOT NULL,
    FOREIGN KEY (bus_number) REFERENCES buses(bus_number),
    FOREIGN KEY (client_id) REFERENCES clients(id)
    );

CREATE SEQUENCE reservations_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE reservations ALTER COLUMN id SET DEFAULT nextval('reservations_seq');

CREATE TABLE IF NOT EXISTS bills (
                                     id BIGINT PRIMARY KEY,
                                     reservation_id BIGINT,
                                     payment_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
    );

CREATE SEQUENCE bills_seq START WITH 1 INCREMENT BY 1;

ALTER TABLE bills ALTER COLUMN id SET DEFAULT nextval('bills_seq');
