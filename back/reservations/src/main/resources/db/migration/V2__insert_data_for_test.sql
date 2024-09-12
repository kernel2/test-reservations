-- Insert clients
INSERT INTO clients (id, name, email) VALUES (nextval('clients_seq'), 'John Doe', 'john.doe@example.com');
INSERT INTO clients (id, name, email) VALUES (nextval('clients_seq'), 'Jane Smith', 'jane.smith@example.com');
INSERT INTO clients (id, name, email) VALUES (nextval('clients_seq'), 'Alice Johnson', 'alice.johnson@example.com');
INSERT INTO clients (id, name, email) VALUES (nextval('clients_seq'), 'Bob Brown', 'bob.brown@example.com');

-- Insert buses
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS123', 40, '08:30:00', 50.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS456', 30, '14:00:00', 75.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS239', 22, '14:00:00', 100.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS139', 14, '14:00:00', 101.00);

-- Insert reservations
INSERT INTO reservations (id, bus_number, client_id, date_of_travel) VALUES (nextval('reservations_seq'), 'BUS123', 1, '2024-09-15T08:30:00');
INSERT INTO reservations (id, bus_number, client_id, date_of_travel) VALUES (nextval('reservations_seq'), 'BUS456', 2, '2024-09-16T14:00:00');
INSERT INTO reservations (id, bus_number, client_id, date_of_travel) VALUES (nextval('reservations_seq'), 'BUS239', 3, '2024-09-17T14:00:00');
INSERT INTO reservations (id, bus_number, client_id, date_of_travel) VALUES (nextval('reservations_seq'), 'BUS139', 4, '2024-09-18T14:00:00');

-- Insert bills
INSERT INTO bills (id, reservation_id, payment_type) VALUES (nextval('bills_seq'), 1, 'Credit Card');
INSERT INTO bills (id, reservation_id, payment_type) VALUES (nextval('bills_seq'), 2, 'PayPal');
INSERT INTO bills (id, reservation_id, payment_type) VALUES (nextval('bills_seq'), 3, 'Bank Transfer');
INSERT INTO bills (id, reservation_id, payment_type) VALUES (nextval('bills_seq'), 4, 'Cash');
