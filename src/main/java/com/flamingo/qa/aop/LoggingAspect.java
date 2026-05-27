package com.flamingo.qa.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.flamingo.qa.services..*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* com.flamingo.qa.tests..*(..))")
    public void testMethods() {}

    @Around("serviceMethods()")
    public Object logServiceCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.debug("→ [SERVICE] {} | args: {}", method, Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.debug("← [SERVICE] {} | completed in {}ms", method, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable ex) {
            log.error("✗ [SERVICE] {} | failed after {}ms — {}", method, System.currentTimeMillis() - start, ex.getMessage());
            throw ex;
        }
    }

    @Around("testMethods()")
    public Object logTestExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();
        log.info("▶ [TEST] {} | started", method);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.info("✔ [TEST] {} | passed in {}ms", method, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable ex) {
            log.error("✘ [TEST] {} | failed after {}ms — {}", method, System.currentTimeMillis() - start, ex.getMessage());
            throw ex;
        }
    }
}
