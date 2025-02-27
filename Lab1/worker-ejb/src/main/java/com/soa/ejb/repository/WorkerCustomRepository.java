package com.soa.ejb.repository;

import com.soa.ejb.models.Coordinates;
import com.soa.ejb.models.HairColor;
import com.soa.ejb.models.Nationality;
import com.soa.ejb.models.Worker;


import javax.ejb.Local;
import java.util.Date;
import java.util.UUID;

@Local
public interface WorkerCustomRepository {
    <T> void updateWorkerFieldById(UUID id, String field, T value);

    UUID createWorker(Worker worker);

    UUID createCoordinatesForNewWorker(Integer x, Integer y);

    UUID createPersonForNewWorker(Date birthday, UUID passportID, HairColor hairColor, Nationality nationality);
} 