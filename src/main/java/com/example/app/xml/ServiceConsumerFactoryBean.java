package com.example.app.xml;

import org.springframework.beans.factory.FactoryBean;

import java.util.List;

public class ServiceConsumerFactoryBean implements FactoryBean<ServiceConsumer> {

    private ServiceConsumer service;
    private List<Processor> processors;

    public void setService(ServiceConsumer service) {
        this.service = service;
    }

    public void setProcessors(List<Processor> processors) {
        this.processors = processors;
    }

    @Override
    public ServiceConsumer getObject() throws Exception {
        if (processors != null && !processors.isEmpty()) {
            for (Processor processor : processors) {
                service.addProcessor(processor);
            }
        }
        return service;
    }

    @Override
    public Class<?> getObjectType() {
        return ServiceConsumer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
