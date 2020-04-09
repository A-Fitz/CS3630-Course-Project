-- Author: Ross Baldwin
--
-- Purpose: This script creates foreign keys
-- using update statements for the
-- Airport Management System

-- Foreign Keys for airport_employee table
ALTER TABLE airport_employee
ADD CONSTRAINT airport_employee_airport_id_fkey
FOREIGN KEY (airport_id)
REFERENCES airport(id);

ALTER TABLE airport_employee
ADD CONSTRAINT airport_employee_job_id_fkey
FOREIGN KEY (job_id)
REFERENCES airport_job_type(id);

-- Foreign Keys for terminal table
ALTER TABLE terminal
ADD CONSTRAINT terminal_airport_id_fkey
FOREIGN KEY (airport_id)
REFERENCES airport(id);

-- Foreign Keys for gate table
ALTER TABLE gate
ADD CONSTRAINT gate_terminal_id_fkey
FOREIGN KEY (terminal_id)
REFERENCES terminal(id);

-- Foreign Keys for aircraft table
ALTER TABLE aircraft
ADD CONSTRAINT aircraft_airline_id_fkey
FOREIGN KEY (airline_id)
REFERENCES airline(id);

-- Foreign Keys for flight table
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

-- Foreign Keys for airline_employee table
ALTER TABLE airline_employee
ADD CONSTRAINT airline_employee_airline_id_fkey
FOREIGN KEY (airline_id)
REFERENCES airline(id);

ALTER TABLE airline_employee
ADD CONSTRAINT airline_employee_job_id_fkey
FOREIGN KEY (job_id)
REFERENCES airline_job_type(id);

-- Foreign Keys for airline_employee_on_flight table
ALTER TABLE airline_employee_on_flight
ADD CONSTRAINT airline_employee_on_flight_flight_id_fkey
FOREIGN KEY (flight_id)
REFERENCES flight(id);

ALTER TABLE airline_employee_on_flight
ADD CONSTRAINT airline_employee_on_flight_airline_employee_id_fkey
FOREIGN KEY (airline_employee_id)
REFERENCES airline_employee(id);

-- Foreign Keys for passenger_on_flight table
ALTER TABLE passenger_on_flight
ADD CONSTRAINT passenger_on_flight_flight_id_fkey
FOREIGN KEY (flight_id)
REFERENCES flight(id);

ALTER TABLE passenger_on_flight
ADD CONSTRAINT passenger_on_flight_passenger_id_fkey
FOREIGN KEY (passenger_id)
REFERENCES passenger(id);

-- Foreign Keys for baggage table
ALTER TABLE baggage
ADD CONSTRAINT baggage_passenger_on_flight_id_fkey
FOREIGN KEY (passenger_on_flight_id)
REFERENCES passenger_on_flight(id);

-- Foreign Keys for ticket table
ALTER TABLE ticket
ADD CONSTRAINT ticket_passenger_on_flight_id_fkey
FOREIGN KEY (passenger_on_flight_id)
REFERENCES passenger_on_flight(id);