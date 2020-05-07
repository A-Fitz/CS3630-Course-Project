package database.tables;

import database.DatabaseObject;

public class AirportJobType extends DatabaseObject {
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

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Title: [" +
                title +
                "]";
    }
}
