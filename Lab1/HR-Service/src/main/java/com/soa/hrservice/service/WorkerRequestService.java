package com.soa.hrservice.service;

import jakarta.ejb.Singleton;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;

@Singleton
public class WorkerRequestService {

    public void doQuery() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder
                    .get("http://localhost:8080/worker/get/0b4cd6d5-5a8b-4aa7-8f4a-49b74a733a90")
                    .build();
            httpclient.execute(httpGet, response -> {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity1 = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
                return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
