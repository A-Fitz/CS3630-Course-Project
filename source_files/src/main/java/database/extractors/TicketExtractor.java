package database.extractors;

import database.tables.Ticket;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TicketExtractor implements ResultSetExtractor<List<Ticket>> {
    @Override
    public List<Ticket> extractData(ResultSet rs) throws SQLException {

        List<Ticket> ticketList = new ArrayList<>();

        while (rs.next()) {
            Ticket ticket = new Ticket();

            Integer id;
            Integer passenger_on_flight_id;
            Float price;
            String seat;
            Integer seat_class_id;
            Integer ticket_status_id;
            Timestamp purchase_timestamp;

            id = rs.getInt(Ticket.ID_COLUMN_NAME);
            if (rs.wasNull())
                id = null;
            passenger_on_flight_id = rs.getInt(Ticket.PASSENGER_ON_FLIGHT_ID_COLUMN_NAME);
            price = rs.getFloat(Ticket.PRICE_COLUMN_NAME);
            seat = rs.getString(Ticket.SEAT_COLUMN_NAME);
            seat_class_id = rs.getInt(Ticket.SEAT_CLASS_ID_COLUMN_NAME);
            ticket_status_id = rs.getInt(Ticket.TICKET_STATUS_ID_COLUMN_NAME);
            purchase_timestamp = rs.getTimestamp(Ticket.PURCHASE_TIMESTAMP_COLUMN_NAME);

            ticket.setId(id);
            ticket.setPassenger_on_flight_id(passenger_on_flight_id);
            ticket.setPrice(price);
            ticket.setSeat(seat);
            ticket.setSeat_class_id(seat_class_id);
            ticket.setTicket_status_id(ticket_status_id);
            ticket.setPurchase_timestamp(purchase_timestamp);

            ticketList.add(ticket);
        }
        return ticketList;
    }
}
