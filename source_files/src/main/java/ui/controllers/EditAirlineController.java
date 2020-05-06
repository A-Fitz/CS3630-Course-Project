package ui.controllers;

import database.operators.AirlineOperator;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;
import ui.converters.AirlineStringConverter;
import ui.converters.AirportStringConverter;

public class EditAirlineController {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox airlineChoiceComboBox;
    @FXML private TextField nameTextField;
    @FXML private TextField abbreviationTextField;
    @FXML private Label messageLabel;

    public void initialize() {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
            updateAirlineChoiceComboBox();
        });
    }

    public void editAirlineButtonClicked()
    {
        if(airlineChoiceComboBox.getValue()!=null &&
                nameTextField.getText()!= null &&
                abbreviationTextField.getText()!= null)
        {
            Airline airlineChosen = (Airline)airlineChoiceComboBox.getValue();
            Airline newAirline=new Airline();
            newAirline.setAbbreviation(abbreviationTextField.getText());
            newAirline.setName(nameTextField.getText());

            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = airlineOperator.updateById(airlineChosen.getId(), newAirline);

            if (rowsAffected == 0) {
                Util.setMessageLabel("Airline not updated. Both the abbreviation and name fields are unique to an airline.", Color.RED, messageLabel);
            } else {
                clearAllFields();
                Util.setMessageLabel("Airline updated.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
            updateAirlineChoiceComboBox();
        }
        else
        {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void updateAirlineChoiceComboBox()
    {
        airlineChoiceComboBox.setConverter(new AirlineStringConverter());
        airlineChoiceComboBox.getItems().addAll(airlineOperator.selectAll());
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        airlineChoiceComboBox.getItems().clear();
        nameTextField.clear();
        abbreviationTextField.clear();
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
