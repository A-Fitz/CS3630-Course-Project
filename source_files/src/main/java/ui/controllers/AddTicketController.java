package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.formatters.FloatTextFormatter;
import ui.converters.FlightStringConverter;
import ui.converters.PassengerStringConverter;

import java.util.List;

public class AddTicketController {
    private TicketOperator ticketOperator = TicketOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private SeatClassTypeOperator seatClassTypeOperator = SeatClassTypeOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private ComboBox<SeatClassType> seatClassComboBox;
    @FXML private TextField seatTextField;
    @FXML private TextField priceTextField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
        flightComboBox.setConverter(new FlightStringConverter());
        passengerComboBox.setConverter(new PassengerStringConverter());

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
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            // First try to add in the passenger on flight.
            Flight chosenFlight = flightComboBox.getValue();
            Passenger chosenPassenger = passengerComboBox.getValue();
            PassengerOnFlight pof = new PassengerOnFlight();
            pof.setFlight_id(chosenFlight.getId());
            pof.setPassenger_id(chosenPassenger.getId());
            int rowsAffected = passengerOnFlightOperator.insert(pof);

            // If passenger on flight was inserted, try to make a ticket.
            if(rowsAffected != 0) {
                Ticket ticket = new Ticket();
                ticket.setPassenger_on_flight_id(pof.getId());
                ticket.setSeat_class_id(seatClassComboBox.getValue().getId());
                ticket.setSeat(seatTextField.getText());
                ticket.setPrice(Float.parseFloat(priceTextField.getText()));
                rowsAffected = ticketOperator.insert(ticket);
            }

            if (rowsAffected == 0) {
                // Ticket not inserted. Display error message.
                // Could be due to PassengerOnFlight not being inserted as well.
                ui.Util.setMessageLabel("Ticket not added.", Color.RED, messageLabel); //TODO: why?
            } else {
                // Ticket inserted. Clear each text field and display success message.
                clearAllTextFields();
                ui.Util.setMessageLabel("Ticket added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
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
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        ui.Launcher.showStage();
    }

    /**
     * Called when the user chooses a Flight in the Flight ComboBox.
     * @param actionEvent holds extra event information
     */
    public void flightChosen(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null) {
            passengerComboBox.getItems().clear();
            passengerComboBox.setValue(null);
            List<PassengerOnFlight> passengerOnFlightObjects = passengerOnFlightOperator.selectByFlightId(flightComboBox.getValue().getId());
            for (PassengerOnFlight pof : passengerOnFlightObjects) {
                passengerComboBox.getItems().add(passengerOperator.selectById(pof.getPassenger_id()));
            }
        }
    }
}
