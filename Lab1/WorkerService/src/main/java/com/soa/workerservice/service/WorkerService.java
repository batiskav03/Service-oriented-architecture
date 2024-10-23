package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkerService {

    WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker getWorker(UUID id) {
        Worker worker = workerRepository.getWorkerById(id);
        System.out.println(worker);
        return null;
    }
}
