package com.soa.workerservice.model.responses;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class MessageResponse {

    private Integer code;
    private Date date;
    private String message;
}
