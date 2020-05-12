package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import ui.formatters.FloatTextFormatter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTicketController extends ThreeColumnController {
    private TicketOperator ticketOperator = TicketOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private SeatClassTypeOperator seatClassTypeOperator = SeatClassTypeOperator.getInstance();

    @FXML private Button addButton;
    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private ComboBox<SeatClassType> seatClassComboBox;
    @FXML private TextField seatTextField;
    @FXML private TextField priceTextField;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());

        priceTextField.setTextFormatter(new FloatTextFormatter());

        flightComboBox.getItems().addAll(flightOperator.selectAll());
        seatClassComboBox.getItems().addAll(seatClassTypeOperator.selectAll());
    }

    public void addTicketButtonClicked(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null &&
                passengerComboBox.getValue() != null &&
                seatClassComboBox.getValue() != null &&
                seatTextField.getText() != null && !seatTextField.getText().isEmpty() &&
                priceTextField.getText() != null && !seatTextField.getText().isEmpty()) {
            // disable buttons until a success/failure is received
            disable();

            // First try to add in the passenger on flight.
            PassengerOnFlight pof = new PassengerOnFlight();
            pof.setFlight_id(flightComboBox.getValue().getId());
            pof.setPassenger_id(passengerComboBox.getValue().getId());

            try {
                passengerOnFlightOperator.insert(pof);
            }

            catch (DataAccessException dae) {
                ui.Util.setMessageLabel("Passenger on Flight not added.", Color.RED, messageLabel);
                enable();
                return;
            }

                Ticket ticket = new Ticket();
                ticket.setPassenger_on_flight_id(pof.getId());
                ticket.setSeat_class_id(seatClassComboBox.getValue().getId());
                ticket.setSeat(seatTextField.getText());
                ticket.setPrice(Float.parseFloat(priceTextField.getText()));

                try {
                    ticketOperator.insert(ticket);
                }

                catch (DataAccessException dae) {
                // Ticket not inserted. Display error message.
                // Could be due to PassengerOnFlight not being inserted as well.
                    ui.Util.setMessageLabel("Ticket not added.", Color.RED, messageLabel); //TODO: why?
                    enable();
                    return;
            }
                // Ticket inserted. Clear each text field and display success message.
                clearAllTextFields();
                ui.Util.setMessageLabel("Ticket added.", Color.GREEN, messageLabel);
            enable();
        } else {
            // All fields must not be null. Display error message.
            ui.Util.setMessageLabel("Ticket not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen. Also get rid of selected values for the ComboBoxes.
     * Do not clear the items for the Flight or Seat Class ComboBox as those don't need to be regenerated.
     */
    private void clearAllTextFields() {
        flightComboBox.setValue(null);
        passengerComboBox.getItems().clear();
        passengerComboBox.setValue(null);
        seatClassComboBox.setValue(null);
        seatTextField.clear();
        priceTextField.clear();
    }

    /**
     * Called when the user chooses a Flight in the Flight ComboBox. Fills passenger combo box.
     *
     * @param actionEvent holds extra event information
     */
    public void flightChosen(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null) {
            passengerComboBox.getItems().clear();
            passengerComboBox.setValue(null);
            passengerComboBox.getItems().addAll(passengerOperator.selectManyByFlightId(flightComboBox.getValue().getId()));
        }
    }
}
