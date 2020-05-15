package ui.controllers;

import database.operators.AirlineJobTypeOperator;
import database.operators.AirportJobTypeOperator;
import database.tables.AirlineJobType;
import database.tables.AirportJobType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class AddJobTypeController extends DatabaseManipulatingController {

    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    @FXML private ComboBox<String> regionComboBox;
    @FXML private TextField jobTypeTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
                backButton.getScene().getRoot().requestFocus());
    }

    public void addJobTitleButtonClicked() {
        if (regionComboBox.getValue() != null &&
                jobTypeTextField.getText() != null && !jobTypeTextField.getText().isEmpty()) {
            disable();
            if (regionComboBox.getValue().equals("Airport")) {
                setAirportJobType();
            } else if (regionComboBox.getValue().equals("Airline")) {
                setAirlineJobType();
            }
            enable();
        } else {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void setAirportJobType() {
        AirportJobType airportJobType = new AirportJobType();
        airportJobType.setTitle(jobTypeTextField.getText());

        try {
            airportJobTypeOperator.insert(airportJobType);

            clearAllFields();
            Util.setMessageLabel("Airport Job Title added.", Color.GREEN, messageLabel);
        }
        catch (DuplicateKeyException dke) {
            Util.setMessageLabel("Airport job type not added. The title is unique to an airport job type.", Color.RED, messageLabel);
        } catch (DataAccessException dae) {
            Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
        } catch (Exception e) {
            Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
        }
    }

    private void setAirlineJobType() {
        AirlineJobType airlineJobType = new AirlineJobType();
        airlineJobType.setTitle(jobTypeTextField.getText());

        try {
            airlineJobTypeOperator.insert(airlineJobType);

            clearAllFields();
            Util.setMessageLabel("Airline Job Title added.", Color.GREEN, messageLabel);
        }
        catch (DuplicateKeyException dke) {
            Util.setMessageLabel("Airline job type not added. The title is unique to an airline job type.", Color.RED, messageLabel);
        } catch (DataAccessException dae) {
            Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
        } catch (Exception e) {
            Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
        }
    }

    private void clearAllFields() {
        regionComboBox.setValue(null);
        jobTypeTextField.clear();
    }
}
