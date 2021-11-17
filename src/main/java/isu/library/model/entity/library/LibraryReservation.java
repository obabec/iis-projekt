package isu.library.model.entity.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(columnDefinition = "SERIAL")
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
