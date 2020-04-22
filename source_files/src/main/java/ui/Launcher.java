package ui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

/**
 * This class functions as a launcher. It will connect the client to the database and show them the start screen.
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

    @Override
    public void start(Stage stage) {
        Launcher.stage = stage;

        // show loading dialog until database is connected, then show start screen
        Alert loadingDialog = createLoadingDialog();
        task.setOnRunning((e) -> loadingDialog.show());
        task.setOnSucceeded((e) -> {
            loadingDialog.hide();
            try {
                // show the start screen
                Parent root = FXMLLoader.load(getClass().getResource("/StartScreen.fxml"));
                stage.setTitle("Airport Management System");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace(); // TODO better exception handling
                System.exit(0);
            }
        });
        new Thread(task).start();

        Launcher.stage.setOnCloseRequest(e -> System.exit(0));
    }

    private Alert createLoadingDialog() {
        Alert loadingDialog = new Alert(Alert.AlertType.INFORMATION);
        loadingDialog.setHeaderText(null);
        loadingDialog.setTitle("Airport Management System");
        loadingDialog.setContentText("Airport Management System is loading... Please Wait");
        loadingDialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        loadingDialog.initStyle(StageStyle.UNDECORATED);
        return loadingDialog;
    }
}
