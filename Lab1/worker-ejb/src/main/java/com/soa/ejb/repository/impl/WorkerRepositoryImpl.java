package com.soa.ejb.repository.impl;

import com.soa.ejb.models.Worker;
import com.soa.ejb.repository.DatabaseManager;
import com.soa.ejb.repository.WorkerRepository;
import org.jboss.ejb3.annotation.Pool;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stateless
@Pool("products-pool")
public class WorkerRepositoryImpl implements WorkerRepository {

    @Inject
    private DatabaseManager databaseManager;


    @Override
    public Worker getWorkerById(UUID id) {
        try {
            ResultSet rs = databaseManager.executeQuery("SELECT * FROM worker where id = ?", id);
            rs.first();
            return rs.getObject("worker", Worker.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteWorkerById(UUID id) {
        try {
            databaseManager.executeUpdate("DELETE FROM worker where id = ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Worker> findAll() {
        try {
            ResultSet rs = databaseManager.executeQuery("select * from worker");
            List<Worker> workers = new ArrayList<>();
            while (rs.next()) {
                workers.add(rs.getObject("worker", Worker.class));
            }

            return workers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Deprecated
    public void save(Worker worker) {
        try {
            databaseManager.executeUpdate(
                    "INSERT INTO worker (id, name, coordinates_id," +
                            " creation_date, salary, start_date," +
                            " status, position, person_id) values (?, ?, ?, ?, ?, ?/)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
