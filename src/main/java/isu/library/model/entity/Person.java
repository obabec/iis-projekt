/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
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
import java.sql.Date;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SERIAL")
    int id;
    private String name;
    private String surname;
    @Column(name = "birth_date")
    private Date birthDate;
    private String role;
    private String username;
    private String password;
    @Column(name = "library_id")
    private Integer libraryId;

    public Person(String name, String surname, Date birthDate, String role, String username, String password, Integer libraryId) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.role = role;
        this.username = username;
        this.password = password;
        this.libraryId = libraryId;
    }

}
