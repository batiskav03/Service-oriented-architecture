package com.soa.workerservice.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDetails<T> {
    private String field;
    private T value;
}
