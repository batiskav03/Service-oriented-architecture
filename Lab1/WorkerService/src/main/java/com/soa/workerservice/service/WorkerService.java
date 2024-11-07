package com.soa.workerservice.service;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.repository.Impl.WorkerCustomRepositoryImpl;
import com.soa.workerservice.repository.WorkerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WorkerCustomRepositoryImpl workerCustomRepository;

    public WorkerService(WorkerRepository workerRepository, WorkerCustomRepositoryImpl workerCustomRepository) {
        this.workerRepository = workerRepository;
        this.workerCustomRepository = workerCustomRepository;
    }

    public Worker getWorker(UUID id) {
        return workerRepository.getWorkerById(id);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
    public UUID deleteWorker(UUID id) {
        Worker worker = getWorker(id);
        UUID uuid = worker.getId();
        workerRepository.deleteWorkerById(id);
        return uuid;
    }

    public <T> UUID updateWorkerField(UUID id, String field, T value){
        Worker worker = getWorker(id);
        UUID uuid = worker.getId();
        workerCustomRepository.updateWorkerFieldById(id, field, value);
        return uuid;
    }

    public UUID createWorker(Worker worker){
        try {
            return workerCustomRepository.createWorker(worker);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Worker> selectUniqWorkerPositions(){
        Iterable<Worker> workers = workerRepository.findAll();
        return StreamSupport.stream(workers.spliterator(), false)
                .collect(Collectors.toMap(
                        Worker::getPosition,
                        worker -> worker,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();

    }
}
