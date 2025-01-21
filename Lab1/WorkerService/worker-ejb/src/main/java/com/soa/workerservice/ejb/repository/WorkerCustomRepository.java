package com.soa.workerservice.ejb.repository;

import com.soa.workerservice.ejb.models.Coordinates;
import com.soa.workerservice.ejb.models.HairColor;
import com.soa.workerservice.ejb.models.Nationality;
import com.soa.workerservice.ejb.models.Worker;

import jakarta.ejb.Local;
import java.util.Date;
import java.util.UUID;

@Local
public interface WorkerCustomRepository {
    <T> void updateWorkerFieldById(UUID id, String field, T value);

    UUID createWorker(Worker worker);

    UUID createCoordinatesForNewWorker(Integer x, Integer y);

    UUID createPersonForNewWorker(Date birthday, UUID passportID, HairColor hairColor, Nationality nationality);
} 