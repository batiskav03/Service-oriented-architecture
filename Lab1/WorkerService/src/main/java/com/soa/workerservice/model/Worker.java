package com.soa.workerservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Getter
@Setter
@SecondaryTable(name = "coordinates", pkJoinColumns = @PrimaryKeyJoinColumn(name = "coordinates_id"))
@SecondaryTable(name = "person", pkJoinColumns = @PrimaryKeyJoinColumn(name = "person_id"))
public class Worker {
    @Id
    private UUID id;
    private String name;
    @ManyToOne
    private Coordinates coordinates;
    private Date creationDate;
    private Long salary;
    private Date startDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Position position;
    @OneToOne
    private Person person;

}
