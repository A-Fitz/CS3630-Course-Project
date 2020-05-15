-- Author: Ross Baldwin, Austin FitzGerald, Jason Liu
--
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