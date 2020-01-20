package com.example.app.xml;

import org.springframework.beans.BeanMetadataElement;
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

import java.util.List;

public class ServiceBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final String VARIABLE_ELEMENT = "variable";

    private static final String PROCESSOR_ELEMENT = "processor";

    private static final String HTTP_REQUEST_ELEMENT = "http-request";

    private static final String COMPONENT_ELEMENT = "component";

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        return parseServiceConsumerElement(element, parserContext);
    }

    private AbstractBeanDefinition parseServiceConsumerElement(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ServiceConsumerFactoryBean.class);
        factory.addPropertyValue("service", parseServiceConsumer(element));

        List<Element> childElements = DomUtils.getChildElementsByTagName(element, PROCESSOR_ELEMENT, HTTP_REQUEST_ELEMENT, COMPONENT_ELEMENT);
        if (!childElements.isEmpty()) {
            parseChildElements(childElements, factory, parserContext);
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

    private void parseChildElements(List<Element> childElements, BeanDefinitionBuilder factory, ParserContext parserContext) {
        ManagedList<BeanMetadataElement> children = new ManagedList<>(childElements.size());
        for (Element element : childElements) {
            children.add(parseChildElement(element, parserContext));
        }
        factory.addPropertyValue("processors", children);
    }

    private BeanMetadataElement parseChildElement(Element element, ParserContext parserContext) {
        String tagName = element.getLocalName();
        switch (tagName) {
            case PROCESSOR_ELEMENT:
                return parseProcessor(element, parserContext);
            case HTTP_REQUEST_ELEMENT:
                return parseHttpRequest(element);
            case COMPONENT_ELEMENT:
                return parseComponent(element, parserContext);
        }
        throw new IllegalStateException();
    }

    private BeanDefinition parseComponent(Element element, ParserContext parserContext) {
        Element childElement = DomUtils.getChildElements(element).get(0);
//                BeanDefinitionParserDelegate.BEAN_ELEMENT,
//                BeanDefinitionParserDelegate.REF_ELEMENT);
//        for (Element childElement : childElements) {
//            BeanDefinitionHolder beanDefinitionHolder = parserContext.getDelegate().parseBeanDefinitionElement(childElement);
//        }
//        BeanDefinitionHolder beanDefinitionHolder = parserContext.getDelegate().parseBeanDefinitionElement(childElement);
//        componentBean.addConstructorArgValue(beanDefinitionHolder.getBeanDefinition());
        BeanDefinitionBuilder componentBean = BeanDefinitionBuilder.rootBeanDefinition(ComponentBean.class);
        Object beanElement = parserContext.getDelegate().parsePropertySubElement(childElement, null);
        componentBean.addConstructorArgValue(beanElement);
        return componentBean.getBeanDefinition();
    }

    private BeanMetadataElement parseProcessor(Element element, ParserContext parserContext) {
        String refName = element.getAttribute("ref");
        RuntimeBeanReference ref = new RuntimeBeanReference(refName, false);
        ref.setSource(parserContext.getReaderContext().extractSource(element));
        return ref;
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
