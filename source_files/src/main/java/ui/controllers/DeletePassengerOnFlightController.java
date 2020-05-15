package ui.controllers;

import database.operators.FlightOperator;
import database.operators.PassengerOnFlightOperator;
import database.operators.PassengerOperator;
import database.tables.Flight;
import database.tables.Passenger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class DeletePassengerOnFlightController extends DatabaseManipulatingController {
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();


    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
        flightComboBox.getItems().addAll(flightOperator.selectAll());
    }

    public void deletePassengerOnFlightButtonClicked() {
        if (flightComboBox.getValue() != null && passengerComboBox.getValue() != null) {
            disable();

            try {
                passengerOnFlightOperator.deleteById(
                        passengerOnFlightOperator.selectByPassengerAndFlightId(
                                passengerComboBox.getValue().getId(), flightComboBox.getValue().getId()
                        ).getId());

                clearAllFields();
                Util.setMessageLabel("Passenger on flight deleted.", Color.GREEN, messageLabel);
            }
            catch (DuplicateKeyException dke) {
                Util.setMessageLabel("Passenger on flight not deleted.", Color.RED, messageLabel);
            } catch (DataAccessException dae) {
                Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
            } catch (Exception e) {
                Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
            }
            enable();
        } else {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
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

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        flightComboBox.setValue(null);
        passengerComboBox.setValue(null);
        passengerComboBox.getItems().clear();
    }
}
