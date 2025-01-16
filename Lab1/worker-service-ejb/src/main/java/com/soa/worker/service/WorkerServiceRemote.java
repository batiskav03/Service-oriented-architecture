package com.yourcompany.worker.service;

import com.yourcompany.worker.model.Worker;
import javax.ejb.Remote;
import java.util.List;
import java.util.UUID;

@Remote
public interface WorkerServiceRemote {
    Worker getWorker(UUID id);
    UUID createWorker(Worker worker);
    UUID deleteWorker(UUID id);
    <T> void updateWorkerField(UUID id, String field, T value);
    List<Worker> selectUniqWorkerPositions();
} 