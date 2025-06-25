package com.atguigu.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.common.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
public class MyBlockExceptionHanlder implements BlockExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String resourceName, BlockException e) throws Exception {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(429);
        PrintWriter pw = httpServletResponse.getWriter();

        R error = R.error(500, resourceName+"被Sentinel限流了， 原因：" + e.getClass());
        String json = objectMapper.writeValueAsString(error);
        pw.write(json);
        pw.flush();
        pw.close();
    }
}
