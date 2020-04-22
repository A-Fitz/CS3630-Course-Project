package ui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class ExampleController implements Initializable {
    // TODO private operator someOperator = SomeOperator.getInstance();

    // Put your JavaFX components here. Buttons, Labels, TextFields, etc. The name of this variable will be the fx:id of the component in FXML.
    // Follow camelCase naming with the component type as the last phrase in the word (i.e. 'Button' is the last phrase).
    // Each controller should have the following components.
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    // The following are example components.
    @FXML private TextField exampleTextField1;
    @FXML private TextField exampleTextField2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus()); // Do this to stop text fields from getting auto focus (annoying).

        // This is where your code for doing any programatic ui adjustments should go.
    }

    /**
     * Called when the "do something" button is clicked. Use a method like this to check if their input is valid and
     * then try the operation. If it works display a success message, if not display an error message.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void somethingButtonClicked(ActionEvent actionEvent) {

        if (exampleTextField1.getText() != null && !exampleTextField1.getText().isEmpty()) {
            // Disable interacting with components while the operator is running. Must be done for multithreaded environment.
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = 0; // Ignore this. Look at line below.
            // TODO int rowsAffected = someOperator....

            if (rowsAffected == 0)
                Util.setMessageLabel("Something went wrong.", Color.RED, messageLabel);
            else {
                // Operation succeeded. Maybe clear the textfields on the screen?
                Util.setMessageLabel("Something done.", Color.GREEN, messageLabel);
            }

        } else {
            Util.setMessageLabel("Your input is wrong.", Color.RED, messageLabel);
        }
        mainGridPane.setDisable(false); // Don't forget to unlock the components.
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     * The name of this method is the 'onAction' FXML value for the backButton.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
