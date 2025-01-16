package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Stateless(mappedName = "ejb/WorkerService")
@Remote(WorkerServiceRemote.class)
public class WorkerServiceBean implements WorkerServiceRemote {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // Здесь будет реализация методов
    @Override
    public Worker getWorker(UUID id) {
        return null; // TODO: implement
    }

    @Override
    public UUID createWorker(Worker worker) {
        return null; // TODO: implement
    }

    @Override
    public UUID deleteWorker(UUID id) {
        return null; // TODO: implement
    }
} 