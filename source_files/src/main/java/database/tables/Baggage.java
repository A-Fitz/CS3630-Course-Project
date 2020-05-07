package database.tables;

import database.DatabaseObject;

import java.sql.Date;

public class Baggage extends DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String PASSENGER_ON_FLIGHT_ID_COLUMN_NAME = "passenger_on_flight_id";
    public static final String WEIGHT_COLUMN_NAME = "weight";
    public static final String BAGGAGE_STATUS_ID_COLUMN_NAME = "baggage_status_id";

    private Integer id;
    private Integer passenger_on_flight_id;
    private Float weight;
    private Integer baggage_status_id;

    /* User readable information */
    public static final String FLIGHT_CALLSIGN_COLUMN_NAME = "flight_callsign";
    public static final String BAGGAGE_STATUS_TITLE_COLUMN_NAME = "baggage_status_title";

    private String flight_callsign;
    private String baggage_status_title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassenger_on_flight_id() {
        return passenger_on_flight_id;
    }

    public void setPassenger_on_flight_id(Integer passenger_on_flight_id) {
        this.passenger_on_flight_id = passenger_on_flight_id;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getBaggage_status_id() {
        return baggage_status_id;
    }

    public void setBaggage_status_id(Integer baggage_status_id) {
        this.baggage_status_id = baggage_status_id;
    }

    public String getFlight_callsign() {
        return flight_callsign;
    }

    public void setFlight_callsign(String flight_callsign) {
        this.flight_callsign = flight_callsign;
    }

    public String getBaggage_status_title() {
        return baggage_status_title;
    }

    public void setBaggage_status_title(String baggage_status_title) {
        this.baggage_status_title = baggage_status_title;
    }

    @Override
    public String toString()
    {
        return "Flight Callsign: [" +
                flight_callsign +
                "] "
                + "Weight: [" +
                weight +
                "] " +
                "Status: [" +
                baggage_status_title +
                "]";
    }
}
