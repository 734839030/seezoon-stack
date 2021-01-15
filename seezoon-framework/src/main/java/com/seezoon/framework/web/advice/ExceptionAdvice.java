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
