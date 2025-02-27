package com.soa.ejb;


import com.soa.ejb.models.SearchCriteria;
import com.soa.ejb.models.Worker;
import com.soa.ejb.models.responses.MessageResponse;

import javax.ejb.Remote;
import java.util.List;
import java.util.UUID;



@Remote
public interface WorkerRemote {
    Worker getWorker(UUID id);
    Integer deleteWorker(UUID id);
    Worker updateWorkerField(UUID id, String field, Object value);
    Integer createWorker(Worker worker);
    List<Worker> getAllWorkers(String sorting, SearchCriteria filter, int page, int pageSize);
    List<Worker> selectUniqWorkerPositions();
}