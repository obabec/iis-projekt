package isu.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    private @Id @GeneratedValue int id;
    @Column(name = "library_id")
    private Integer libraryId;
    private String name;
    private Date release;
    private String isbn;
    private String publisher;
    private String genre;
    private Short rate;

    public Book(Integer libraryId, String name, Date release, String isbn, String publisher, String genre, Short rate) {
        this.libraryId = libraryId;
        this.name = name;
        this.release = release;
        this.isbn = isbn;
        this.publisher = publisher;
        this.genre = genre;
        this.rate = rate;
    }
}
