package com.soa.ejb.repository.impl;

import com.soa.ejb.models.*;
import com.soa.ejb.repository.WorkerCustomRepository;
import com.soa.ejb.repository.DatabaseManager;

import jakarta.persistence.PersistenceContext;
import org.jboss.ejb3.annotation.Pool;

import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@Stateless
@Pool("products-pool")
public class WorkerCustomRepositoryImpl implements WorkerCustomRepository {
    @PersistenceContext(unitName = "WorkerPU")

    private DatabaseManager dbManager = new DatabaseManager();

    @Override
    public <T> void updateWorkerFieldById(UUID id, String field, T value) {
        String sql = "UPDATE workers SET " + field + " = ? WHERE id = ?";
        try {
            dbManager.executeUpdate(sql, value, id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update worker field", e);
        }
    }

    @Override
    public UUID createCoordinatesForNewWorker(Integer x, Integer y) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO coordinates (id, x, y) VALUES (?, ?, ?)";
        try {
            dbManager.executeUpdate(sql, id, x, y);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create coordinates", e);
        }
        return id;
    }

    @Override
    public UUID createPersonForNewWorker(Date birthday, UUID passportID,
                                       HairColor hairColor, Nationality nationality) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO persons (id, birthday, passport_id, hair_color, nationality) VALUES (?, ?, ?, ?, ?)";
        try {
            dbManager.executeUpdate(sql, id, birthday, passportID, hairColor.toString(), nationality.toString());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create person", e);
        }
        return id;
    }

    @Override
    public UUID createWorker(Worker worker) {
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

        UUID workerId = UUID.randomUUID();
        String sql = "INSERT INTO workers (id, name, position, salary, coordinates_id, person_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            dbManager.executeUpdate(sql, workerId, worker.getName(), worker.getPosition(), worker.getSalary(), coordinatesId, personId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create worker", e);
        }
        return workerId;
    }


} 