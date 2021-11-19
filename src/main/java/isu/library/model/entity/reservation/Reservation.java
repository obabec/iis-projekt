package isu.library.model.entity.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "blocking")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    int id;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "person_id")
    private Integer personId;
    @Column(name = "date_from")
    private Date dateFrom;
    @Column(name = "date_to")
    private Date dateTo;
    @Column(name = "is_borrowed")
    private Boolean borrowed;

    public Reservation(Integer bookId, Integer personId, Date dateFrom, Date dateTo, boolean borrowed) {
        this.bookId = bookId;
        this.personId = personId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.borrowed = borrowed;
    }
}
