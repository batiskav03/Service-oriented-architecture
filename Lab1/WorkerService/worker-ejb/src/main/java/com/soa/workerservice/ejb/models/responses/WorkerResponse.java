package com.soa.workerservice.ejb.models.responses;

import com.soa.workerservice.ejb.models.Worker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class WorkerResponse extends MessageResponse {
    private Worker worker;
}
