package com.soa.hrservice.controller;

import com.soa.hrservice.service.WorkerRequestService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.Date;
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
        String out = workerRequestService.fireWorker(uuid);
        return Response
                .ok()
                .entity(out)
                .build();
    }

    @POST
    @Path("hire/{person-id}/{position}/{start-date}")
    public Response hirePerson(@PathParam("person-id") String personId,
                               @PathParam("position") String position,
                               @PathParam("start-date") String startDate) {
        try {
            UUID.fromString(personId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Response.status(400)
                    .entity("Invalid parameters supplied,\n" +
                            "person-id is uncorrected, change id and retry x")
                    .build();
        }
        String out = workerRequestService.hirePerson(personId, position, startDate);
        return Response.ok()
                .entity(out)
                .build();
    }

}
