-- Création de la table Client
CREATE TABLE IF NOT EXISTS clients (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- Création de la table Bus
CREATE TABLE IF NOT EXISTS buses (
    bus_number VARCHAR(255) PRIMARY KEY,
    seats INT NOT NULL,
    departure_time TIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );

-- Création de la table Reservation
CREATE TABLE IF NOT EXISTS reservations (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            date DATE NOT NULL,
                                            bus_number VARCHAR(255),
    client_id BIGINT,
    FOREIGN KEY (bus_number) REFERENCES buses(bus_number),
    FOREIGN KEY (client_id) REFERENCES clients(id)
    );

-- Création de la table Bill
CREATE TABLE IF NOT EXISTS bills (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     reservation_id BIGINT,
                                     payment_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
    );