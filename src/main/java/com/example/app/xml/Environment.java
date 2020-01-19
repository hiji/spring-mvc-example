package com.example.app.xml;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    Map<String, String> variables = new HashMap<>();

    void putVariable(String name, String value) {
        variables.put(name, value);
    }

    public String getVariable(String name) {
        return variables.get(name);
    }
}
