package com.soa.workerservice.ejb.repository.impl;

import com.soa.workerservice.ejb.models.*;
import com.soa.workerservice.ejb.repository.WorkerCustomRepository;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.UUID;

@Stateless
public class WorkerCustomRepositoryImpl implements WorkerCustomRepository {
    
    @PersistenceContext(unitName = "WorkerPU")
    private EntityManager entityManager;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public <T> void updateWorkerFieldById(UUID id, String field, T value) {
        if (!isValidField(field)) {
            throw new IllegalArgumentException("Invalid field: " + field);
        }

        String jpql = "UPDATE Worker w SET w." + field + " = :value WHERE w.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("value", value);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UUID createCoordinatesForNewWorker(Integer x, Integer y) {
        Coordinates coordinates = new Coordinates();
        coordinates.setId(UUID.randomUUID());
        coordinates.setX(x);
        coordinates.setY(y);
        
        entityManager.persist(coordinates);
        entityManager.flush();
        
        return coordinates.getId();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UUID createPersonForNewWorker(Date birthday, UUID passportID,
                                       HairColor hairColor, Nationality nationality) {
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setBirthday(birthday);
        person.setPassportID(passportID);
        person.setHairColor(hairColor);
        person.setNationality(nationality);
        
        entityManager.persist(person);
        entityManager.flush();
        
        return person.getId();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UUID createWorker(Worker worker) {
        // Создаем связанные сущности
        UUID coordinatesId = createCoordinatesForNewWorker(
            worker.getCoordinates().getX(), 
            worker.getCoordinates().getY()
        );
        
        UUID personId = createPersonForNewWorker(
            worker.getPerson().getBirthday(),
            worker.getPerson().getPassportID(),
            worker.getPerson().getHairColor(),
            worker.getPerson().getNationality()
        );

        // Получаем созданные сущности
        Coordinates coordinates = entityManager.find(Coordinates.class, coordinatesId);
        Person person = entityManager.find(Person.class, personId);

        // Создаем и сохраняем работника
        worker.setId(UUID.randomUUID());
        worker.setCoordinates(coordinates);
        worker.setPerson(person);
        
        entityManager.persist(worker);
        entityManager.flush();
        
        return worker.getId();
    }

    private boolean isValidField(String field) {
        return true;
    }
} 