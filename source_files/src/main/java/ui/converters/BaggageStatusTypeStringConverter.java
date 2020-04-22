package ui.converters;

import database.tables.BaggageStatusType;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and BaggageStatusType data objects. Useful for UI display of objects.
 */
public class BaggageStatusTypeStringConverter extends StringConverter<BaggageStatusType> {
    /**
     * Creates a String containing the information which can identify a given object.
     * @param baggageStatusType The given object.
     * @return The String.
     */
    @Override
    public String toString(BaggageStatusType baggageStatusType) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: [");
        sb.append(baggageStatusType.getTitle());
        sb.append("]");

        return sb.toString();
    }

    /**
     * Usually used to create an object from identifiable information. In this case we do not need to use it
     * as we have specific fields for each table's columns.
     * @param s Not used.
     * @return null
     */
    @Override
    public BaggageStatusType fromString(String s) {
        return null;
    }
}
