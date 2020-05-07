-- Author: Austin FitzGerald, Ross Baldwin
--
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
