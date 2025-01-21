package com.soa.workerservice.ejb.repository;

import com.soa.workerservice.ejb.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, UUID>, JpaSpecificationExecutor<Worker> {
    Worker getWorkerById(UUID id);
    void deleteWorkerById(UUID id);
} 