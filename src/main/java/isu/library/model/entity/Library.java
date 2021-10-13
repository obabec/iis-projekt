package isu.library.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.Date;

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
    private Integer streetNumber;
    @Column(name = "from_time")
    private LocalTime openFrom;
    @Column(name = "to_time")
    private LocalTime openTo;
    private String description;
}
