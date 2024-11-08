package com.soa.workerservice.model.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDetailsRequest<T> {
    private String field;
    private T value;
}
