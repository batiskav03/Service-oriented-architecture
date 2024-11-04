package com.soa.workerservice.controller;

import com.soa.workerservice.model.Coordinates;
import com.soa.workerservice.model.Worker;
import com.soa.workerservice.model.responses.MessageResponse;
import com.soa.workerservice.model.responses.WorkerResponse;
import com.soa.workerservice.service.WorkerService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @GetMapping("api/worker/get/{id}")
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

    @DeleteMapping("api/worker/delete/{id}")
    public MessageResponse deleteWorker(@PathVariable String id) {

        UUID workerId;

        try {
            workerId = workerService.deleteWorker(UUID.fromString(id));
            if (workerId == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(204)
                        .message("Resource not found")
                        .build();
            }
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, fired worker id is: " + workerId)
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
                }
                ;

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
    //todo: sout out
    @PostMapping("/api/worker/create")
    public MessageResponse createWorker(@RequestBody Worker worker) {
        System.out.println(worker.toString());
        try {
            boolean created = false;
            UUID uuid = workerService.createWorker(worker);
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, UUID of created worker is " + uuid.toString())
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

    @GetMapping("/api/workers/get")
    public MessageResponse getAllWorkers(@RequestBody String sorting) {
        return MessageResponse.builder().
                date(new Date())
                .code(200)
                .message("Ok")
                .build();
        //TODO: Add logic
    }

    @GetMapping("/api/worker/getUniqPosition")
    public MessageResponse getUniqWorkersByPosition() {
        try {
            List<Worker> workers = workerService.selectUniqWorkerPositions();
            if (workers.isEmpty()) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(204)
                        .message("No Content")
                        .build();
            }
            return MessageResponse.builder().
                    date(new Date())
                    .code(200)
                    .message("Ok, Uniq Workers by positions - " + workers)
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
