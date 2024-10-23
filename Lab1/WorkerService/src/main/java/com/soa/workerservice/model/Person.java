package com.soa.workerservice.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Person {
    private Date birthday;
    private UUID passportID;
    private HairColor hairColor;
    private Nationality nationality;
}
