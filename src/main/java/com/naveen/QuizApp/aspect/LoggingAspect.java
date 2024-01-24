package com.naveen.QuizApp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private Logger loging =  Logger.getLogger(getClass().getName());
    @Pointcut("execution(* com.naveen.QuizApp.controller.MainController.*(..))")
    public void forControllerPackage()
    {
    }
    @Pointcut("execution(* com.naveen.QuizApp.dao.*.*(..))")
    public void forDaoPackage()
    {
    }
    @Pointcut("execution(* com.naveen.QuizApp.service.*.*(..))")
    public void forServicePackage()
    {
    }
    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {

        // display method we are calling
        String theMethod = theJoinPoint.getSignature().toShortString();
        loging.info("=====>> in @Before: calling method: " + theMethod);

        // display the arguments to the method

        // get the arguments
        Object[] args = theJoinPoint.getArgs();

        // loop throw and display args
        for (Object tempArg : args) {
            loging.info("=====>> argument: " + tempArg);
        }
    }
    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

        // display method we are returning from
        String theMethod = theJoinPoint.getSignature().toShortString();
        loging.info("=====>> in @AfterReturning: from method: " + theMethod);

        // display data returned
        loging.info("=====>> result: " + theResult);

    }
}
