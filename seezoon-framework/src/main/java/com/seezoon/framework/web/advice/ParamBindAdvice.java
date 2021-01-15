package com.seezoon.framework.web.advice;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.seezoon.framework.exception.ServerRuntimeException;

/**
 * @author hdf
 */
@RestControllerAdvice
public class ParamBindAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ParamBindAdvice.class);

    private static String[] parsePatterns =
        {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "HH:mm"};

    /**
     * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString().trim() : "";
            }

            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
        });
        // Date 类型转换 如果有需求可以打开编码方便但浪费较多，建议使用@DateTimeFormat接收时间类参数
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    setValue(DateUtils.parseDate(text, parsePatterns));
                } catch (ParseException e) {
                    throw new ServerRuntimeException(e);
                }
            }
        });
    }

    /**
     * json 反序列化trim
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.deserializerByType(String.class,
                    new StdScalarDeserializer<String>(String.class) {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                            throws IOException {
                            return StringUtils.trim(jsonParser.getValueAsString());
                        }
                    });
            }
        };
    }
}
