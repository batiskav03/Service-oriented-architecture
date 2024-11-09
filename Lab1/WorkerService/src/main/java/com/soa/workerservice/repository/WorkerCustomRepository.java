package com.soa.workerservice.repository;

import com.soa.workerservice.model.Coordinates;
import com.soa.workerservice.model.HairColor;
import com.soa.workerservice.model.Nationality;
import com.soa.workerservice.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.UUID;

public interface WorkerCustomRepository extends CrudRepository<Worker, UUID> {
    <T> void updateWorkerFieldById(UUID id, String field, T value);

    UUID createWorker(Worker worker);

    UUID createCoordinatesForNewWorker(Integer x,  Integer y);

    UUID createPersonForNewWorker(Date birthday, UUID passportID, HairColor hairColor, Nationality nationality);

}
