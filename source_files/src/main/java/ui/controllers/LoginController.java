package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.UserTypes;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private ComboBox<UserTypes> userTypeComboBox;
    @FXML private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userTypeComboBox.getItems().addAll(UserTypes.values());
    }

    /**
     * Called when the login button is clicked. Handles validating the user's choice of user type.
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void loginButtonClicked(ActionEvent actionEvent) {
        if (userTypeComboBox.getValue() != null) {
            switchStartScreen(userTypeComboBox.getValue());
        } else {
            Util.setMessageLabel("Error. Please choose a user type.", Color.RED, messageLabel);
        }

    }

    private void switchStartScreen(UserTypes activeUserType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ButtonScreen.fxml"));
            Parent root = fxmlLoader.load();
            ButtonScreenController buttonScreenController = fxmlLoader.getController();
            buttonScreenController.setActiveUserType(userTypeComboBox.getValue());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Button Screen");
            stage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            stage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            stage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            stage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            stage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            Util.setMessageLabel("Error loading the next screen.", Color.RED, messageLabel);
        }
    }


}
