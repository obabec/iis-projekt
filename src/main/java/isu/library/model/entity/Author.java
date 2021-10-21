package isu.library.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(columnDefinition = "SERIAL") int id;
    private String name;
    private String surname;
}