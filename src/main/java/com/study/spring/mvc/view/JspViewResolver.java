package com.study.spring.mvc.view;

public class JspViewResolver implements ViewResolver {

    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    @Override
    public View resolveView(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new RedirectView(viewName);
        }
        return new JspView(viewName + ".jsp");
    }
}
