package com.soa.workerservice.repository;

import com.soa.workerservice.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WorkerCustomRepository extends CrudRepository<Worker, UUID> {
    <T> void updateWorkerFieldById(UUID id, String field, T value);
}
