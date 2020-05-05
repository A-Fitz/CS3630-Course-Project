package ui.formatters;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;

import java.util.function.UnaryOperator;

/**
 * This class is used to force a TextField to allow only Float input.
 */
public class FloatTextFormatter extends TextFormatter<Float> {

    public FloatTextFormatter() {
        super(new FloatStringConverter(), null, new FloatFilter());
    }

    private static class FloatFilter implements UnaryOperator<Change> {
        @Override
        public Change apply(Change change) {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() == 0) {
                    return change;
                }
                try {
                    Float.parseFloat(change.getControlNewText());
                    return change;
                } catch (NumberFormatException e) {
                }
                return null;

            }
            return change;
        }
    }
}