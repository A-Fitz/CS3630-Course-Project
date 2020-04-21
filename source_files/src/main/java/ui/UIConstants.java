package ui;

/**
 * Class contains static fields used in many UI related classes.
 */
public final class UIConstants {
    /**
     * Do not allow instantiation of this class.
     */
    private UIConstants()
    {}

    /** Displayed on screen when a database query is running. **/
    public static final String CONTROLLER_QUERY_RUNNING_MESSAGE = "Request in progress...";

    /** How long the message label should be displayed in seconds. **/
    public static final int MESSAGE_LABEL_DISPLAY_TIME_SECONDS = 5;
}
