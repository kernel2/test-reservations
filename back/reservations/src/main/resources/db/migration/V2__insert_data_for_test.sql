-- Insert data into clients
INSERT INTO clients (name, email) VALUES ('John Doe', 'john.doe@example.com');
INSERT INTO clients (name, email) VALUES ('Jane Smith', 'jane.smith@example.com');
INSERT INTO clients (name, email) VALUES ('Alice Johnson', 'alice.johnson@example.com');
INSERT INTO clients (name, email) VALUES ('Bob Brown', 'bob.brown@example.com');
INSERT INTO clients (name, email) VALUES ('Emily Davis', 'emily.davis@example.com');

-- Insert data into buses
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS123', 40, '2024-09-21 08:30:00', 50.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS456', 30, '2024-09-16 14:00:00', 75.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS789', 50, '2024-09-17 10:00:00', 80.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS101', 20, '2024-09-17 16:00:00', 60.00);
INSERT INTO buses (bus_number, seats, departure_time, price) VALUES ('BUS202', 25, '2024-09-17 18:00:00', 90.00);

-- Insert reservations and trips

-- Reservation 1 for John Doe with multiple trips
INSERT INTO reservations (client_id) VALUES (1);  -- Reservation id assumed to be 1
-- Trips for reservation 1: Dates of trips now match the buses' departure times
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip,  price) VALUES (1, 'BUS123', '2024-09-21 08:30:00',40, 50.00);
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip, price) VALUES (1, 'BUS456', '2024-09-16 14:00:00', 30,75.00);
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip,  price) VALUES (1, 'BUS789', '2024-09-17 10:00:00', 50,80.00);

-- Bill for reservation 1
INSERT INTO bills (reservation_id, payment_type) VALUES (1, 'Credit Card');

-- Reservation 2 for Jane Smith with a single trip
INSERT INTO reservations (client_id) VALUES (2);  -- Reservation id assumed to be 2
-- Trip for reservation 2: Date matches the bus' departure time
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip, price) VALUES (2, 'BUS101', '2024-09-17 16:00:00',20, 60.00);

-- Bill for reservation 2
INSERT INTO bills (reservation_id, payment_type) VALUES (2, 'PayPal');

-- Reservation 3 for Alice Johnson with multiple trips
INSERT INTO reservations (client_id) VALUES (3);  -- Reservation id assumed to be 3
-- Trips for reservation 3: Dates match the buses' departure times
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip,  price) VALUES (3, 'BUS456', '2024-09-16 14:00:00', 30,75.00);
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip,  price) VALUES (3, 'BUS789', '2024-09-17 10:00:00', 50,80.00);

-- Bill for reservation 3
INSERT INTO bills (reservation_id, payment_type) VALUES (3, 'Bank Transfer');

-- Reservation 4 for Emily Davis with multiple trips
INSERT INTO reservations (client_id) VALUES (5);  -- Reservation id assumed to be 4

-- Trips for reservation 4: Dates match the buses' departure times
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip, price) VALUES (4, 'BUS123', '2024-09-21 08:30:00', 40,50.00);
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip, price) VALUES (4, 'BUS202', '2024-09-17 18:00:00', 25,90.00);
INSERT INTO trips (reservation_id, bus_number, date_of_travel, seats_per_trip, price) VALUES (4, 'BUS101', '2024-09-17 16:00:00', 20,60.00);

-- Bill for reservation 4
INSERT INTO bills (reservation_id, payment_type) VALUES (4, 'Credit Card');
