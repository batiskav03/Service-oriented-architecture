package com.soa.workerservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Worker {
    @Id
    private UUID id;
    private String name;
    @ManyToOne
    private Coordinates coordinates;
    private Date creationDate;
    private Long salary;
    private Date startDate;
    private Position position;
    private Status status;
    @OneToOne
    private Person person;

}
