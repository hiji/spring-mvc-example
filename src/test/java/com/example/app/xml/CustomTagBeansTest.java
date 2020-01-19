package com.example.app.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:com/example/app/beans.xml")
public class CustomTagBeansTest {

    @Autowired
    List<ServiceConsumer> target;

    @Test
    void executeServiceConsumerBean() {
        target.forEach(v -> v.execute("first"));
    }
}
