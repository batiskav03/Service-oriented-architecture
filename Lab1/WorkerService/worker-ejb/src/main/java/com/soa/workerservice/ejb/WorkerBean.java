package com.soa.workerservice.ejb;

import com.soa.workerservice.ejb.models.Position;
import com.soa.workerservice.ejb.models.SearchCriteria;
import com.soa.workerservice.ejb.models.Worker;
import com.soa.workerservice.ejb.models.responses.MessageResponse;
import com.soa.workerservice.ejb.repository.WorkerRepository;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateless
@Remote(WorkerRemote.class)
public class WorkerBean implements WorkerRemote {

    @Inject
    private WorkerRepository workerRepository;

    @Override
    public MessageResponse getWorker(UUID id) {
        Worker worker = workerRepository.getWorkerById(id);

        if (worker == null){ new MessageResponse( 404, new Date(), "Worker not found");}

        return new MessageResponse(200, new Date(), "ok");
    }

    @Override
    public MessageResponse deleteWorker(UUID id) {
        workerRepository.deleteWorkerById(id);
        return new MessageResponse(200, new Date(), "ok");
    }

    @Override
    public MessageResponse updateWorkerField(UUID id, String field, Object value) {
        Worker worker = workerRepository.getWorkerById(id);
        if (worker != null) {
            switch (field) {
                case "name":
                    worker.setName((String) value);
                    break;
                case "position":
                    worker.setPosition((Position) value);
                    break;
                case "salary":
                    worker.setSalary((Long) value);
                    break;
                default:
                    return new MessageResponse(403, new Date(), "Invalid field");
            }
            workerRepository.save(worker);
            return new MessageResponse(200, new Date(), "Worker updated");
        }
        return new MessageResponse(404,  new Date(), "Worker not found");
    }

    @Override
    public MessageResponse createWorker(Worker worker) {
        workerRepository.save(worker);
        return new MessageResponse(200, new Date(), "Worker created");
    }

    @Override
    public List<Worker> getAllWorkers(String sorting, SearchCriteria filter, int page, int pageSize) {
        return workerRepository.findAll();
    }

    @Override
    public List<Worker> selectUniqWorkerPositions() {
        return workerRepository.findAll().stream()
                               .map(Worker::getPosition)
                               .distinct()
                               .map(position -> new Worker())
                               .toList();
    }
} 