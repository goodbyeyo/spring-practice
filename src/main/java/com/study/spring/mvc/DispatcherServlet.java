package com.study.spring.mvc;

import com.study.spring.annotation.RequestMethod;
import com.study.spring.mvc.view.JspViewResolver;
import com.study.spring.mvc.view.ModelAndView;
import com.study.spring.mvc.view.View;
import com.study.spring.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/")    // 어떤 경로로 요청이 들어와도 실행됨
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapping> handlerMappings;
    private List<ViewResolver> viewResolvers;
    private List<HandlerAdapter> handlerAdapters;

    @Override
    public void init() throws ServletException {
        // 초기화
        RequestMappingHandlerMapping rmfm = new RequestMappingHandlerMapping();
        rmfm.init();

        AnnotationHandlerMapping adm = new AnnotationHandlerMapping("com.study.spring");
        adm.initialize();

        handlerMappings = List.of(rmfm, adm);
        handlerAdapters = List.of(new SingleControllerHandlerAdapter(), new AnnotationHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("[DispatcherServlet] service started");
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        try {
            // 1) 요청 URI -> HandlerMapping 검색
            Object handler = handlerMappings.stream()
                    .filter(hm -> hm.findHandler(new HandlerKey(requestMethod, requestURI)) != null)
                    .map(hm -> hm.findHandler( new HandlerKey(requestMethod, requestURI)))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("no handler mapping found "+requestMethod+","+requestURI));

            // 2) HandlerAdapter 지원하는 adapter 찾아서
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(handler))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

            // 3) HandlerAdapter 내부에서 Controller 실행 -> ModelAndView 리턴
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

            // 4) ViewResolver 에 해당하는 뷰 를 랜더링
            for (ViewResolver viewResolver : viewResolvers) {
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }

        } catch (Exception e) {
            log.error("exception occurred [{}]", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
