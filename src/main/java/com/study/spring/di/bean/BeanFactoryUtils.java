package com.study.spring.di.bean;

import com.study.spring.di.annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeanFactoryUtils {
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        // inject 어노테이션이 붙은 생성자만 가지고온다
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(
                clazz, ReflectionUtils.withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }
}
