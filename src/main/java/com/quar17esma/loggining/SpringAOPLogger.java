package com.quar17esma.loggining;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class SpringAOPLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.quar17esma.service..*.*(..)) || " +
            "execution(* com.quar17esma.controller..*.*(..)) || " +
            "execution(* com.quar17esma.dao..*.*(..))")
    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object retVal = joinPoint.proceed();

        stopWatch.stop();

        StringBuilder logMessage = makeExecutionTimeLogMessage(joinPoint, stopWatch);
        logger.debug(logMessage.toString());

        return retVal;
    }

    private StringBuilder makeExecutionTimeLogMessage(ProceedingJoinPoint joinPoint, StopWatch stopWatch) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");

        appendArgs(joinPoint, logMessage);

        logMessage.append(")");
        logMessage.append(" execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");

        return logMessage;
    }

    private void appendArgs(ProceedingJoinPoint joinPoint, StringBuilder logMessage) {
        Object[] args = joinPoint.getArgs();
        for (Object object:args) {
            logMessage.append(object).append(",");
        }
        if (args.length > 0) {
            logMessage.deleteCharAt(logMessage.length() - 1);
        }
    }
}
