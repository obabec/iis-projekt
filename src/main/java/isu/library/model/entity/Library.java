package isu.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "library")
@Getter
@Setter
@NoArgsConstructor
public class Library {
    private @Id
    @GeneratedValue
    int id;
    private String name;
    private String tag;
    private String street;
    private String city;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "from_time")
    private Time openFrom;
    @Column(name = "to_time")
    private Time openTo;
    private String description;

    public Library(String name, String tag, String street, String city, String streetNumber, Time openFrom, Time openTo, String description) {
        this.name = name;
        this.tag = tag;
        this.street = street;
        this.city = city;
        this.streetNumber = streetNumber;
        this.openFrom = openFrom;
        this.openTo = openTo;
        this.description = description;
    }
}
