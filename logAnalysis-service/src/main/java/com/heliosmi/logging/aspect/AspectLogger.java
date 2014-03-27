package com.heliosmi.logging.aspect;

import java.net.UnknownHostException;
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
 * AOP Logger to log across all Spring beans with in the application. It should not use LogSender to submit data to sink.
 * @author Saurabh Maheshwari
 *
 */
@Component
@Aspect
public class AspectLogger {
    private static final AtomicLong uniqueId = new AtomicLong(0);
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

        try {
            returnValue = pjp.proceed();
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        LogMessage logMessage = new LogMessage.Builder().applicationName(APP_NAME)
                .className(pjp.getTarget().getClass().getSimpleName())
                .duration(stopwatch.elapsed(TimeUnit.MILLISECONDS)).hostName(getLocalHostName())
                .methodName(pjp.getSignature().getName())
                .packageName(pjp.getTarget().getClass().getPackage().toString())
                .request(generateToString(pjp.getArgs())).threadID(uniqueNum.get().toString()).build();

        log.info(logMessage.toString());

        return returnValue;

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

    private String generateToString(Object[] args) {
        return ToStringBuilder.reflectionToString(args, ToStringStyle.DEFAULT_STYLE);
    }

}
