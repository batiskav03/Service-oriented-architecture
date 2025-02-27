package com.soa.ejb.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Getter

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {
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
