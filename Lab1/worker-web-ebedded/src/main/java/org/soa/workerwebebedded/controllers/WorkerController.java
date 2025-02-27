package org.soa.workerwebebedded.controllers;

import com.soa.ejb.WorkerRemote;
import com.soa.ejb.models.Worker;
import com.soa.ejb.models.request.UpdateDetailsRequest;
import com.soa.ejb.models.responses.MessageResponse;
import com.soa.ejb.models.responses.WorkerResponse;
import com.soa.ejb.remotes.MyEjbRemote;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WorkerController {

    private Context context;

    private WorkerRemote workerBean;

    public WorkerController(Context context) {
        this.context = context;
        try {
            workerBean = (WorkerRemote) context.lookup("ejb:/worker-ejb-1.0-SNAPSHOT-jar-with-dependencies/WorkerBean!com.soa.ejb.WorkerRemote");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/api/worker/get/{id}")
    public MessageResponse getWorker(@PathVariable String id) {
        try {
            Worker res = workerBean.getWorker(UUID.fromString(id));
            if (res == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }
            return WorkerResponse.builder().worker(res).code(200).message("ok").date(new Date()).build();
        } catch (IllegalArgumentException e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @DeleteMapping("/api/worker/delete/{id}")
    public MessageResponse deleteWorker(@PathVariable String id) {
        try {
            Integer responseCode =  workerBean.deleteWorker(UUID.fromString(id));
            if (responseCode == 404) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }

            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("ok")
                    .build();
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
        Worker worker = workerBean.updateWorkerField(id, updateDetailsRequest.getField(), updateDetailsRequest.getValue());

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
                .worker(worker)
                .message("ok")
                .build();
    }

    @PostMapping("/api/worker/create")
    public MessageResponse createWorker(@RequestBody Worker worker) {
        Integer responseCode =  workerBean.createWorker(worker);

        if (responseCode == 404) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(404)
                    .message("Worker not found")
                    .build();
        }

        return WorkerResponse.builder()
                .date(new Date())
                .code(200)
                .worker(worker)
                .message("ok")
                .build();
    }

    @GetMapping("/api/worker/get")
    public MessageResponse getWorkers() {
        List<Worker> workers = workerBean.getAllWorkers(null, null, 0, 0);
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
                .message("Ok, Workers - " + workers)
                .build();
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