package isu.library.model.entity.vote;

import isu.library.model.entity.Book;
import isu.library.model.entity.library.Library;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private Library library;

    @Column(name = "vote_amount")
    private Integer voteAmount;

    @Transient
    private boolean enabled = true;

    public Vote(Book book, Library library, Integer voteAmount) {
        this.book = book;
        this.library = library;
        this.voteAmount = voteAmount;
    }
}