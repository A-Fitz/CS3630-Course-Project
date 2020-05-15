-- Authors: Ross Baldwin, Austin FitzGerald, Jason Liu
--
-- Purpose: This script creates tables, constraints, foreign keys, 
-- functions and triggers for our entire database. These are split
-- into different scripts in the 'Specific Scripts' directory.

-- Purpose: This script creates tables for the
-- Airport Management System database


DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS airline;
DROP TABLE IF EXISTS airline_employee;
DROP TABLE IF EXISTS airline_employee_on_flight;
DROP TABLE IF EXISTS airline_job_type;
DROP TABLE IF EXISTS airport;
DROP TABLE IF EXISTS airport_employee;
DROP TABLE IF EXISTS airport_job_type;
DROP TABLE IF EXISTS baggage;
DROP TABLE IF EXISTS baggage_status_type;
DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS flight_status_type;
DROP TABLE IF EXISTS gate;
DROP TABLE IF EXISTS passenger;
DROP TABLE IF EXISTS passenger_on_flight;
DROP TABLE IF EXISTS seat_class_type;
DROP TABLE IF EXISTS terminal;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS ticket_status_type;

CREATE TABLE IF NOT EXISTS aircraft (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airline_id INTEGER NOT NULL,
	serial_number TEXT NOT NULL UNIQUE,
	make TEXT NOT NULL,
	model TEXT NOT NULL,
	year INTEGER NOT NULL,
	capacity INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS airline (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name TEXT NOT NULL UNIQUE,
	abbreviation TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS airline_employee (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airline_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	first_name TEXT NOT NULL,
	middle_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	address TEXT NOT NULL,
	phone TEXT NOT NULL,
	birth_date DATE NOT NULL,
	UNIQUE(first_name, middle_name, last_name, email, address, phone, birth_date)
);

CREATE TABLE IF NOT EXISTS airline_employee_on_flight (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	flight_id INTEGER NOT NULL,
	airline_employee_id INTEGER NOT NULL,
	UNIQUE(flight_id, airline_employee_id)
);

CREATE TABLE IF NOT EXISTS airline_job_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS airport (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name TEXT NOT NULL,
	iata_code TEXT NOT NULL UNIQUE,
	city TEXT NOT NULL,
	country TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS airport_employee (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airport_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	first_name TEXT NOT NULL,
	middle_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	address TEXT NOT NULL,
	phone TEXT NOT NULL,
	birth_date DATE NOT NULL,
	UNIQUE(first_name, middle_name, last_name, email, address, phone, birth_date)
);

CREATE TABLE IF NOT EXISTS airport_job_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS baggage (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passenger_on_flight_id INTEGER NOT NULL,
	weight REAL NOT NULL,
	baggage_status_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS baggage_status_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS flight (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	callsign TEXT NOT NULL,
	airline_id INTEGER NOT NULL,
	departure_airport_id INTEGER NOT NULL,
	arrival_airport_id INTEGER NOT NULL,
	departure_gate_id INTEGER NOT NULL,
	arrival_gate_id INTEGER NOT NULL,
	aircraft_id INTEGER NOT NULL,
	flight_status_id INTEGER NOT NULL,
	boarding_date DATE,
	UNIQUE(callsign, boarding_date)
);

CREATE TABLE IF NOT EXISTS flight_status_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS gate (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	terminal_id INTEGER NOT NULL,
	gate_code TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS passenger (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passport_number TEXT UNIQUE,
	first_name TEXT NOT NULL,
	middle_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	address TEXT NOT NULL,
	phone TEXT NOT NULL,
	birth_date DATE NOT NULL,
	UNIQUE(first_name, middle_name, last_name, email, address, phone, birth_date)
);

CREATE TABLE IF NOT EXISTS passenger_on_flight (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	flight_id INTEGER NOT NULL,
	passenger_id INTEGER NOT NULL,
	UNIQUE(flight_id, passenger_id)
);

CREATE TABLE IF NOT EXISTS seat_class_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS terminal (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airport_id INTEGER NOT NULL,
	terminal_code TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passenger_on_flight_id INTEGER NOT NULL,
	price REAL NOT NULL,
	seat TEXT NOT NULL,
	seat_class_id INTEGER NOT NULL,
	ticket_status_id INTEGER NOT NULL,
	purchase_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_status_type (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	title TEXT NOT NULL UNIQUE
);


-- Purpose: This script creates foreign key constraints 
-- for the Airport Management System database tables


-- aircraft table
ALTER TABLE aircraft
ADD CONSTRAINT aircraft_airline_id_fkey
FOREIGN KEY (airline_id)
REFERENCES airline(id);

-- airline_employee table
ALTER TABLE airline_employee
ADD CONSTRAINT airline_employee_airline_id_fkey
FOREIGN KEY (airline_id)
REFERENCES airline(id);

ALTER TABLE airline_employee
ADD CONSTRAINT airline_employee_job_id_fkey
FOREIGN KEY (job_id)
REFERENCES airline_job_type(id);

-- airline_employee_on_flight table
ALTER TABLE airline_employee_on_flight
ADD CONSTRAINT airline_employee_on_flight_flight_id_fkey
FOREIGN KEY (flight_id)
REFERENCES flight(id);

ALTER TABLE airline_employee_on_flight
ADD CONSTRAINT airline_employee_on_flight_airline_employee_id_fkey
FOREIGN KEY (airline_employee_id)
REFERENCES airline_employee(id);

-- airport_employee table
ALTER TABLE airport_employee
ADD CONSTRAINT airport_employee_airport_id_fkey
FOREIGN KEY (airport_id)
REFERENCES airport(id);

ALTER TABLE airport_employee
ADD CONSTRAINT airport_employee_job_id_fkey
FOREIGN KEY (job_id)
REFERENCES airport_job_type(id);

-- baggage table
ALTER TABLE baggage
ADD CONSTRAINT baggage_passenger_on_flight_id_fkey
FOREIGN KEY (passenger_on_flight_id)
REFERENCES passenger_on_flight(id);

ALTER TABLE baggage
ADD CONSTRAINT baggage_status_id_fkey
FOREIGN KEY (baggage_status_id)
REFERENCES baggage_status_type(id);

-- flight table
ALTER TABLE flight
ADD CONSTRAINT flight_airline_id_fkey
FOREIGN KEY (airline_id)
REFERENCES airline(id);

ALTER TABLE flight
ADD CONSTRAINT flight_departure_airport_id_id_fkey
FOREIGN KEY (departure_airport_id)
REFERENCES airport(id);

ALTER TABLE flight
ADD CONSTRAINT flight_arrival_airport_id_id_fkey
FOREIGN KEY (arrival_airport_id)
REFERENCES airport(id);

ALTER TABLE flight
ADD CONSTRAINT flight_departure_gate_id_id_fkey
FOREIGN KEY (departure_gate_id)
REFERENCES gate(id);

ALTER TABLE flight
ADD CONSTRAINT flight_arrival_gate_id_id_fkey
FOREIGN KEY (arrival_gate_id)
REFERENCES gate(id);

ALTER TABLE flight
ADD CONSTRAINT flight_aircraft_id_fkey
FOREIGN KEY (aircraft_id)
REFERENCES aircraft(id);

ALTER TABLE flight
ADD CONSTRAINT flight_status_id_fkey
FOREIGN KEY (flight_status_id)
REFERENCES flight_status_type(id);

-- gate table
ALTER TABLE gate
ADD CONSTRAINT gate_terminal_id_fkey
FOREIGN KEY (terminal_id)
REFERENCES terminal(id);

-- passenger_on_flight table
ALTER TABLE passenger_on_flight
ADD CONSTRAINT passenger_on_flight_flight_id_fkey
FOREIGN KEY (flight_id)
REFERENCES flight(id);

ALTER TABLE passenger_on_flight
ADD CONSTRAINT passenger_on_flight_passenger_id_fkey
FOREIGN KEY (passenger_id)
REFERENCES passenger(id);

-- terminal table
ALTER TABLE terminal
ADD CONSTRAINT terminal_airport_id_fkey
FOREIGN KEY (airport_id)
REFERENCES airport(id);

-- ticket table
ALTER TABLE ticket
ADD CONSTRAINT ticket_passenger_on_flight_id_fkey
FOREIGN KEY (passenger_on_flight_id)
REFERENCES passenger_on_flight(id);

ALTER TABLE ticket
ADD CONSTRAINT ticket_seat_class_id_fkey
FOREIGN KEY (seat_class_id)
REFERENCES seat_class_type(id);

ALTER TABLE ticket
ADD CONSTRAINT ticket_status_id_fkey
FOREIGN KEY (ticket_status_id)
REFERENCES ticket_status_type(id);


-- Purpose: This script creates indexes for foreign key attributes in each table.

DROP INDEX IF EXISTS index_aircraft;
DROP INDEX IF EXISTS index_airline_employee;
DROP INDEX IF EXISTS index_airline_employee_on_flight;
DROP INDEX IF EXISTS index_airport_employee;
DROP INDEX IF EXISTS index_baggage;
DROP INDEX IF EXISTS index_flight;
DROP INDEX IF EXISTS index_gate;
DROP INDEX IF EXISTS index_passenger_on_flight;
DROP INDEX IF EXISTS index_terminal;
DROP INDEX IF EXISTS index_ticket;

CREATE INDEX index_aircraft ON aircraft
(
  airline_id
);

CREATE INDEX index_airline_employee ON airline_employee
(
  airline_id, job_id
);

CREATE INDEX index_airline_employee_on_flight ON airline_employee_on_flight
(
  flight_id, airline_employee_id
);

CREATE INDEX index_airport_employee ON airport_employee
(
  airport_id, job_id
);

CREATE INDEX index_baggage ON baggage
(
  passenger_on_flight_id, baggage_status_id
);

CREATE INDEX index_flight ON flight
(
  airline_id, departure_airport_id, arrival_airport_id, departure_gate_id, arrival_gate_id, aircraft_id, flight_status_id
);

CREATE INDEX index_gate ON gate
(
  terminal_id
);

CREATE INDEX index_passenger_on_flight ON passenger_on_flight
(
  flight_id, passenger_id
);

CREATE INDEX index_terminal ON terminal
(
  airport_id
);

CREATE INDEX index_ticket ON ticket
(
  passenger_on_flight_id, seat_class_id, ticket_status_id
);


-- Purpose: This script creates tables, functions and triggers
-- for capturing deletion in the baggage, airline_employee, 
-- airline_employee_on_flight, passenger, passenger_on_flight, 
-- and ticket tables.

-- baggage table
DROP TABLE IF EXISTS audit_baggage;
DROP TRIGGER IF EXISTS after_baggage_deletion ON baggage;
DROP FUNCTION IF EXISTS log_baggage_deletion();

CREATE TABLE IF NOT EXISTS audit_baggage (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passenger_on_flight_id INTEGER NOT NULL,
	weight REAL NOT NULL,
	baggage_status_id INTEGER NOT NULL,
	deleted_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_baggage_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_baggage (passenger_on_flight_id, weight, baggage_status_id, deleted_on)
		VALUES (OLD.passenger_on_flight_id, OLD.weight, OLD.baggage_status_id, now());
		DELETE FROM baggage WHERE (baggage.id = OLD.id);
		RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_baggage_deletion
	AFTER DELETE ON baggage
	FOR EACH ROW
	EXECUTE PROCEDURE log_baggage_deletion();
	
-- airline_employee_table
DROP TABLE IF EXISTS audit_airline_employee;
DROP TRIGGER IF EXISTS after_airline_employee_deletion ON airline_employee;
DROP FUNCTION IF EXISTS log_airline_employee_deletion();

CREATE TABLE IF NOT EXISTS audit_airline_employee (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airline_employee_id INTEGER NOT NULL,
	airline_id INTEGER NOT NULL,
	job_id INTEGER NOT NULL,
	first_name TEXT NOT NULL,
	middle_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	address TEXT NOT NULL,
	phone TEXT NOT NULL,
	birth_date DATE NOT NULL,
	deleted_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_airline_employee_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_airline_employee (airline_employee_id, airline_id, job_id, first_name, middle_name, last_name, email, address, phone, birth_date, deleted_on)
		VALUES (OLD.id, OLD.airline_id, OLD.job_id, OLD.first_name, OLD.middle_name, OLD.last_name, OLD.email, OLD.address, OLD.phone, OLD.birth_date, now());
    
	RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_airline_employee_deletion
	AFTER DELETE
	ON airline_employee
	FOR EACH ROW
	EXECUTE PROCEDURE log_airline_employee_deletion();

-- airline_employee_on_flight table
DROP TABLE IF EXISTS audit_airline_employee_on_flight;
DROP TRIGGER IF EXISTS after_airline_employee_on_flight_deletion ON airline_employee_on_flight;
DROP FUNCTION IF EXISTS log_airline_employee_on_flight_deletion();

CREATE TABLE IF NOT EXISTS audit_airline_employee_on_flight (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	airline_employee_on_flight_id INTEGER NOT NULL,
	flight_id INTEGER NOT NULL,
	airline_employee_id INTEGER NOT NULL,
	deleted_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_airline_employee_on_flight_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_airline_employee_on_flight (airline_employee_on_flight_id, flight_id, airline_employee_id, deleted_on)
		VALUES (OLD.id, OLD.flight_id, OLD.airline_employee_id, now());
    
	RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_airline_employee_on_flight_deletion
	AFTER DELETE
	ON airline_employee_on_flight
	FOR EACH ROW
	EXECUTE PROCEDURE log_airline_employee_on_flight_deletion();

-- passenger table
DROP TABLE IF EXISTS audit_passenger;
DROP TRIGGER IF EXISTS after_passenger_deletion ON passenger;
DROP FUNCTION IF EXISTS log_passenger_deletion();

CREATE TABLE IF NOT EXISTS audit_passenger (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passenger_id INTEGER NOT NULL,
	passport_number TEXT,
	first_name TEXT NOT NULL,
	middle_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	email TEXT NOT NULL,
	address TEXT NOT NULL,
	phone TEXT NOT NULL,
	birth_date DATE NOT NULL,
	deleted_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_passenger_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_passenger (passenger_id, passport_number, first_name, middle_name, last_name, email, address, phone, birth_date, deleted_on)
		VALUES (OLD.id, OLD.passport_number, OLD.first_name, OLD.middle_name, OLD.last_name, OLD.email, OLD.address, OLD.phone, OLD.birth_date, now());
    
	RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_passenger_deletion
	AFTER DELETE
	ON passenger
	FOR EACH ROW
	EXECUTE PROCEDURE log_passenger_deletion();
	
-- passenger_on_flight_table
DROP TABLE IF EXISTS audit_passenger_on_flight;
DROP TRIGGER IF EXISTS after_passenger_on_flight_deletion ON passenger_on_flight;
DROP FUNCTION IF EXISTS log_passenger_on_flight_deletion();

CREATE TABLE IF NOT EXISTS audit_passenger_on_flight (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	passenger_on_flight_id INTEGER NOT NULL,
	flight_id INTEGER NOT NULL,
	passenger_id INTEGER NOT NULL,
	deleted_on TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_passenger_on_flight_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_passenger_on_flight (passenger_on_flight_id, flight_id, passenger_id, deleted_on)
		VALUES (OLD.id, OLD.flight_id, OLD.passenger_id, now());
    
		DELETE FROM ticket WHERE (ticket.passenger_on_flight_id = OLD.id);
		DELETE FROM baggage WHERE (baggage.passenger_on_flight_id = OLD.id);
    
	RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_passenger_on_flight_deletion
	AFTER DELETE
	ON passenger_on_flight
	FOR EACH ROW
	EXECUTE PROCEDURE log_passenger_on_flight_deletion();
	
-- ticket table
DROP TABLE IF EXISTS audit_ticket;
DROP TRIGGER IF EXISTS after_ticket_deletion ON ticket;
DROP FUNCTION IF EXISTS log_ticket_deletion();

CREATE TABLE IF NOT EXISTS audit_ticket (
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	ticket_id INTEGER NOT NULL,
	flight_id INTEGER NOT NULL,
	passenger_id INTEGER NOT NULL,
	price REAL NOT NULL,
	seat TEXT NOT NULL,
	seat_class_id INTEGER NOT NULL,
	ticket_status_id INTEGER NOT NULL,
	purchase_timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
	deleted_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE OR REPLACE FUNCTION log_ticket_deletion() RETURNS TRIGGER AS
	$BODY$
	BEGIN
		INSERT INTO audit_ticket (ticket_id, flight_id, passenger_id, price, seat, seat_class_id, ticket_status_id, purchase_timestamp, deleted_timestamp)
		VALUES (OLD.id, OLD.flight_id, OLD.passenger_id, OLD.price, OLD.seat, OLD.seat_class_id, OLD.ticket_status_id, OLD.purchase_timestamp, now());
    
		DELETE FROM ticket WHERE (ticket.id = OLD.id);
		DELETE FROM passenger_on_flight WHERE (passenger_on_flight.flight_id = OLD.flight_id AND passenger_on_flight.passenger_id = OLD.passenger_id);
    
	RETURN NEW;
	END;
	$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_ticket_deletion
	AFTER DELETE
	ON ticket
	FOR EACH ROW
	EXECUTE PROCEDURE log_ticket_deletion();
