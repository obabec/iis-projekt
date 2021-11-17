package isu.library.model.entity.reservation;

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
})
public class UserReservation {
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
    @Column(name = "is_borrowed")
    private Boolean borrowed;

    @Transient
    private Boolean extendPossible = true;
}
