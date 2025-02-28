package com.soa.hrservice.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkerRequestService {
    private static final String DELETE_URL = "http://worker-service-web:1516/api/worker/delete/";
    private static final String HIRE_URL = "http://worker-service-web:1516/api/worker/create";

    @Autowired
    private RestTemplate restTemplate;

    public String fireWorker(UUID uuid) {
        ResponseEntity<String> response = restTemplate.exchange(DELETE_URL + uuid.toString(), HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }

    public String hirePerson(String personId, String position, String startDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String input = "{ \"startDate\": \"" + startDate + "\", \"position\": \"" + position + "\", \"personId\": \"" + personId + "\"}";
        System.out.println(input);
        HttpEntity rq = new HttpEntity<>(input, headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(HIRE_URL, rq, String.class);
        return responseEntityStr.getBody();
    }
}