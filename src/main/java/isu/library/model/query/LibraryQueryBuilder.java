package isu.library.model.query;

public class LibraryQueryBuilder {
    private String guery;
    private boolean whereComplete = false;

    public LibraryQueryBuilder() {
        this.guery = "SELECT * FROM library l";
    }

    private void addWhereOrAnd() {
        if (whereComplete) {
            this.guery += " AND ";
        } else {
            this.guery += " WHERE ";
            whereComplete = true;
        }
    }

    public LibraryQueryBuilder findByName(String libraryName) {
        addWhereOrAnd();
        this.guery += "l.name like '%" + libraryName + "%'";
        return this;
    }

    public LibraryQueryBuilder findByCity(String city) {
        addWhereOrAnd();
        this.guery += "l.city like '%" + city + "%'";
        return this;
    }

    public LibraryQueryBuilder findByTag(String tag) {
        addWhereOrAnd();
        this.guery += "l.tag like '%" + tag + "%'";
        return this;
    }

    public String getQuery() {
        return guery;
    }
}
