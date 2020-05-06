package database.tables.information;

import java.sql.Date;

public class BaggageInformation {
    public static String FLIGHT_CALLSIGN_COLUMN_NAME = "flight_callsign";
    public static String PASSENGER_PASSPORT_NUMBER_COLUMN_NAME = "passenger_passport_number";
    public static String PASSENGER_FIRST_NAME_COLUMN_NAME = "passenger_first_name";
    public static String PASSENGER_MIDDLE_NAME_COLUMN_NAME = "passenger_middle_name";
    public static String PASSENGER_LAST_NAME_COLUMN_NAME = "passenger_last_name";
    public static String PASSENGER_BIRTH_DATE_COLUMN_NAME = "passenger_birthdate";
    public static String BAGGAGE_STATUS_TITLE_COLUMN_NAME = "baggage_status_title";

    private Integer id;
    private String flight_callsign;
    private String passenger_passport_number;
    private String passenger_first_name;
    private String passenger_middle_name;
    private String passenger_last_name;
    private Date passenger_birthdate;
    private Float weight;
    private String baggage_status_title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlight_callsign() {
        return flight_callsign;
    }

    public void setFlight_callsign(String flight_callsign) {
        this.flight_callsign = flight_callsign;
    }

    public String getPassenger_passport_number() {
        return passenger_passport_number;
    }

    public void setPassenger_passport_number(String passenger_passport_number) {
        this.passenger_passport_number = passenger_passport_number;
    }

    public String getPassenger_first_name() {
        return passenger_first_name;
    }

    public void setPassenger_first_name(String passenger_first_name) {
        this.passenger_first_name = passenger_first_name;
    }

    public String getPassenger_middle_name() {
        return passenger_middle_name;
    }

    public void setPassenger_middle_name(String passenger_middle_name) {
        this.passenger_middle_name = passenger_middle_name;
    }

    public String getPassenger_last_name() {
        return passenger_last_name;
    }

    public void setPassenger_last_name(String passenger_last_name) {
        this.passenger_last_name = passenger_last_name;
    }

    public Date getPassenger_birthdate() {
        return passenger_birthdate;
    }

    public void setPassenger_birthdate(Date passenger_birthdate) {
        this.passenger_birthdate = passenger_birthdate;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBaggage_status_title() {
        return baggage_status_title;
    }

    public void setBaggage_status_title(String baggage_status_title) {
        this.baggage_status_title = baggage_status_title;
    }

    @Override
    public String toString() {
        String toReturn = "Flight Callsign: [" +
                flight_callsign +
                "] ";
        if(passenger_passport_number != null)
        {
            toReturn += "Passenger Passport #: [" +
                    passenger_passport_number +
                    "] ";
        }
        else
        {
            toReturn += "Passenger First Name: [" +
                    passenger_first_name +
                    "] "
                    +  "Passenger Middle Name: [" +
                    passenger_middle_name +
                    "] "
                    +  "Passenger Last Name: [" +
                    passenger_last_name +
                    "] ";
        }
        toReturn += "Weight: [" +
                weight +
                "] " +
                "Status: [" +
                baggage_status_title +
                "]";

        return toReturn;
    }
}
