package com.example.app.xml;

public class HogeProcessor implements Processor {
    @Override
    public Object execute(Environment environment, Object obj) {
        System.out.println("HogeProcessor");
        return obj;
    }
}
