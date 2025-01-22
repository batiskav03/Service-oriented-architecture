package com.soa.workerservice.ejb;

import com.soa.workerservice.ejb.models.Worker;
import com.soa.workerservice.ejb.models.responses.MessageResponse;
import com.soa.workerservice.ejb.models.responses.WorkerResponse;
import com.soa.workerservice.ejb.repository.WorkerRepository;
import com.soa.workerservice.ejb.repository.impl.WorkerCustomRepositoryImpl;
import com.soa.workerservice.ejb.models.SearchCriteria;
//import com.soa.workerservice.ejb.models.WorkerSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.inject.Inject;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Stateless
@Remote(WorkerRemote.class)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class WorkerBean implements WorkerRemote {
    
    @Inject
    private WorkerRepository workerRepository;
    
    @Inject
    private WorkerCustomRepositoryImpl workerCustomRepository;
    
    @Override
    public MessageResponse getWorker(UUID id) {
        try {
            Worker worker = workerRepository.getWorkerById(id);
            if (worker == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }
            return WorkerResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok")
                    .worker(worker)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @Override
    public MessageResponse deleteWorker(UUID id) {
        try {
            Worker worker = workerRepository.getWorkerById(id);
            if (worker == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(204)
                        .message("Resource not found")
                        .build();
            }
            UUID workerId = worker.getId();
            workerRepository.deleteWorkerById(id);
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, fired worker id is: " + workerId)
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @Override
    public MessageResponse updateWorkerField(UUID id, String field, Object value) {
        try {
            Worker worker = workerRepository.getWorkerById(id);
            if (worker == null) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(404)
                        .message("Worker not found")
                        .build();
            }

            Field currentField = worker.getClass().getDeclaredField(field);
            currentField.setAccessible(true);
            Object currentValue = currentField.get(worker);

            if (currentValue != null && currentValue.equals(value)) {
                return MessageResponse.builder()
                        .date(new Date())
                        .code(304)
                        .message("Business no modified don't disturb")
                        .build();
            }

            workerCustomRepository.updateWorkerFieldById(id, field, value);
            
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, UUID of updated worker is " + id.toString())
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @Override
    public MessageResponse createWorker(Worker worker) {
        try {
            UUID uuid = workerCustomRepository.createWorker(worker);
            return MessageResponse.builder()
                    .date(new Date())
                    .code(200)
                    .message("Ok, UUID of created worker is " + uuid.toString())
                    .build();
        } catch (Exception e) {
            return MessageResponse.builder()
                    .date(new Date())
                    .code(400)
                    .message("Invalid parameters supplied")
                    .build();
        }
    }

    @Override
    public Page<Worker> getAllWorkers(String sorting, SearchCriteria filter, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sorting));
        //Specification<Worker> spec = new WorkerSpecification(filter);
        //workerRepository.findAll(spec, pageable);
        return null;

    }

    @Override
    public List<Worker> selectUniqWorkerPositions() {
        try {
            Iterable<Worker> workers = workerRepository.findAll();
            return StreamSupport.stream(workers.spliterator(), false)
                    .collect(Collectors.toMap(
                            Worker::getPosition,
                            worker -> worker,
                            (existing, replacement) -> existing
                    ))
                    .values()
                    .stream()
                    .toList();
        } catch (Exception e) {
            return null;
        }
    }
} 