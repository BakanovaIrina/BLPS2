package com.blps.config;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamundaConfig {

    @Bean
    public void deployProcessDefinition(RepositoryService repositoryService, RuntimeService runtimeService) {
        repositoryService.createDeployment()
                .addClasspathResource("processes/process.bpmn")
                .deploy();
    }

    public void startProcess(DelegateExecution execution) {
        runtimeService.startProcessInstanceByKey("bookingProcess");
    }
}

