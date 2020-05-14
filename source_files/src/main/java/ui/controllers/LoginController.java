package ui.controllers;

import database.DatabaseConnection;
import database.operators.AirlineOperator;
import database.operators.SystemUserOperation;
import database.tables.Aircraft;
import database.tables.Airline;
import database.tables.SystemUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;
import ui.exceptions.IncorrectInputException;
import ui.formatters.IntegerTextFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends ThreeColumnController {
    private SystemUserOperation systemUserOperator = SystemUserOperation.getInstance();

    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Button exitButton;
    @FXML private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
                exitButton.getScene().getRoot().requestFocus());
    }

    /**
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void loginButtonClicked(ActionEvent actionEvent) {
        if (userNameTextField.getText() != null && passwordField.getText() != null) {
            disable();

            //isRegisteredUser=-1, if user doesn't exist;
            //isRegisteredUser=0, if user exist but password is wrong;
            //isRegisteredUser=1, if both username and password are correct;
            int isRegisteredUser= -1;
            SystemUser activeUser=null;
            List<SystemUser> userList = systemUserOperator.selectAll();
            for (SystemUser user : userList) {
                if (user.getUserName().equals(userNameTextField.getText()) &&
                        user.getPassword().equals(passwordField.getText())) {
                    isRegisteredUser = 1;
                    activeUser=user;
                }
                if (user.getUserName().equals(userNameTextField.getText()) &&
                        !user.getPassword().equals(passwordField.getText())) {
                    isRegisteredUser = 0;
                }
            }

            if (isRegisteredUser == 1) {
                Util.setMessageLabel("Logging...", Color.GREEN, messageLabel);
                switchStartScreen(activeUser);
            } else if (isRegisteredUser == 0) {
                userNameTextField.clear();
                Util.setMessageLabel("Password is wrong.", Color.RED, messageLabel);
            }else{
                userNameTextField.clear();
                passwordField.clear();
                Util.setMessageLabel("User doesn't exist.", Color.RED, messageLabel);
            }
            enable();
        } else {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }

    }

    /**
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void exitButtonClicked(ActionEvent actionEvent) {

    }

    private void switchStartScreen(SystemUser activeUser) {
        try {
            String fxmlPath = "src/main/resources/RunTimeStartScreen.fxml";
            FileInputStream fileInputStream = new FileInputStream(new File(fxmlPath));
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fileInputStream);
            RunTimeStartScreenController controller = loader.getController();
            controller.setActiveUser(activeUser);



            Stage runTimeStage = new Stage();
            runTimeStage.setScene(new Scene(root));
            runTimeStage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            runTimeStage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            runTimeStage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            runTimeStage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            runTimeStage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO better exception handling
        }
    }
}
