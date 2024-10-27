package com.soa.workerservice.controller;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.model.responses.MessageResponse;
import com.soa.workerservice.model.responses.WorkerResponse;
import com.soa.workerservice.service.WorkerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class WorkerController {
    private final WorkerService workerService;
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }
    //todo: 500 & 503 response
    @GetMapping("/worker/get/{id}")
    public MessageResponse getWorker(@PathVariable String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry")
                    .build();
        }
        Worker worker = workerService.getWorker(uuid);
        if (worker == null) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(404)
                    .message("Worker not found")
                    .build();
        }

        return WorkerResponse.builder()
                .date(new Date())
                .code(200)
                .message("OK")
                .worker(worker)
                .build();
    }


}
