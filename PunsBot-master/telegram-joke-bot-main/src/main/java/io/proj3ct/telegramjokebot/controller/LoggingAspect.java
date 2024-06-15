package io.proj3ct.telegramjokebot.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Точка соединения для всех контроллерных методов
    @Pointcut("within(io.proj3ct.telegramjokebot.controller.*)")
    public void controllerMethods() {}

    // Логирование перед и после вызова контроллерных методов
    @Around("controllerMethods()")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("Вход в контроллерный метод: {}.{}() с аргументами: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        logger.info("Выход из контроллерного метода: {}.{}()  затрачено времени: {} ms. Результат: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                endTime - startTime,
                result);

        return result;
    }


}