package com.soa.hrservice.controller;

import com.soa.hrservice.service.WorkerRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/hr")
public class HRController {

    @Autowired
    private WorkerRequestService workerRequestService;

    @DeleteMapping("/fire/{id}")
    public ResponseEntity<String> fireWorker(@PathVariable("id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid parameters supplied,\n" +
                            "id is uncorrected, change id and retry x");
        }
        String out = workerRequestService.fireWorker(uuid);
        return ResponseEntity.ok(out);
    }

    @PostMapping("/hire/{person-id}/{position}/{start-date}")
    public ResponseEntity<String> hirePerson(@PathVariable("person-id") String personId,
                                             @PathVariable("position") String position,
                                             @PathVariable("start-date") String startDate) {
        try {
            UUID.fromString(personId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid parameters supplied,\n" +
                            "person-id is uncorrected, change id and retry x");
        }
        String out = workerRequestService.hirePerson(personId, position, startDate);
        return ResponseEntity.ok(out);
    }

}