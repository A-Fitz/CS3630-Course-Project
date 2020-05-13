package ui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

/**
 * This class functions as a launcher. It will connect the client to the database and show them the start screen.
 * Also handles failures in connecting to the database and loading the start screen.
 */
public class Launcher extends Application {
    private static Stage stage;

    /**
     * This task is used to make a connection to the database before the program can be used by a user.
     */
    private Task<Boolean> task = new Task<>() {
        @Override
        public Boolean call() throws SQLException {
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            return databaseConnection.operate();
        }
    };

    public static void main(String[] args) {
        launch();
    }

    public static void closeStage() {
        Launcher.stage.close();
    }

    public static void showStage() {
        Launcher.stage.show();
    }

    /**
     * Entry point for the application. Called after the class is constructed and the init() method is called.
     * This will delegate a task to connect to the database, it will handle success and failure of that task.
     * @param stage New primary stage on application start
     */
    @Override
    public void start(Stage stage) {
        Launcher.stage = stage;

        // show loading dialog until database is connected, then show start screen
        Alert dialog = createLoadingDialog();
        task.setOnRunning((e) -> dialog.show());
        task.setOnSucceeded((e) -> {
            try {
                // show the start screen
                Parent root = FXMLLoader.load(getClass().getResource("/StartScreen.fxml"));
                stage.setTitle("Airport Management System");
                stage.setScene(new Scene(root));
                stage.show();
                dialog.hide();
            } catch (Exception ex) {
                changeDialogToErrorDialog(dialog, "Could not load the Start Screen.");
            }
        });
        task.setOnFailed(e -> {
            changeDialogToErrorDialog(dialog, "Could not connect to the database.");
        });
        task.setOnCancelled(e -> {
            changeDialogToErrorDialog(dialog, "Connection cancelled.");
        });
        new Thread(task).start();

        Launcher.stage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * Creates a dialog which is shown as sort of a splash screen. Lets the user know that the application is loading
     * instead of showing them nothing.
     * @return A new dialog, used to show that the system is loading
     */
    private Alert createLoadingDialog() {
        Alert loadingDialog = new Alert(Alert.AlertType.INFORMATION);
        loadingDialog.setHeaderText(null);
        loadingDialog.setTitle("Airport Management System");
        loadingDialog.setContentText("Airport Management System is loading... Please Wait");
        loadingDialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        loadingDialog.initStyle(StageStyle.UTILITY);
        return loadingDialog;
    }

    /**
     * Changes the loading dialog to an error dialog. Displays a custom message of what the error was.
     * @param dialog Dialog to change (the loading dialog)
     * @param message What error message to display alongside the base message
     */
    private void changeDialogToErrorDialog(Alert dialog, String message)
    {
        dialog.setAlertType(Alert.AlertType.ERROR);
        dialog.getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        Button closeButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.setOnAction(eventCloseClicked -> System.exit(0));
        dialog.setContentText("Airport Management System failed to load. " + message);
    }
}
