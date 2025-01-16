package com.soa.worker.rest;

import com.soa.worker.model.Worker;
import com.soa.worker.model.MessageResponse;
import com.soa.worker.service.WorkerServiceRemote;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.UUID;

@Path("/api/worker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkerResource {

    @EJB(mappedName = "ejb/WorkerService")
    private WorkerServiceRemote workerService;

    @GET
    @Path("/get/{id}")
    public Response getWorker(@PathParam("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Worker worker = workerService.getWorker(uuid);
            
            if (worker == null) {
                return Response.status(404)
                    .entity(new MessageResponse(404, new Date(), "Worker not found"))
                    .build();
            }
            
            return Response.ok(new MessageResponse(200, new Date(), "Ok", worker)).build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(400)
                .entity(new MessageResponse(400, new Date(), "Invalid UUID format"))
                .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteWorker(@PathParam("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            UUID deletedId = workerService.deleteWorker(uuid);
            
            if (deletedId == null) {
                return Response.status(204)
                    .entity(new MessageResponse(204, new Date(), "Resource not found"))
                    .build();
            }
            
            return Response.ok(new MessageResponse(200, new Date(), 
                "Ok, fired worker id is: " + deletedId)).build();
                
        } catch (IllegalArgumentException e) {
            return Response.status(400)
                .entity(new MessageResponse(400, new Date(), "Invalid UUID format"))
                .build();
        }
    }

    @POST
    @Path("/create")
    public Response createWorker(Worker worker) {
        try {
            UUID uuid = workerService.createWorker(worker);
            return Response.ok(new MessageResponse(200, new Date(), 
                "Ok, UUID of created worker is " + uuid.toString())).build();
        } catch (Exception e) {
            return Response.status(400)
                .entity(new MessageResponse(400, new Date(), "Invalid worker data"))
                .build();
        }
    }
} 