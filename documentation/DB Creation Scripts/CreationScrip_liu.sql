--AirlineEmployee, AirportEmployee, Ticket, AirlineEmployee_On_Flight, Terminal, Gate, Airport
DROP TABLE AirlineEmployee;
DROP TABLE AirportEmployee;
DROP TABLE Ticket;
DROP TABLE AirlineEmployee_On_Flight;
DROP TABLE Terminal;
DROP TABLE Gate;
DROP TABLE Airport;


CREATE TABLE airline_employee
(
	id serial PRIMARY KEY,
	airline_id integer NOT NULL,
	job_type_id integer NOT NULL,
	first_name varchar(255) NOT NULL,
	middle_name varchar(255),
	last_name varchar(255) NOT NULL,
	email varchar(255),
	phone varchar(255),
	birth_date date  NOT NULL
);

CREATE TABLE airport_employee
(
	id serial PRIMARY KEY,
	airport_id integer  NOT NULL,
	job_type_id integer NOT NULL,
	first_name varchar(255)  NOT NULL,
	middle_name varchar(255),
	last_name varchar(255)  NOT NULL,
	email varchar(255),
	phone varchar(255),
	birth_date date  NOT NULL
);

CREATE TABLE ticket
(
	id serial PRIMARY KEY,	
	passenger_on_flight_id integer NOT NULL,
	weight numeric(3,1),
	baggage_status varchar(255)

);

CREATE TABLE airline_employee_on_flight
(
	id serial PRIMARY KEY,
	flight_id integer NOT NULL,
	airline_employee_id	integer NOT NULL
);

CREATE TABLE terminal
(
	id serial PRIMARY KEY,
	airport_id integer NOT NULL,
	terminal_code varchar(255)
);

CREATE TABLE gate
(
	id serial PRIMARY KEY,
	terminal_id integer,
	gate_code varchar(255) NOT NULL	
);

CREATE TABLE airport
(
	id serial PRIMARY KEY,
	name varchar(255) NOT NULL,
	abbreviation varchar(8) NOT NULL,
	city varchar(255) NOT NULL,
	country varchar(255) NOT NULL
);