package com.study.spring.mvc;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}
