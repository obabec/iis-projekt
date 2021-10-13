package isu.library.model.query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;


public class BookQueryBuilder {
    private String guery;
    private boolean whereComplete = false;
    public BookQueryBuilder(String guery) {
        this.guery = guery;
    }

    private void addWhereOrAnd() {
        if (whereComplete) {
            this.guery += " AND ";
        } else {
            this.guery += " WHERE ";
            whereComplete = true;
        }
    }

    public BookQueryBuilder() {
        this.guery = "SELECT b.id, b.name, b.release, b.isbn, b.publisher, b.genre, b.rate FROM book b";
    }

    public BookQueryBuilder filterByAuthor(String authorName) {
        whereComplete = true;
        this.guery += " INNER JOIN authorship a ON b.id = a.book_id INNER JOIN person p ON a.person_id = p.id WHERE p.surname LIKE '%" + authorName + "%'";
        return this;
    }

    public BookQueryBuilder filterByLibrary(String libraryName) {
        addWhereOrAnd();
        this.guery += "library_id IN (select id from library where name like '%" + libraryName + "%')";
        return this;
    }

    public BookQueryBuilder filterByAvailability() {
        addWhereOrAnd();
        this.guery += "b.id NOT IN (SELECT book_id FROM blocking WHERE CURRENT_DATE BETWEEN date_from AND date_to)";
        return this;
    }

    public BookQueryBuilder filterByName(String bookName) {
        addWhereOrAnd();
        this.guery += "b.name LIKE '%" + bookName + "%'";
        return this;
    }

    public BookQueryBuilder filterByGenre(String genre) {
        addWhereOrAnd();
        this.guery += "b.genre LIKE '%" + genre + "%'";
        return this;
    }

    public BookQueryBuilder filterByIsbn(String isbn) {
        addWhereOrAnd();
        this.guery += "b.isbn = " + isbn;
        return this;
    }

    public BookQueryBuilder filterByPublisher(String publisher) {
        addWhereOrAnd();
        this.guery += "b.publisher LIKE '%" + publisher + "%'";
        return this;
    }

    public BookQueryBuilder filterByRate(Integer rate) {
        addWhereOrAnd();
        this.guery += "b.rate = " + rate;
        return this;
    }

    public BookQueryBuilder filterByReleaseUnder(int year) {
        LocalDate ld = LocalDate.of(year, 1, 1);
        addWhereOrAnd();
        this.guery += "b.release <= " + Date.valueOf(ld);;
        return this;
    }

    public BookQueryBuilder filterByReleaseAbove(int year) {
        LocalDate ld = LocalDate.of(year, 1, 1);
        addWhereOrAnd();
        this.guery += "b.release <>= " + Date.valueOf(ld);;
        return this;
    }

    public String getQuery() {
        return this.guery;
    }
}
