package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class WorkerService {
    
    @EJB(mappedName = "ejb/WorkerService")
    private WorkerServiceRemote workerServiceRemote;
    
    public Worker getWorker(UUID id) {
        return workerServiceRemote.getWorker(id);
    }
    
    public UUID createWorker(Worker worker) {
        return workerServiceRemote.createWorker(worker);
    }
    
    public UUID deleteWorker(UUID id) {
        return workerServiceRemote.deleteWorker(id);
    }
} 