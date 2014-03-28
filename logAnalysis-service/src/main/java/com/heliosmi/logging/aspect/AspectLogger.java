package com.heliosmi.logging.aspect;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.activemq.util.InetAddressUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.heliosmi.logging.data.LogMessage;

/**
 * AOP Logger to log across all Spring beans with in the application. It should
 * not use LogSender to submit data to sink.
 * 
 * Generates a unique threadId using UNIX time.
 * @author Saurabh Maheshwari
 * 
 */
@Component
@Aspect
public class AspectLogger {
    
    private static final AtomicLong uniqueId = new AtomicLong(new Date().getTime());
    private static final ThreadLocal<Long> uniqueNum = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return uniqueId.getAndIncrement();
        }
    };

    private static final String APP_NAME = "LogAnalysis";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("within(com.heliosmi.logging..*)")
    public void allLocalBeans() {
    }

    @Around("allLocalBeans()")
    public Object profiler(ProceedingJoinPoint pjp) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = pjp.proceed();
        } catch (Throwable ex) {
            throwable = ex;
        }
        LogMessage logMessage = new LogMessage.Builder().applicationName(APP_NAME)
                .className(pjp.getTarget().getClass().getSimpleName())
                .duration(stopwatch.elapsed(TimeUnit.MILLISECONDS))
                .hostName(getLocalHostName())
                .methodName(pjp.getSignature().getName())
                .packageName(pjp.getTarget().getClass().getPackage().toString())
                .request(generateToString(pjp.getArgs()))
                .threadID(uniqueNum.get().toString())
                .response(ifNotNull(returnValue)).errorStacktrace(ifNotNull(throwable))
                .errorYN(throwable != null ? Boolean.TRUE : Boolean.FALSE).build();

        log.info(logMessage.toString());

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;

    }

    /**
     * Creates a toString if the <code>returnValue</code> is not null.
     * 
     * @param returnValue
     * @return
     */
    private String ifNotNull(Object returnValue) {
        return returnValue != null ? generateToString(returnValue) : null;
    }

    private String generateToString(Object returnValue) {
        String returnValueString = ToStringBuilder.reflectionToString(returnValue, ToStringStyle.DEFAULT_STYLE);
        return returnValueString;
    }
    
    private String generateToString(Object[] args) {
        return ToStringBuilder.reflectionToString(args, ToStringStyle.DEFAULT_STYLE);
    }

    private String getLocalHostName() {
        String localHostName = "UNKNOWN_HOST";
        try {
            localHostName = InetAddressUtil.getLocalHostName();
        } catch (UnknownHostException e) {
            log.error("Cannot identify hostName.");
        }

        return localHostName;
    }

    

}
