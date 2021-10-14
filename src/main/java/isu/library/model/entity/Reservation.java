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
@Table(name = "blocking")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private @Id
    @GeneratedValue
    int id;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "person_id")
    private Integer parsonId;
    @Column(name = "date_from")
    private Date dateFrom;
    @Column(name = "date_to")
    private Date dateTo;
    @Column(name = "is_borrowed")
    private boolean borrowed;

    public Reservation(Integer bookId, Integer parsonId, Date dateFrom, Date dateTo, boolean borrowed) {
        this.bookId = bookId;
        this.parsonId = parsonId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.borrowed = borrowed;
    }
}
