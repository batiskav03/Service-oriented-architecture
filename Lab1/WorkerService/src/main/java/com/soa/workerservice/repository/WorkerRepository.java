package com.soa.workerservice.repository;

import com.soa.workerservice.model.Worker;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WorkerRepository extends CrudRepository<Worker, UUID> {

    Worker getWorkerById(UUID id);


    void deleteWorkerById(UUID id);

    //Worker createWorker(UUID id, String name,)

    //TODO: Add all fields
//    @Query("SELECT * FROM users WHERE name = :#{#worker.name} AND salary = :#{#worker.salary} AND status = :#{worker.status} ")
//    Worker getWorkerByUniqFields(@Param("worker") Worker worker);





}
