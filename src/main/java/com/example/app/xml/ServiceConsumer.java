package com.example.app.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceConsumer {

    private final String name;

    private final Environment environment;

    private final List<Processor> processors = new ArrayList<>();

    public ServiceConsumer(String name, Environment environment) {
        this.name = name;
        this.environment = environment;
    }

    public Object execute(Object message) {
        System.out.println(ServiceConsumer.class.getSimpleName() + "#execute() -> " + name);
        Object input = message;
        Object output = null;
        for (Processor processor : processors) {
            if (processor == null) continue;
            output = processor.execute(environment, input);
            input = output;
        }
        return output;
    }

    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

}
