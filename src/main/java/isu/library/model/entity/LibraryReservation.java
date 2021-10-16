package isu.library.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blocking")
@SecondaryTables({
        @SecondaryTable(name = "book"),
        @SecondaryTable(name = "person")
})
public class LibraryReservation {
    @Id
    private Integer id;
    @Column(name = "book_id", table = "book")
    private Integer bookId;
    @Column(name = "date_from")
    private Date dateFrom;
    @Column(name = "date_to")
    private Date dateTo;
    @Column(name = "name", table = "book")
    private String bookName;
    @Column(name = "isbn", table = "book")
    private String isbn;
    @Column(name = "name", table = "person")
    private String name;
    @Column(name = "surname", table = "person")
    private String surname;
    @Column(name = "birth_date", table = "person")
    private Date birthDate;
    @Column(name = "is_borrowed")
    private Boolean borrowed;
}
