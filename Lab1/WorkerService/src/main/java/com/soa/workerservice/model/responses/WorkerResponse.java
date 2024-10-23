package com.soa.workerservice.model.responses;

import com.soa.workerservice.model.Worker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class WorkerResponse extends MessageResponse {
    private Worker worker;
}
