package com.soa.hrservice.service;

import jakarta.ejb.Singleton;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@Singleton
public class WorkerRequestService {

    public String fireWorker(UUID uuid) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder
                    .delete("http://localhost:8080/worker/delete/" + uuid.toString())
                    .build();
            String out = httpclient.execute(httpGet, response -> {
                System.out.println(response.getEntity().getContent().toString());
                final HttpEntity entity1 = response.getEntity();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }
                EntityUtils.consume(entity1);
                return output;
            });

            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
