package database.tables;

public class Airline implements DatabaseObject {
    /* Basic information */
    public static final String ID_COLUMN_NAME = "id";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String ABBREVIATION_COLUMN_NAME = "abbreviation";

    private Integer id;
    private String name;
    private String abbreviation;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "Name: [" +
                name +
                "] " +
                "Abbreviation: [" +
                abbreviation +
                "]";
    }
}
