package database.operators;

import database.DatabaseConnection;
import database.extractors.TicketExtractor;
import database.tables.Ticket;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class TicketOperator {
    private static TicketOperator instance = new TicketOperator();
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate = DatabaseConnection.getInstance().getNamedParameterJdbcTemplate();

    /**
     * Do not allow instantiation of a TicketOperator object. This is a Singleton.
     */
    private TicketOperator() {

    }

    public static TicketOperator getInstance() {
        return instance;
    }

    /**
     * Selects a ticket row, in the form of a Java object, from the ticket table given an id.
     *
     * @param id The value of the id column for an ticket row
     * @return (null if no ticket row exists with that id) (an ticket object if row exists with that id)
     */
    public Ticket selectById(int id) {
        TicketExtractor extractor = new TicketExtractor();

        String queryTemplate = "SELECT * FROM ticket WHERE id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Ticket> ticketList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);

        if (ticketList == null || ticketList.size() == 0)
            return null;
        else
            return ticketList.get(0);
    }

    /**
     * Tries to update a row in the ticket table given an id and a representative Java object.
     *
     * @param id     The value of the id column of the row to update.
     * @param ticket A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    public int updateById(int id, Ticket ticket) {
        String queryTemplate = "UPDATE ticket SET "
                + Ticket.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + " = :new_passenger_on_flight_id,"
                + Ticket.PRICE_COLUMN_NAME + " = :new_price,"
                + Ticket.SEAT_COLUMN_NAME + " = :new_seat,"
                + Ticket.SEAT_CLASS_ID_COLUMN_NAME + " = :new_seat_class_id,"
                + Ticket.TICKET_STATUS_ID_COLUMN_NAME + " = :new_ticket_status_id,"
                + Ticket.PURCHASE_TIMESTAMP_COLUMN_NAME + " = :new_purchase_timestamp"
                + " WHERE " + Ticket.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_passenger_on_flight_id", ticket.getPassenger_on_flight_id());
        parameters.addValue("new_price", ticket.getPrice());
        parameters.addValue("new_seat", ticket.getSeat());
        parameters.addValue("new_seat_class_id", ticket.getSeat_class_id());
        parameters.addValue("new_ticket_status_id", ticket.getTicket_status_id());
        parameters.addValue("new_purchase_timestamp", ticket.getPurchase_timestamp());
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }

    /**
     * Tries to insert a new row into the ticket table given a representative Java object.
     *
     * @param ticket The Ticket object which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    public int insert(Ticket ticket) {
        String queryTemplate = "INSERT INTO ticket ("
                + Ticket.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME + ", "
                + Ticket.PRICE_COLUMN_NAME + ", "
                + Ticket.SEAT_COLUMN_NAME + ", "
                + Ticket.SEAT_CLASS_ID_COLUMN_NAME + ", "
                + Ticket.TICKET_STATUS_ID_COLUMN_NAME + ", "
                + Ticket.PURCHASE_TIMESTAMP_COLUMN_NAME + ") "
                + "VALUES(:new_passenger_on_flight_id, :new_price, :new_seat, :new_seat_class_id, "
                + ":new_ticket_status_id, :new_purchase_timestamp)";

        // Map of variable names and the values to replace with
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("new_passenger_on_flight_id", ticket.getPassenger_on_flight_id());
        parameters.addValue("new_price", ticket.getPrice());
        parameters.addValue("new_seat", ticket.getSeat());
        parameters.addValue("new_seat_class_id", ticket.getSeat_class_id());
        parameters.addValue("new_ticket_status_id", ticket.getTicket_status_id());
        parameters.addValue("new_purchase_timestamp", ticket.getPurchase_timestamp());

        // Statement to insert the row
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters);
        } catch (DuplicateKeyException dke) {
            // do nothing
        }

        return rowsAffected;
    }

    /**
     * Tries to delete a row in the ticket table given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    public int deleteById(int id) {
        String queryTemplate = "DELETE FROM ticket "
                + " WHERE " + Ticket.ID_COLUMN_NAME + " = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        return namedParameterJdbcTemplate.update(queryTemplate, parameters);
    }
}
