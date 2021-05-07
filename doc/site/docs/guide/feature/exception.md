# 异常处理

异常会有异常码和异常原因，需要更细分可以自行扩展。

## ExceptionAdvice
框架使用Spring ControllerAdvice来统一处理异常，

```java
package com.seezoon.framework.web.advice;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.seezoon.framework.api.DefaultCodeMsgBundle;
import com.seezoon.framework.api.Result;
import com.seezoon.framework.exception.BusinessException;
import com.seezoon.framework.exception.ServerRuntimeException;

/**
 * 异常advice
 *
 * @author hdf
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 具体Excetion 场景参见其java doc
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class,
        ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class})
    public Result parameterInvalidException(Exception e) {
        return Result.error(DefaultCodeMsgBundle.PARAM_INVALID, e.getMessage());

    }

    @ExceptionHandler({BindException.class})
    public Result bindException(BindException e) {
        return Result.error(DefaultCodeMsgBundle.PARAM_BIND_ERROR, e.getMessage());
    }

    /**
     * 使用{@link org.springframework.util.Assert} 来验证参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public Result illegalArgumentException(IllegalArgumentException e) {
        return Result.error(DefaultCodeMsgBundle.PARAM_INVALID, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result businessException(BusinessException e) {
        return Result.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(ServerRuntimeException.class)
    public Result serverRuntimeException(ServerRuntimeException e) {
        return Result.error(DefaultCodeMsgBundle.FAIL, e.getMessage());
    }

    /**
     * 可以细化异常，spring 从小异常抓，抓到就不往后走
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) throws Exception {
        logger.error("global exception ", e);
        return Result.error(DefaultCodeMsgBundle.UNKNOWN, e.getMessage());
    }
}

```

## 异常说明

> 异常都会转换为标准响应格式。

- BindException

  当Spring 自动参数转换失败时候会触发这个异常，如果非数值字符串准数字，时间转换等出现异常。

- ConstraintViolationException

  对象上数据，JSR-303 验证失败时候。

- MethodArgumentNotValidException

  方法参数使用`@Valid`验证不过。

- IllegalArgumentException

  使用org.springframework.util.Assert 来验证参数不通过时候。

- MethodArgumentTypeMismatchException 和MissingServletRequestParameterException

  Spring MVC controller 接收参数时候失败。

- BusinessException 

  为自定义业务逻辑异常，适用于层级较深时候或者逻辑错误，**但不要频繁使用，正常情况异常是极端情况出现。**

- ServerRuntimeException

  为自定义运行时异常。

