package com.soa.workerservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Coordinates {
    @Id
    private UUID id;
    private Integer x;
    private Integer y;
}
