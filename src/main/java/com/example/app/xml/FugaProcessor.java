package com.example.app.xml;

import java.util.List;
import java.util.Objects;

public class FugaProcessor implements Processor {

    public FugaProcessor(List<FugaOption> option) {
        Objects.requireNonNull(option);
        if (option.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Object execute(Environment environment, Object obj) {
        return obj;
    }
}
