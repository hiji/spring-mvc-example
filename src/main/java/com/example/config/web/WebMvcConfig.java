package com.example.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableWebMvc
//@ComponentScan("com.example")
@ImportResource("WEB-INF/mvc-config-test.xml")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
}
