package com.soa.workerservice.repository;

import com.soa.workerservice.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WorkerRepository extends CrudRepository<Worker, UUID> {

    Worker getWorkerById(UUID id);
}
