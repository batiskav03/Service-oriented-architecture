package com.soa.ejb.models.responses;

import com.soa.ejb.models.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class WorkerResponse extends MessageResponse implements Serializable {

    public WorkerResponse() {}

    private Worker worker;
}
