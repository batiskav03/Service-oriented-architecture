package com.soa.workerservice.controller;

import com.soa.workerservice.ejb.WorkerRemote;
import com.soa.workerservice.ejb.models.Worker;
import com.soa.workerservice.ejb.models.responses.MessageResponse;
import com.soa.workerservice.ejb.models.responses.PageResponse;
import com.soa.workerservice.ejb.models.request.UpdateDetailsRequest;
import com.soa.workerservice.ejb.models.request.PageableAndSortingRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.naming.InitialContext;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class WorkerController {
    
    private final WorkerRemote workerBean;
    
    public WorkerController() {
        try {
            InitialContext context = new InitialContext();
            workerBean = (WorkerRemote) context.lookup("java:global/worker-ejb/WorkerBean!com.soa.workerservice.ejb.WorkerRemote");
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup EJB", e);
        }
    }
    
    @GetMapping("api/worker/get/{id}")
    public MessageResponse getWorker(@PathVariable String id) {
        try {
            return workerBean.getWorker(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }
    
    @DeleteMapping("api/worker/delete/{id}")
    public MessageResponse deleteWorker(@PathVariable String id) {
        try {
            return workerBean.deleteWorker(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @PatchMapping("/api/worker/update/{id}")
    public MessageResponse updateWorker(@PathVariable UUID id, @RequestBody UpdateDetailsRequest<?> updateDetailsRequest) {
        return workerBean.updateWorkerField(id, updateDetailsRequest.getField(), updateDetailsRequest.getValue());
    }

    @PostMapping("/api/worker/create")
    public MessageResponse createWorker(@RequestBody Worker worker) {
        return workerBean.createWorker(worker);
    }

    @GetMapping("/api/workers/get")
    public MessageResponse getAllWorkers(@RequestBody PageableAndSortingRequest request) {
        Page<Worker> result = workerBean.getAllWorkers(
            request.getSorting(), 
            request.getFilter(),
            request.getPage(), 
            request.getPageSize()
        );
        return new PageResponse<>(200, new Date(), "Ok", result);
    }

    @GetMapping("/api/worker/getUniqPosition")
    public MessageResponse getUniqWorkersByPosition() {
        List<Worker> workers = workerBean.selectUniqWorkerPositions();
        if (workers == null) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(204)
                    .message("No Content")
                    .build();
        }
        return MessageResponse.builder()
                .date(new Date())
                .code(200)
                .message("Ok, Uniq Workers by positions - " + workers)
                .build();
    }
} 