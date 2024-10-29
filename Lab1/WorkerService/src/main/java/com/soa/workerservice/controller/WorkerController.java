package com.soa.workerservice.controller;

import com.soa.workerservice.model.Coordinates;
import com.soa.workerservice.model.Worker;
import com.soa.workerservice.model.responses.MessageResponse;
import com.soa.workerservice.model.responses.WorkerResponse;
import com.soa.workerservice.service.WorkerService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@RestController
public class WorkerController {
    private final WorkerService workerService;

    private final String EMPTY_VALUE_CONSTANT = " ";

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
                .message("Ok")
                .worker(worker)
                .build();
    }

    @DeleteMapping("/worker/delete/{id}")
    public MessageResponse deleteWorker(@PathVariable String id) {

        UUID uuid;

        try {
            UUID workerId = workerService.deleteWorker(UUID.fromString(id));
            if (workerId == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(204)
                        .message("Resource not found")
                        .build();
            }
            return WorkerResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok")
                    .build();

        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry")
                    .build();
        }
    }

    @PatchMapping("/api/worker/update/{id}")
    public MessageResponse updateWorker(@PathVariable UUID id, @RequestBody Map<String, String> worker) {

        try {
            boolean modified = false;
            Worker oldWorker = workerService.getWorker(id);

            if (oldWorker == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }

            ArrayList<String> modifiedFieldsName = new ArrayList<>();
            ArrayList<Field> oldFields = new ArrayList<>(List.of(oldWorker.getClass().getFields()));
            for (String field : worker.keySet()) {
                var value = worker.getOrDefault(field, EMPTY_VALUE_CONSTANT);
                if (value.equals(EMPTY_VALUE_CONSTANT)) continue;
                if (workerService.updateWorkerField(id, field, value) == null) {
                    return MessageResponse.builder()
                            .date(new Date())
                            .code(204)
                            .message("No Content")
                            .build();
                };

            }

            Worker updatedWorker = workerService.getWorker(id);
            ArrayList<Field> updatedFields = new ArrayList<>(List.of(updatedWorker.getClass().getFields()));
            for (Field oldField : oldFields) {
                if (!oldField.equals(updatedFields.get(oldFields.indexOf(oldField)))) {
                    modified = true;
                    modifiedFieldsName.add(oldField.getName());
                }
            }

            if (modified) {
                return WorkerResponse.builder()
                        .date(new Date())
                        .code(200)
                        .message("Ok. Modified fields: " + modifiedFieldsName)
                        .build();
            }
            return WorkerResponse.builder()
                    .date(new Date())
                    .code(304)
                    .message("Business no modified don't disturb")
                    .build();
        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry")
                    .build();
        }

    }

}
