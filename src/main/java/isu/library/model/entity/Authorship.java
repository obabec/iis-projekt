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

@Entity
@Table(name = "authorship")
@NoArgsConstructor
@Getter
@Setter
public class Authorship {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(columnDefinition = "SERIAL") int id;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "author_id")
    private Integer authorId;

    public Authorship(int authorId, int bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }
}
