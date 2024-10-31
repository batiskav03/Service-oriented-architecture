package com.soa.hrservice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
@ApplicationScoped
public class JaxRsApp extends Application {

}