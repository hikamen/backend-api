package com.base.backend.core.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.springframework.web.bind.support.WebArgumentResolver.UNRESOLVED;

/**
 * 扩展Spring MVC实现控制器方法参数绑定
 * @author kamen
 */
public class WebArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * @see HandlerMethodArgumentResolver#supportsParameter(MethodParameter parameter)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().equals(WebRequestContext.class)) {
            return true;
        } else if (parameter.getParameterType().equals(Page.class)) {
            return true;
        }
        return false;
    }

    /**
     * @see HandlerMethodArgumentResolver#resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterType().equals(WebRequestContext.class)) {
            return new WebRequestContext(webRequest);
        } else if (parameter.getParameterType().equals(Page.class)) {
            return new WebRequestContext(webRequest).getMyBatisPage();
        }
        return UNRESOLVED;
    }
}
