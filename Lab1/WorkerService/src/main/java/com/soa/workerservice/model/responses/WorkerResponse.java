package com.soa.workerservice.model.responses;

import com.soa.workerservice.model.Worker;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WorkerResponse extends MessageResponse {
    private Worker worker;
}
