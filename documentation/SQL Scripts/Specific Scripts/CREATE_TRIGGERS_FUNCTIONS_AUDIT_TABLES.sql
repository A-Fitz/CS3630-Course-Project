-- Authors: Ross Baldwin, Austin FitzGerald, Jason Liu
--
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
