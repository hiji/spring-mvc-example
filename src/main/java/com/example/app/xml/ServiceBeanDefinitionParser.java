package com.example.app.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final String VARIABLE_ELEMENT = "variable";

    private static final String PROCESSOR_ELEMENT = "processor";

    private static final String HTTP_REQUEST_ELEMENT = "http-request";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        return parseServiceConsumerElement(element, parserContext.getRegistry());
    }

    private AbstractBeanDefinition parseServiceConsumerElement(Element element, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ServiceConsumerFactoryBean.class);
        factory.addPropertyValue("service", parseServiceConsumer(element));

        List<Element> childElements = DomUtils.getChildElementsByTagName(element, PROCESSOR_ELEMENT, HTTP_REQUEST_ELEMENT);
        if (!childElements.isEmpty()) {
            parseChildElements(childElements, factory, registry);
        }

        return factory.getBeanDefinition();
    }

    private BeanDefinition parseServiceConsumer(Element element) {
        BeanDefinitionBuilder service = BeanDefinitionBuilder.rootBeanDefinition(ServiceConsumer.class);
        Environment environment = new Environment();
        List<Element> variableElements = DomUtils.getChildElementsByTagName(element, VARIABLE_ELEMENT);
        for (Element variableElement : variableElements) {
            environment.putVariable(variableElement.getAttribute("name"), variableElement.getAttribute("value"));
        }
        service.addConstructorArgValue(element.getAttribute(NAME_ATTRIBUTE));
        service.addConstructorArgValue(environment);
        return service.getBeanDefinition();
    }

    private void parseChildElements(List<Element> childElements, BeanDefinitionBuilder factory, BeanDefinitionRegistry registry) {
        ManagedList<BeanDefinition> children = new ManagedList<>(childElements.size());
        for (Element element : childElements) {
            children.add(parseChildElement(element, registry));
        }
        factory.addPropertyValue("processors", children);
    }

    private BeanDefinition parseChildElement(Element element, BeanDefinitionRegistry registry) {
        String tagName = element.getLocalName();
        if (tagName.equals(PROCESSOR_ELEMENT)) {
            return parseProcessor(element, registry);
        } else if (tagName.equals(HTTP_REQUEST_ELEMENT)) {
            return parseHttpRequest(element);
        }
        throw new IllegalStateException();
    }

    private BeanDefinition parseProcessor(Element element, BeanDefinitionRegistry registry) {
        String refName = element.getAttribute("ref");
        return registry.getBeanDefinition(refName);
    }

    private BeanDefinition parseHttpRequest(Element element) {
        BeanDefinitionBuilder httpRequest = BeanDefinitionBuilder.rootBeanDefinition(HttpRequest.class);
        httpRequest.addConstructorArgValue(element.getAttribute("path"));
        return httpRequest.getBeanDefinition();
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

}
