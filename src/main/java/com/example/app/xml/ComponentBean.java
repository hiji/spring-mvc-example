package com.example.app.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ComponentBean implements Processor {

    private final Object bean;

    public ComponentBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object execute(Environment environment, Object obj) {
        Class<?> messageType = obj.getClass();
        Method[] methods = bean.getClass().getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            Class<?> parameterType = method.getParameterTypes()[0];
            if (parameterType.isAssignableFrom(obj.getClass())) {
                targetMethod = method;
                break;
            }
        }
        try {
            Object result = targetMethod.invoke(bean, obj);
            return result;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
