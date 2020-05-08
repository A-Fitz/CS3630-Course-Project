package database.tables;

public class TicketStatusType implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String TITLE_COLUMN_NAME = "title";

    private Integer id;
    private String title;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
