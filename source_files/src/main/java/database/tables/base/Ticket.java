package database.tables.base;

import java.sql.Timestamp;

public class Ticket {
    public static final String ID_COLUMN_NAME = "id";
    public static final String PASSENGER_ON_FLIGHT_ID_COLUMN_NAME = "passenger_on_flight_id";
    public static final String PRICE_COLUMN_NAME = "price";
    public static final String SEAT_COLUMN_NAME = "seat";
    public static final String SEAT_CLASS_ID_COLUMN_NAME = "seat_class_id";
    public static final String TICKET_STATUS_ID_COLUMN_NAME = "ticket_status_id";
    public static final String PURCHASE_TIMESTAMP_COLUMN_NAME = "purchase_timestamp";

    private Integer id;
    private Integer passenger_on_flight_id;
    private Float price;
    private String seat;
    private Integer seat_class_id;
    private Integer ticket_status_id;
    private Timestamp purchase_timestamp;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Integer getSeat_class_id() {
        return seat_class_id;
    }

    public void setSeat_class_id(Integer seat_class_id) {
        this.seat_class_id = seat_class_id;
    }

    public Integer getTicket_status_id() {
        return ticket_status_id;
    }

    public void setTicket_status_id(Integer ticket_status_id) {
        this.ticket_status_id = ticket_status_id;
    }

    public Timestamp getPurchase_timestamp() {
        return purchase_timestamp;
    }

    public void setPurchase_timestamp(Timestamp purchase_timestamp) {
        this.purchase_timestamp = purchase_timestamp;
    }
}
