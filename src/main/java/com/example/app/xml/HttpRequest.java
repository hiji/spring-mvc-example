package com.example.app.xml;

public class HttpRequest implements Processor {

    private final String path;

    public HttpRequest(String path) {
        this.path = path;
    }

    @Override
    public Object execute(Environment environment, Object obj) {
        System.out.println("environment(var1) -> " + environment.getVariable("var1"));
        System.out.println("HttpRequest -> " + path);
        return obj;
    }
}
