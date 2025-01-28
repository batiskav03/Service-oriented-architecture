package com.soa.workerservice.ejb.repository;

import com.soa.workerservice.ejb.models.Worker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface WorkerRepository {
    Worker getWorkerById(UUID id);
    void deleteWorkerById(UUID id);
    List<Worker> findAll();
    Worker save(Worker worker);
} 