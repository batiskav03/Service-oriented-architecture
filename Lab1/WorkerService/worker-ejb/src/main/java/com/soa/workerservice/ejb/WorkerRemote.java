package com.soa.workerservice.ejb;

import com.soa.workerservice.ejb.models.SearchCriteria;
import com.soa.workerservice.ejb.models.Worker;
import com.soa.workerservice.ejb.models.responses.MessageResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.ejb.Remote;

@Remote
public interface WorkerRemote {
    MessageResponse getWorker(UUID id);
    MessageResponse deleteWorker(UUID id);
    MessageResponse updateWorkerField(UUID id, String field, Object value);
    MessageResponse createWorker(Worker worker);
    List<Worker> getAllWorkers(String sorting, SearchCriteria filter, int page, int pageSize);
    List<Worker> selectUniqWorkerPositions();
} 