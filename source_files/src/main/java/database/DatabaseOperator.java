package database;

import java.util.List;

/**
 * Interface is implemented by database operator classes which manage the queries to retrieve and manipulate
 * database object classes.
 * @param <DatabaseObject> A given DatabaseObject class. Yes this is just a generic name, but that is because
 *                            Java does not play nice when trying to use abstract classes in interfaces.
 */
public interface DatabaseOperator<DatabaseObject>
{
    /**
     * Selects a row from the database table which aligns with the DatabaseObject type and all of its applicable
     * information in the form of a DatabaseObject.
     *
     * @param id The value of the id column for a flight row
     * @return (null if no row exists with that id)
     * (a DatabaseObject if row exists with that id)
     */
    DatabaseObject selectById(int id);

    /**
     * Selects all rows from the database table which align with the DatabaseObject type and all of its applicable
     * information in the form of a list of DatabaseObjects.
     *
     * @return a list of DatabaseObjects
     */
    List<DatabaseObject> selectAll();


    /**
     * Tries to update a row in the table which aligns with the DatabaseObject type given an id and a
     * DatabaseObject containing the values to update with.
     *
     * @param id     The value of the id column of the row to update.
     * @param holdingUpdatedValues A java object representing the new values for the row.
     * @return (0 if the update failed, the id did not exist in the table) (1 if the row was successfully updated)
     */
    int updateById(int id, DatabaseObject holdingUpdatedValues);


    /**
     * Tries to insert a new row into the table which aligns with the DatabaseObject type given a
     * DatabaseObject containing the values to insert.
     *
     * @param toInsert The DatabaseObject which holds the data to insert into columns
     * @return (0 if a constraint was not met and the row could not be inserted) (1 if the row was inserted)
     */
    int insert(DatabaseObject toInsert);


    /**
     * Tries to delete a row in the table which aligns with the DatabaseObject type given an id.
     *
     * @param id The value of the id column of the row to delete.
     * @return (0 if the delete failed, the id did not exist in the table) (1 if the row was successfully deleted)
     */
    int deleteById(int id);
}
