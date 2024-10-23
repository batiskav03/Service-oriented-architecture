package com.soa.workerservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Person {
    @Id
    private UUID id;
    private Date birthday;
    private UUID passportID;
    private HairColor hairColor;
    private Nationality nationality;
}
