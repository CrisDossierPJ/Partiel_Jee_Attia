package com.christian.attia.partiel;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class App extends ResourceConfig{
    public App() {
        packages("com.christian.attia.partiel");
    }
}
