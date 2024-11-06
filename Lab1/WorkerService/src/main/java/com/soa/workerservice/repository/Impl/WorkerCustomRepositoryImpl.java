package com.soa.workerservice.repository.Impl;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.repository.WorkerCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkerCustomRepositoryImpl implements WorkerCustomRepository{
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
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

    private boolean isValidField(String field) {
        return true;
    }

    @Override
    public <S extends Worker> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Worker> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Worker> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public Iterable<Worker> findAll() {
        return null;
    }

    @Override
    public Iterable<Worker> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(Worker entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Worker> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
