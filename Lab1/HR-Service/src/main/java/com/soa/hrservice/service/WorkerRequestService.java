package com.soa.hrservice.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkerRequestService {
    private static final String DELETE_URL = "http://localhost:8080/api/worker/delete/";
    private static final String HIRE_URL = "http://localhost:8080/api/worker/create";

    @Autowired
    private RestTemplate restTemplate;

    public String fireWorker(UUID uuid) {
        ResponseEntity<String> response = restTemplate.exchange(DELETE_URL + uuid.toString(), HttpMethod.DELETE, null, String.class);
        return response.getBody();
    }

    public String hirePerson(String personId, String position, String startDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String input = "{ 'startDate': '" + startDate + "', 'position': '" + position + "', 'personId': '" + personId + "'}";
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(HIRE_URL, input, String.class);
        return responseEntityStr.getBody();
    }
}