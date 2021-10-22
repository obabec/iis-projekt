package isu.library.model.entity.vote;

import isu.library.model.entity.library.Library;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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

     @Column(name = "book_name")
     private String bookName;

    @ManyToOne
    @JoinColumn(name = "library_id", referencedColumnName = "id")
    private Library library;

    @Column(name = "vote_amount")
    private Integer voteAmount;

    @Transient
    private boolean enabled = true;

    public Vote(String bookName, Library library, Integer voteAmount) {
        this.bookName = bookName;
        this.library = library;
        this.voteAmount = voteAmount;
    }
}
