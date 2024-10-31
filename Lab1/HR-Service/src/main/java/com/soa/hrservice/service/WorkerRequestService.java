package com.soa.hrservice.service;

import jakarta.ejb.Singleton;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.util.UUID;

@Singleton
public class WorkerRequestService {

    public String fireWorker(UUID uuid) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder
                    .delete("http://localhost:8080/worker/delete/" + uuid.toString())
                    .build();
            httpclient.execute(httpGet, response -> {
                System.out.println(response.toString());
                final HttpEntity entity1 = response.getEntity();
                EntityUtils.consume(entity1);
                return entity1.toString();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "123";
    }

}
