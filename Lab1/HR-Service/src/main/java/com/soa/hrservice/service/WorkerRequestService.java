package com.soa.hrservice.service;

import jakarta.ejb.Singleton;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@Singleton
public class WorkerRequestService {

    public final String DELETE_URL = "http://localhost:8080/api/worker/delete/";
    public final String HIRE_URL = "http://localhost:8080/api/worker/create";

    public String fireWorker(UUID uuid) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder
                    .delete( DELETE_URL + uuid.toString())
                    .build();
            return getMessageFromResponse(httpclient, httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String hirePerson(String personId, String position, String startDate) {
        StringEntity requestEntity = new StringEntity(
          "{ " +
                  " \"startDate\": \"" + startDate + "\"," +
                  "\"position\": \"" + position + "\"," +
                  "\"personId\": \"" + personId + "\"" +
                  "}",
                ContentType.APPLICATION_JSON);
        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ClassicHttpRequest httpPost = new HttpPost(HIRE_URL);
            httpPost.setEntity(requestEntity);
            return getMessageFromResponse(httpClient, httpPost);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMessageFromResponse(CloseableHttpClient httpClient, ClassicHttpRequest httpRequest) throws IOException {
        return httpClient.execute(httpRequest, response -> {
            final HttpEntity entity = response.getEntity();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            EntityUtils.consume(entity);
            return output;
        });
    }

}
