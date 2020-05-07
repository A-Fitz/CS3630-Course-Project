-- Author: Ross Baldwin
--
-- Purpose: This script creates a table, function, and trigger
-- for capturing deletion in the ticket table.

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
	