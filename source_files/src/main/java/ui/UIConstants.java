package ui;

/**
 * Class contains static fields used in many UI related classes.
 */
public final class UIConstants {
    /**
     * Displayed on screen when a database query is running.
     **/
    public static final String CONTROLLER_QUERY_RUNNING_MESSAGE = "Request in progress...";
    /**
     * How long the message label should be displayed in seconds.
     **/
    public static final int MESSAGE_LABEL_DISPLAY_TIME_SECONDS = 5;

    public static final double VIEW_MIN_WIDTH = 800.00;
    public static final double VIEW_MIN_HEIGHT = 450.00;
    public static final double VIEW_PREFERRED_WIDTH = 1280.00;
    public static final double VIEW_PREFERRED_HEIGHT = 720.00;
    public static final double VIEW_MAIN_GRIDPANE_CONTENT_ROW_HEIGHT_PERCENTAGE = 95.00;
    public static final double VIEW_MAIN_GRIDPANE_BACK_BUTTON_GRIDPANE_ROW_HEIGHT_PERCENTAGE = 5.00;
    public static final double VIEW_TRIPLE_COLUMN_GRIDPANE_CONTENT_COLUMN_WIDTH_PERCENTAGE = 50.00;
    public static final double VIEW_TRIPLE_COLUMN_GRIDPANE_NON_CONTENT_COLUMN_WIDTH_PERCENTAGE = 25.00;
    public static final double VIEW_VBOX_HBOX_SPACING = 5.00;
    public static final double VIEW_INSETS = 5.00;
    public static final double VIEW_BACK_BUTTON_GRIDPANE_NON_BUTTON_COLUMN_WIDTH_PERCENTAGE = 95.00;
    public static final double VIEW_BACK_BUTTON_GRIDPANE_BUTTON_COLUMN_WIDTH_PERCENTAGE = 5.00;
    public static final double VIEW_LABEL_COLUMN_WIDTH_PERCENTAGE = 15.00;
    public static final double VIEW_SELECTOR_COLUMN_WIDTH_PERCENTAGE = 85.00;

    /**
     * Do not allow instantiation of this class.
     */
    private UIConstants() {
    }
}
