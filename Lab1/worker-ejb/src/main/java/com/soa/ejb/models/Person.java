package com.soa.ejb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    @Id
    private UUID id;
    private Date birthday;
    private UUID passportID;
    private HairColor hairColor;
    private Nationality nationality;
}
