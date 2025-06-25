package com.atguigu.order.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobaExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public String error(Throwable e)
    {
        return "R.error()";
    }
}
