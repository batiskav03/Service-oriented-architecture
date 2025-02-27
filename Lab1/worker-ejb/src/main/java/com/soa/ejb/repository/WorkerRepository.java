package com.soa.ejb.repository;

import com.soa.ejb.models.Worker;

import javax.ejb.Local;
import java.util.List;
import java.util.UUID;

@Local
public interface WorkerRepository {
    Worker getWorkerById(UUID id);
    void deleteWorkerById(UUID id);
    List<Worker> findAll();
    void save(Worker worker);
} 