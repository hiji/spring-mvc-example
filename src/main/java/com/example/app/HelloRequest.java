package com.example.app;

public class HelloRequest {

    private String name;

    private int age;

    public HelloRequest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public HelloRequest() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
