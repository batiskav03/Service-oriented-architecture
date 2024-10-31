package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.repository.WorkerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class WorkerService {

    private WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public Worker getWorker(UUID id) {
        return workerRepository.getWorkerById(id);
    }


    public UUID deleteWorker(UUID id) {
        Worker worker = getWorker(id);
        UUID uuid = worker.getId();
        workerRepository.deleteWorkerById(id);
        return uuid;
    }

    public <T> UUID updateWorkerField(UUID id, String field, T value){
        Worker worker = getWorker(id);
        UUID uuid = worker.getId();
        //TODO: add UUID options
        workerRepository.updateWorkerFieldById(id, field, value);
        return uuid;
    }

    public UUID createWorker(Worker worker){
        try {
            workerRepository.createWorker(worker);
            Worker currentWorker = workerRepository.getWorkerByUniqFields(worker);
            return currentWorker.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Worker> selectUniqWorkerPositions(){
        return workerRepository.getUniqWorkersByPosition();
    }
}
