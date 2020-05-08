package database.tables;

/**
 * Interface which is implemented by clases which represent a row in a table given by the class's name.
 * Also can contain other user-readable data.
 * Created by operator classes and controllers.
 */
public interface DatabaseObject
{
    /**
     * Each DatabaseObject must have an id. This method returns that id.
     * @return The id of the row represented by this object.
     */
    Integer getId();

    /**
     * Each DatabaseObject must have an id. This method sets that id.
     * @param id The id of the row which will represent this object.
     */
    void setId(Integer id);

    /**
     * Each DatabaseObject must have a toString method, but only those which contain user-readable information
     * about the row should fully implement it. This is used by controllers, and shown in views.
     * @return Some text which contains user-readable information about this object.
     */
    String toString();
}
