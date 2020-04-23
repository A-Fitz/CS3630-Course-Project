-- Author: Ross Baldwin, Austin FitzGerald, Jason Liu
--
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
	airline_employee_id INTEGER NOT NULL
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
	passenger_id INTEGER NOT NULL
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
