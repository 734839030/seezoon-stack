package com.seezoon.framework.web.advice;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 完成RequestBody 参数打印
 *
 * @author hdf
 */
@Slf4j
@ControllerAdvice
public class JsonRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        if (log.isDebugEnabled() && (converterType.isAssignableFrom(MappingJackson2HttpMessageConverter.class))) {
            log.debug("Request Body=>{}", objectMapper.writeValueAsString(body));
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
