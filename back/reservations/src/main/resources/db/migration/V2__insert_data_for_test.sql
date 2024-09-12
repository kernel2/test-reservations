-- Insert clients
INSERT INTO clients (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO clients (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');


-- Insert buses
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS123', 40, '08:30:00', 50.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS456', 30, '14:00:00', 75.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS239', 22, '14:00:00', 100.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS139', 14, '14:00:00', 101.00);

-- Insert reservations
INSERT INTO reservations (date, bus_number, client_id) VALUES ('2024-09-15', 'BUS123', 1);
INSERT INTO reservations (date, bus_number, client_id) VALUES ('2024-09-16', 'BUS456', 2);

-- Insert bills
INSERT INTO bills (reservation_id, payment_type) VALUES (1, 'Credit Card');
INSERT INTO bills (reservation_id, payment_type) VALUES (2, 'PayPal');
