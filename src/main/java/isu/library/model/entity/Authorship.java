package isu.library.model.entity;


import isu.library.model.repository.AuthorshipRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "person_id")
    private Integer personId;

    public Authorship(int personId, int bookId) {
        this.personId = personId;
        this.bookId = bookId;
    }
}
