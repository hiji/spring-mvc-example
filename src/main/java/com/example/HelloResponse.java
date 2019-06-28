package com.example;

public class HelloResponse {

    private String name;

    private int age;

    public HelloResponse(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Jacksonで返却時にGetter必要
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
