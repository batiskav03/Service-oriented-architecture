package com.soa.hrservice.controller;

import com.soa.hrservice.service.WorkerRequestService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;


@Path("/hr")
public class HRController {

    @Inject
    private WorkerRequestService workerRequestService;


    @DELETE
    @Path("fire/{id}")
    public Response fireWorker(@PathParam("id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(400)
                    .entity("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry x")
                    .build();
        }
        workerRequestService.fireWorker(uuid);
//        Worker worker = workerService.getWorker(uuid);
//        if (worker == null) {
//            return null;
//        }
        return Response.ok().build();
    }

}
