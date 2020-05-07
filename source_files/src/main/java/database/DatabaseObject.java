package database;

/**
 * Class which represents a row in a table given by its name. Also can contain other user-readable data.
 * Created by operator classes and controllers.
 */
public abstract class DatabaseObject
{
    /**
     * Each DatabaseObject must have an id. This method returns that id.
     * @return The id of the row represented by this object.
     */
    public abstract Integer getId();

    /**
     * Each DatabaseObject must have an id. This method sets that id.
     * @param id The id of the row which will represent this object.
     */
    public abstract void setId(Integer id);

    /**
     * Each DatabaseObject must have a toString method, but only those which contain user-readable information
     * about the row should fully implement it. This is used by controllers, and shown in views.
     * @return Some text which contains user-readable information about this object.
     */
    public abstract String toString();
}
