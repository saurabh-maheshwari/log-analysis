package com.heliosmi.logging.aspect;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heliomi.logging.util.ServiceTestDataFactory;
import com.heliosmi.logging.listener.LogListener;

public class AspectLoggerTest {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Check if an Exception thrown during {@link ProceedingJoinPoint} execution
     * is bubbled out of AspectLogger.
     * 
     * @throws Throwable
     */
    @Test
    public void testProfilerException() throws Throwable {
        AspectLogger aspectLogger = new AspectLogger();
        ProceedingJoinPoint mockPjp = mock(ProceedingJoinPoint.class);

        when(mockPjp.proceed()).thenThrow(new RuntimeException("Dummy Exception"));
        when(mockPjp.getTarget()).thenReturn(LogListener.class);

        Signature mockSignature = mock(Signature.class);
        when(mockSignature.getName()).thenReturn("onMesage");
        when(mockPjp.getSignature()).thenReturn(mockSignature);
        when(mockPjp.getArgs()).thenReturn(new Object[] { ServiceTestDataFactory.createActiveMQMapMessage() });

        try {
            Object obj = aspectLogger.profiler(mockPjp);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Dummy Exception"));
        }
    }

    /**
     * Makes sure null is returned during execution.
     * 
     * @throws Throwable
     */
    @Test
    public void testProfilerReturnValueNull() throws Throwable {
        AspectLogger aspectLogger = new AspectLogger();
        ProceedingJoinPoint mockPjp = mock(ProceedingJoinPoint.class);

        when(mockPjp.proceed()).thenReturn(null);
        when(mockPjp.getTarget()).thenReturn(LogListener.class);

        Signature mockSignature = mock(Signature.class);
        when(mockSignature.getName()).thenReturn("onMesage");
        when(mockPjp.getSignature()).thenReturn(mockSignature);
        when(mockPjp.getArgs()).thenReturn(new Object[] { ServiceTestDataFactory.createActiveMQMapMessage() });

        Object obj = aspectLogger.profiler(mockPjp);

        assertTrue(obj == null);
    }
}
