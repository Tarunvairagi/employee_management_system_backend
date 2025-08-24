package com.emp.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Logs all method entry in controller and service packages
    @Before("execution(* com.emp.ems.controller..*(..)) || execution(* com.emp.ems.service..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Entering Method: {}() with arguments = {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    // Logs successful return
    @AfterReturning(pointcut = "execution(* com.emp.ems..*(..))", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info("Method {}() returned: {}", joinPoint.getSignature().getName(), result);
    }

    // Logs any exception thrown
    @AfterThrowing(pointcut = "execution(* com.emp.ems..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error("Exception in {}(): {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}

