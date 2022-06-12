package com.ssg.techrookie.backend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.ssg.techrookie.backend.web.controller.*ApiController.*(..))")
    public Object controllerLogging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try {
            log.debug("Request : {} {} - start", request.getMethod(), request.getRequestURI());
            return pjp.proceed(pjp.getArgs());
        } finally {
            log.debug("Request : {} {} - end", request.getMethod(), request.getRequestURI());
        }
    }

}
