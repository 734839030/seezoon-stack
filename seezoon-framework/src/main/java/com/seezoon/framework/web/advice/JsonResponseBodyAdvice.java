package com.seezoon.framework.web.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hdf
 */
@ControllerAdvice
@Slf4j
public class JsonResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
        MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        if (log.isDebugEnabled()) {
            log.debug("Respone Body=>{}", objectMapper.writeValueAsString(bodyContainer.getValue()));
        }
    }
}
