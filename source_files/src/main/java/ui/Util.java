package ui;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Contains static methods which are used by multiple UI classes.
 */
public class Util {
    private static PauseTransition messageLabelDelay;

    /**
     * Displays some colored message in a label for a given number of seconds.
     * If called again before timer ends, cancel first timer.
     *
     * @param text         The text to display in messageLabel
     * @param color        The text color to display in messageLabel
     * @param duration     The duration to display this messageLabel (in seconds)
     * @param messageLabel The label to change.
     */
    public static void setMessageLabel(String text, Color color, int duration, Label messageLabel) {
        messageLabel.setText(text);
        messageLabel.setTextFill(color);

        if (messageLabelDelay != null)
            messageLabelDelay.stop();

        messageLabelDelay = new PauseTransition(Duration.seconds(duration));
        messageLabelDelay.setOnFinished(event ->
        {
            messageLabel.setText("");
            messageLabel.setTextFill(Color.BLACK);
        });
        messageLabelDelay.play();
    }
}
