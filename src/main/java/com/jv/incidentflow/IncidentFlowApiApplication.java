package com.jv.incidentflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IncidentFlowApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(IncidentFlowApiApplication.class, args);
  }
}
