package com.soa.worker.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates;
    @Column(name = "creation_date")
    private Date creationDate;
    private Double salary;
    @Column(name = "start_date")
    private Date startDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Position position;
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;


} 