package com.soa.workerservice.ejb.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
public class MessageResponse {

    private Integer code;
    private Date date;
    private String message;
}
