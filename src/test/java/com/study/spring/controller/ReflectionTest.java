package com.study.spring.controller;

import com.study.spring.annotation.Controller;
import com.study.spring.annotation.Service;
import com.study.spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ReflectionTest {

    @DisplayName("@Contorller, @Service 어노테이션이 붙은 클래스 목록을 가져온다")
    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));
        log.debug("beans: " + beans);

    }


    private Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("com.study.spring");

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));

        return beans;

//        Reflections reflections = new Reflections("com.study.spring");
//
//        Set<Class<?>> beans = new HashSet<>();
//
//        // 해당 클래스 패키지 내에  @Controller annotation 있는 클래스를 찾는것
//        beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));
//        beans.addAll(reflections.getTypesAnnotatedWith(Service.class));
//        return beans;
    }

    @DisplayName("클래스에 선언된 필드 이름 가져온다")
    @Test
    void showClass() {
        Class<User> clazz = User.class;
        log.debug(clazz.getName());

        log.debug("user all declared fields [{}]",
                Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));

        log.debug("user all declared constructors [{}]",
                Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));

        log.debug("user all declared methods [{}]",
                Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @DisplayName("메모리 Heap 영역에 로드되어있는 클래스 타입 객체를은 가져온 3가지 방법은 모두 일치한다")
    @Test
    void load() throws ClassNotFoundException {
        // 1
        Class<User> clazz = User.class;

        // 2
        User user = new User("woo", "황상욱");
        Class<? extends User> clazz2 = user.getClass();

        // 3
        Class<?> clazz3 = Class.forName("com.study.spring.model.User");

        log.debug("class: [{}]", clazz);
        log.debug("class2: [{}]", clazz2);
        log.debug("class3: [{}]", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz).isTrue();
    }
}
