package com.yourcompany.worker.service;

import com.yourcompany.worker.model.Worker;
import javax.ejb.Stateless;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Stateless(mappedName = "ejb/WorkerService")
@Remote(WorkerServiceRemote.class)
public class WorkerServiceBean implements WorkerServiceRemote {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Worker getWorker(UUID id) {
        return entityManager.find(Worker.class, id);
    }
    
    @Override
    public UUID createWorker(Worker worker) {
        entityManager.persist(worker);
        return worker.getId();
    }
    
    @Override
    public UUID deleteWorker(UUID id) {
        Worker worker = getWorker(id);
        if (worker != null) {
            entityManager.remove(worker);
            return id;
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> void updateWorkerField(UUID id, String field, T value) {
        if (!isValidField(field)) {
            throw new IllegalArgumentException("Invalid field: " + field);
        }

        String jpql = "UPDATE Worker w SET w." + field + " = :value WHERE w.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("value", value);
        query.setParameter("id", id);

        query.executeUpdate();
    }
    
    @Override
    public List<Worker> selectUniqWorkerPositions() {
        return entityManager.createQuery(
            "SELECT DISTINCT w FROM Worker w GROUP BY w.position", 
            Worker.class
        ).getResultList();
    }
    
    private boolean isValidField(String field) {
        try {
            Worker.class.getDeclaredField(field);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
} 