package com.soa.ejb.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
public class MessageResponse implements Serializable {

    public MessageResponse() {}

    private Integer code;
    private Date date;
    private String message;
}
