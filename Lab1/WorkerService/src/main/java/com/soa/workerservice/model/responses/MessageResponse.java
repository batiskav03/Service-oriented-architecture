package com.soa.workerservice.model.responses;

import lombok.Data;

import java.util.Date;

@Data
public class MessageResponse {

    private Integer code;
    private Date date;
    private String message;
}
