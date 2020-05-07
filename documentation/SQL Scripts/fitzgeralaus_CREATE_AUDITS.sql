-- Author: Austin FitzGerald
--
-- Purpose: This script creates tables, functions, an triggers
-- for capturing deletion in the passenger_on_flight, airline_employee_on_flight,
-- passenger, and airline_employee tables.

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