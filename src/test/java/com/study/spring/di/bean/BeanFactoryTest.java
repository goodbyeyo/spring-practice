package com.study.spring.di.bean;

import com.study.spring.di.annotation.Controller;
import com.study.spring.di.annotation.Service;
import com.study.spring.di.controller.ItemController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {

    private Reflections reflections;
    private BeanFactory beanFactory;

    @BeforeEach
    void setUp() throws Exception {
        reflections = new Reflections("com.study.spring.di");
        // ItemController, ItemService
        Set<Class<?>> preInstantiatedClazz = getTypesAnnotatedWith(Controller.class, Service.class);
        beanFactory = new BeanFactory(preInstantiatedClazz);

    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> ... annotations) {
        Set<Class<?>> beans = new HashSet<>();
        // 해당 타입의 어노테이션이 붙은 클래스 타입의 객체를 조회해서 Add
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    void diTest() {
        ItemController itemController = beanFactory.getBean(ItemController.class);

        assertThat(itemController).isNotNull();
        assertThat(itemController.getItemService()).isNotNull();
    }

}