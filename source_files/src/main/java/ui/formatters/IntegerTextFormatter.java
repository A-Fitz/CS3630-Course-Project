package ui.formatters;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

/**
 * This class is used to force a TextField to allow only Integer input.
 */
public class IntegerTextFormatter extends TextFormatter<Integer> {

    public IntegerTextFormatter() {
        super(new IntegerStringConverter(), null, new IntegerFilter());
    }

    private static class IntegerFilter implements UnaryOperator<Change> {
        @Override
        public Change apply(TextFormatter.Change change) {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() == 0) {
                    return change;
                }
                try {
                    Integer.parseInt(change.getControlNewText());
                    return change;
                } catch (NumberFormatException ignored) {
                }
                return null;

            }
            return change;
        }
    }
}