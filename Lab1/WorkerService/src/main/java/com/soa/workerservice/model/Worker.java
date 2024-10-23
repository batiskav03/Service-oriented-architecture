package com.soa.workerservice.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Worker {
    private UUID id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Long salary;
    private Date startDate;
    private Position position;
    private Status status;
    private Person person;

}
