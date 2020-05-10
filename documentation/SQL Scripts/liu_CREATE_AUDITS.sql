-- Author: Jason Liu
--
-- Purpose: This script creates tables, functions and triggers
-- for capturing deletion in baggage tables.


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

DROP TRIGGER IF EXISTS after_baggage_deletion ON baggage;
CREATE TRIGGER after_baggage_deletion
	AFTER DELETE ON baggage
	FOR EACH ROW
	EXECUTE PROCEDURE log_baggage_deletion();
	
	
	
	