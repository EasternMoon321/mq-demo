package com.eastern.mqdemo.common;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class LogAspect {
    @Pointcut("execution(* com.eastern.mqdemo.controller.*.*(..))")
    public void inControllerLayer(){}

    @Around(value = "inControllerLayer()")
    public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String path = proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName();
        log.info("{} 入参打印 {}", path, Arrays.toString(proceedingJoinPoint.getArgs()));
        Object object = proceedingJoinPoint.proceed();
        log.info("{} 耗时{}ms 出参打印 {}", path, System.currentTimeMillis() - startTime, object);
        return object;
    }
}
