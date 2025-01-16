package com.soa.workerservice.controller;

import com.soa.workerservice.model.Worker;
import com.soa.workerservice.service.WorkerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api/worker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkerController {
    
    @Inject
    private WorkerService workerService;
    
    // Здесь будут REST endpoints
} 