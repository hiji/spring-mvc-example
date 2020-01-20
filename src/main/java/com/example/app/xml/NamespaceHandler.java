package com.example.app.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("consumer", new ServiceBeanDefinitionParser());
        registerBeanDefinitionParser("http-request", new HttpRequestBeanDefinitionParser());
//        registerBeanDefinitionParser("fuga", new FugaBeanDefinitionParser());
//        registerBeanDefinitionParser("component", new HttpRequestBeanDefinitionParser());
    }
}
