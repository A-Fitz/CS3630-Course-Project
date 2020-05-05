package database.tables.base;

public class Baggage {
    public static final String ID_COLUMN_NAME = "id";
    public static final String PASSENGER_ON_FLIGHT_ID_COLUMN_NAME = "passenger_on_flight_id";
    public static final String WEIGHT_COLUMN_NAME = "weight";
    public static final String BAGGAGE_STATUS_ID_COLUMN_NAME = "baggage_status_id";

    private Integer id;
    private Integer passenger_on_flight_id;
    private Float weight;
    private Integer baggage_status_id;

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
}
