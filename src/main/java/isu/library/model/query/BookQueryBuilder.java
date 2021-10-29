package isu.library.model.query;

import java.sql.Date;
import java.time.LocalDate;


public class BookQueryBuilder {
    private String query;
    private boolean whereComplete = false;

    private void addWhereOrAnd() {
        if (whereComplete) {
            this.query += " AND ";
        } else {
            this.query += " WHERE ";
            whereComplete = true;
        }
    }

    public BookQueryBuilder() {
        this.query = "SELECT b.id, b.library_id, b.name, b.release, b.isbn, b.publisher, b.genre, b.rate FROM book b";
    }

    public BookQueryBuilder filterByAuthor(String authorName) {
        whereComplete = true;
        this.query += " INNER JOIN authorship a ON b.id = a.book_id INNER JOIN author p ON a.author_id = p.id WHERE CONCAT(p.name, ' ', p.surname) LIKE '%" + authorName + "%'";
        return this;
    }

    public BookQueryBuilder filterTitles() {
        addWhereOrAnd();
        this.query += "library_id IS NULL";
        return this;
    }

    public BookQueryBuilder filterBooks() {
        addWhereOrAnd();
        this.query += "library_id IS NOT NULL";
        return this;
    }

    public BookQueryBuilder filterByLibrary(String libraryName) {
        addWhereOrAnd();
        this.query += "library_id IN (select id from library where name like '%" + libraryName + "%')";
        return this;
    }

    public BookQueryBuilder filterByLibraryId(Integer libraryId) {
        addWhereOrAnd();
        this.query += "library_id IN (select id from library where id = " + libraryId + ")";
        return this;
    }

    public BookQueryBuilder filterByAvailability() {
        addWhereOrAnd();
        this.query += "b.id NOT IN (SELECT book_id FROM blocking WHERE CURRENT_DATE BETWEEN date_from AND date_to)";
        return this;
    }

    public BookQueryBuilder filterByName(String bookName) {
        addWhereOrAnd();
        this.query += "b.name LIKE '%" + bookName + "%'";
        return this;
    }

    public BookQueryBuilder filterById(Integer id) {
        addWhereOrAnd();
        this.query += "b.id = " + id;
        return this;
    }

    public BookQueryBuilder filterByGenre(String genre) {
        addWhereOrAnd();
        this.query += "b.genre LIKE '%" + genre + "%'";
        return this;
    }

    public BookQueryBuilder filterByIsbn(String isbn) {
        addWhereOrAnd();
        this.query += "b.isbn LIKE '%" + isbn + "%'";
        return this;
    }

    public BookQueryBuilder filterByPublisher(String publisher) {
        addWhereOrAnd();
        this.query += "b.publisher LIKE '%" + publisher + "%'";
        return this;
    }

    public BookQueryBuilder filterByRate(Integer rate) {
        addWhereOrAnd();
        this.query += "b.rate = " + rate;
        return this;
    }

    public BookQueryBuilder filterByReleaseUnder(int year) {
        LocalDate ld = LocalDate.of(year, 1, 1);
        addWhereOrAnd();
        this.query += "b.release <= '" + Date.valueOf(ld) + "'";
        return this;
    }

    public BookQueryBuilder filterByReleaseAbove(int year) {
        LocalDate ld = LocalDate.of(year, 1, 1);
        addWhereOrAnd();
        this.query += "b.release <>= '" + Date.valueOf(ld) + "'";
        return this;
    }

    public String getQuery() {
        return this.query;
    }
}
