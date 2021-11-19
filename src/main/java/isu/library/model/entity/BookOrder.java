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

@Entity
@Table(name = "book_order")
@Getter
@Setter
@NoArgsConstructor
public class BookOrder {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    int id;
    @Column(name = "title_id")
    private Integer titleId;
    @Column(name = "library_id")
    private Integer libraryId;
    private Integer count;

    @Transient
    private String titleName;
    @Transient
    private String libraryName;

    public BookOrder(int libraryId, int titleId, int count) {
        this.libraryId = libraryId;
        this.titleId = titleId;
        this.count = count;
    }
}
