package com.springbootapi.demo.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.springbootapi.demo.Api.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String endpoint = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        RequestLog requestLog = new RequestLog(
                request.getRequestURL().toString(),
                request.getRemoteAddr(),
                endpoint,
                joinPoint.getArgs());
        log.info("----------------doBefore------------------");
        log.info(" Request ---- {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        log.info("----------------doAfter------------------");
    }

    @AfterReturning(returning = "result", pointcut = "log()") // 切面方法返回的对象是result
    public void doAfterReturning(Object result) {
        log.info("Return  -----------: {}",result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String endpoint;
        private Object[] params;

        public RequestLog(String url, String ip, String endpoint, Object[] params) {
            this.url = url;
            this.ip = ip;
            this.endpoint = endpoint;
            this.params = params;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", endpoint='" + endpoint + '\'' +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }
}
