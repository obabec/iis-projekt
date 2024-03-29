/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.util.ArrayList;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    int id;
    @Column(name = "library_id")
    private Integer libraryId;
    private String name;
    private Date release;
    private String isbn;
    private String publisher;
    private String genre;
    private Short rate;

    @Transient
    private String libraryName;

    @Transient
    private ArrayList<Integer> authors;
    @Transient
    private ArrayList<String> authors_names;

    public Book(Integer libraryId, String name, Date release, String isbn, String publisher, String genre, Short rate) {
        this.libraryId = libraryId;
        this.name = name;
        this.release = release;
        this.isbn = isbn;
        this.publisher = publisher;
        this.genre = genre;
        this.rate = rate;
    }

    public Book(String name, Date release, String isbn, String publisher, String genre, Short rate) {
        this.name = name;
        this.release = release;
        this.isbn = isbn;
        this.publisher = publisher;
        this.genre = genre;
        this.rate = rate;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}
