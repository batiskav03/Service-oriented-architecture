package com.soa.workerservice.ejb.repository.impl;

import com.soa.workerservice.ejb.models.Worker;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class WorkerCustomRepositoryImpl {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public <T> void updateWorkerFieldById(UUID id, String fieldName, T value) {
        String query = String.format("UPDATE Worker w SET w.%s = :value WHERE w.id = :id", fieldName);
        entityManager.createQuery(query)
                .setParameter("value", value)
                .setParameter("id", id)
                .executeUpdate();
    }
    
    public UUID createWorker(Worker worker) {
        entityManager.persist(worker);
        return worker.getId();
    }
} 