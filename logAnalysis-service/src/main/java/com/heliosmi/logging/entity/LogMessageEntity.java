package com.heliosmi.logging.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.heliosmi.logging.data.BaseBean;

/**
 * Entity class for LogMessage.
 * @author Saurabh Maheshwari
 *
 */
@Entity
public class LogMessageEntity extends BaseBean {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String threadID;
    private String correlationID;
    private String className;
    private String methodName;
    private String packageName;
    private String applicationName;
    private String hostName;
    private long createdDate;
    private long duration;
        
    @Lob
    private String request;
    
    @Lob
    private String response;
    private boolean errorYN;
    
    @Lob
    private String errorStacktrace;
    
    public String getThreadID() {
        return threadID;
    }
    public void setThreadID(String threadID) {
        this.threadID = threadID;
    }
    public String getCorrelationID() {
        return correlationID;
    }
    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public long getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public boolean isErrorYN() {
        return errorYN;
    }
    public void setErrorYN(boolean errorYN) {
        this.errorYN = errorYN;
    }
    public String getErrorStacktrace() {
        return errorStacktrace;
    }
    public void setErrorStacktrace(String errorStacktrace) {
        this.errorStacktrace = errorStacktrace;
    }
    public long getId() {
        return id;
    }
}
