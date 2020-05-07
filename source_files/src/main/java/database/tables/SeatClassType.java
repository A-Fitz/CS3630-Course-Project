package database.tables;

import database.DatabaseObject;

public class SeatClassType extends DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String TITLE_COLUMN_NAME = "title";

    private Integer id;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    @Override
    public String toString() {
        return "Title: [" +
                title +
                "]";
    }
}
