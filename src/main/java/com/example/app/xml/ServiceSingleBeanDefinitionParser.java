package com.example.app.xml;

import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ServiceSingleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return ServiceConsumer.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String name = element.getAttribute(NAME_ATTRIBUTE);
        builder
                .addConstructorArgValue(name)
                .addPropertyReference("processor", "HogeProcessor");
//                .addConstructorArgReference("HogeProcessor")
//                .addConstructorArgReference("FugaProcessor");
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected void postProcessComponentDefinition(BeanComponentDefinition componentDefinition) {
        super.postProcessComponentDefinition(componentDefinition);
    }
}
