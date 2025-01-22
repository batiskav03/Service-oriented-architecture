package com.soa.workerservice.ejb.repository.impl;

import com.soa.workerservice.ejb.models.Worker;
import com.soa.workerservice.ejb.repository.WorkerRepository;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;


@ApplicationScoped
public class WorkerRepositoryImpl implements WorkerRepository {

    @PersistenceContext(unitName = "WorkerPU")
    private EntityManager entityManager;

    @Override
    public Worker getWorkerById(UUID id) {
        return entityManager.find(Worker.class, id);
    }

    @Override
    public void deleteWorkerById(UUID id) {
        Worker worker = getWorkerById(id);
        if (worker != null) {
            entityManager.remove(worker);
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Worker> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Worker> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Worker> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Worker getOne(UUID uuid) {
        return null;
    }

    @Override
    public Worker getById(UUID uuid) {
        return null;
    }

    @Override
    public Worker getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Worker> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Worker> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Worker> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Worker> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Worker> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Worker> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Worker, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Optional<Worker> findOne(Specification<Worker> spec) {
        return Optional.empty();
    }

    @Override
    public List<Worker> findAll(Specification<Worker> spec) {
        return List.of();
    }

    @Override
    public Page<Worker> findAll(Specification<Worker> spec, Pageable pageable) {
        return null;
    }

    @Override
    public List<Worker> findAll(Specification<Worker> spec, Sort sort) {
        return List.of();
    }

    @Override
    public long count(Specification<Worker> spec) {
        return 0;
    }

    @Override
    public boolean exists(Specification<Worker> spec) {
        return false;
    }

    @Override
    public long delete(Specification<Worker> spec) {
        return 0;
    }

    @Override
    public <S extends Worker, R> R findBy(Specification<Worker> spec, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Worker> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Worker> List<S> saveAll(Iterable<S> entities) {
        return List.of();
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
    public List<Worker> findAll() {
        return List.of();
    }

    @Override
    public List<Worker> findAllById(Iterable<UUID> uuids) {
        return List.of();
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

    @Override
    public List<Worker> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Worker> findAll(Pageable pageable) {
        return null;
    }
}