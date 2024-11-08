package com.soa.workerservice.controller;


import com.soa.workerservice.model.request.PageableAndSortingRequest;
import com.soa.workerservice.model.request.UpdateDetailsRequest;
import com.soa.workerservice.model.Worker;
import com.soa.workerservice.model.responses.PageResponse;
import com.soa.workerservice.model.responses.MessageResponse;
import com.soa.workerservice.model.responses.WorkerResponse;
import com.soa.workerservice.service.WorkerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public <T> MessageResponse updateWorker(@PathVariable UUID id, @RequestBody UpdateDetailsRequest<T> updateDetailsRequest) {

        try {
            boolean modified = false;
            String field = updateDetailsRequest.getField();
            Object value = updateDetailsRequest.getValue();
            Worker oldWorker = workerService.getWorker(id);
            Field current_field = oldWorker.getClass().getDeclaredField(field);
            current_field.setAccessible(true);
            Object current_value = current_field.get(oldWorker);
            if (oldWorker == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }
            workerService.updateWorkerField(id, field, value);
            if (current_value != null) {
                if (current_value.equals(value)) {
                    return MessageResponse.builder()
                            .date(new Date())
                            .code(304)
                            .message("Business no modified don't disturb")
                            .build();
                }
            }
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, UUID of updated worker is " + id.toString())
                    .build();
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry")
                    .build();
        }

    }
    @PostMapping("/api/worker/create")
    public MessageResponse createWorker(@RequestBody Worker worker) {
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
    public MessageResponse getAllWorkers(@RequestBody PageableAndSortingRequest request) {
        Page<Worker> result = workerService.getAllWorkers(request.getSorting(), request.getFilter(), request.getPage(), request.getPageSize());
        return new PageResponse<>(200, new Date(), "Ok", result);
        //TODO: Add logic
    }

    @GetMapping("/api/worker/getUniqPosition")
    public MessageResponse getUniqWorkersByPosition() {
        try {
            List<Worker> workers = workerService.selectUniqWorkerPositions();
            if (workers == null) {
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
