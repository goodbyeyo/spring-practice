package com.study.spring.di.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class BeanFactory {

    private final Set<Class<?>> preInstantiatedClazz;
    private Map<Class<?>, Object> beans = new HashMap<>();

    // class type objects
    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize();
    }

    private void initialize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    // ItemController
    private Object createInstance(Class<?> clazz) {
        // constructor <- 인스턴스 생성 위해
        Constructor<?> constructor = findConstructor(clazz);

        // parameter
        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : constructor.getParameterTypes()) {
            // ItemService
            parameters.add(getParameterByClass(typeClass));
        }

        // create instance
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private Object getParameterByClass(Class<?> typeClass) {
        // ItemService - Bean 에 없음
        Object instanceBean = getBean(typeClass);

        if (Objects.nonNull(instanceBean)) {
            return instanceBean;
        }

        // ItemService - null 이여서 다시 생성
        return createInstance(typeClass);
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        return clazz.getConstructors()[0];

    }

    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType); // class type 에 해당하는 인스턴스 반환
    }
}
