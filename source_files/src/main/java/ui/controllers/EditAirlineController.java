package ui.controllers;

import database.operators.AirlineOperator;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAirlineController extends ThreeColumnController {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();


    @FXML private ComboBox<Airline> airlineChoiceComboBox;
    @FXML private TextField nameTextField;
    @FXML private TextField abbreviationTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
        updateAirlineComboBox();
    }

    public void editAirlineButtonClicked() {
        if (airlineChoiceComboBox.getValue() != null &&
                nameTextField.getText() != null &&
                abbreviationTextField.getText() != null) {
            disable();

            Airline airlineChosen = airlineChoiceComboBox.getValue();
            Airline newAirline = new Airline();
            newAirline.setAbbreviation(abbreviationTextField.getText());
            newAirline.setName(nameTextField.getText());

            try {
                airlineOperator.updateById(airlineChosen.getId(), newAirline);

                clearAllFields();
                updateAirlineComboBox();
                Util.setMessageLabel("Airline updated.", Color.GREEN, messageLabel);
            }
            catch (DuplicateKeyException dke) {
                Util.setMessageLabel("Airline not updated. Both the abbreviation and name fields are unique to an airline.", Color.RED, messageLabel);
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

    private void updateAirlineComboBox() {
        airlineChoiceComboBox.getItems().addAll(airlineOperator.selectAll());
    }

    /**
     * Called when the airline which is to be edited is chosen. Should fill the values of the TextFields and ComboBoxes
     * with what the airline currently has.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void airlineChosen(ActionEvent actionEvent) {
        nameTextField.setText(airlineChoiceComboBox.getValue().getName());
        abbreviationTextField.setText(airlineChoiceComboBox.getValue().getAbbreviation());
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        airlineChoiceComboBox.getItems().clear();
        airlineChoiceComboBox.setValue(null);
        nameTextField.clear();
        abbreviationTextField.clear();
    }
}
