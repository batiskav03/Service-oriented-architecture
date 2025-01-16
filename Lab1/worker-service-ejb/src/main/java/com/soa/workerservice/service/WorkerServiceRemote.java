package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import javax.ejb.Remote;
import java.util.UUID;

@Remote
public interface WorkerServiceRemote {
    // Здесь будут объявлены методы из вашего текущего WorkerService
    Worker getWorker(UUID id);
    UUID createWorker(Worker worker);
    UUID deleteWorker(UUID id);
} 