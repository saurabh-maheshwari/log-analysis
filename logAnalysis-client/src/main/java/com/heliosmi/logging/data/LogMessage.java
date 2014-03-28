/*******************************************************************************
 * Copyright 2014 Saurabh Maheshwari
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.heliosmi.logging.data;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Message bean for persistence. Utilizes a builder pattern for bean creation.
 * 
 * <p>
 * createdDate is number of milliseconds since January 1, 1970.
 * 
 * <p>
 * Request, response and errorStackTrace will be truncated to 1000 characters.
 * All the other fields will be limited to 50. These settings can be changed by changing
 * the constants.
 * 
 * @author Saurabh Maheshwari
 * 
 */
public class LogMessage extends BaseBean {

    private String threadID;
    private String className;
    private String methodName;
    private String packageName;
    private String applicationName;
    private String hostName;
    private long createdDate;
    private long duration;
    private String request;
    private String response;
    private boolean errorYN;
    private String errorStacktrace;

    public static class Builder {

        private static final int MAX_REQUEST_RESPONSE_ERRORSTACKTRACE_LENGTH = 1000;
        private static final int MAX_FIELD_LENGTH = 50;

        private String threadID;        
        private String className;
        private String methodName;
        private String packageName;
        private String applicationName;
        private String hostName;
        private long createdDate;
        private long duration;
        private String request;
        private String response;
        private boolean errorYN;
        private String errorStacktrace;

        public LogMessage build() {
            createdDate = new Date().getTime();
            return new LogMessage(this);
        }

        public Builder threadID(String strValue) {
            threadID = truncate(strValue, MAX_FIELD_LENGTH);
            return this;
        }

        public Builder className(String strValue) {
            className = truncate(strValue, MAX_FIELD_LENGTH);
            return this;
        }

        public Builder methodName(String strValue) {
            methodName = truncate(strValue, MAX_FIELD_LENGTH);
            return this;
        }

        public Builder hostName(String strValue) {
            hostName = truncate(strValue, MAX_FIELD_LENGTH);
            return this;
        }

        public Builder duration(long intValue) {
            duration = intValue;
            return this;
        }

        public Builder request(String string) {
            request = truncate(string, MAX_REQUEST_RESPONSE_ERRORSTACKTRACE_LENGTH);
            return this;
        }

        public Builder packageName(String string) {
            packageName = truncate(string, MAX_FIELD_LENGTH);
            return this;
        }

        public Builder response(String string) {
            response = truncate(string, MAX_REQUEST_RESPONSE_ERRORSTACKTRACE_LENGTH);
            return this;
        }

        public Builder errorYN(Boolean boolValue) {
            errorYN = boolValue;
            return this;
        }

        public Builder errorStacktrace(String string) {
            errorStacktrace = truncate(string, MAX_REQUEST_RESPONSE_ERRORSTACKTRACE_LENGTH);
            return this;
        }

        public Builder applicationName(String string) {
            applicationName = truncate(string, MAX_FIELD_LENGTH);
            return this;
        }

        private String truncate(String string, int maxValue) {
            return StringUtils.substring(string, 0, maxValue);
        }
    }

    private LogMessage(Builder builder) {
        createdDate = builder.createdDate;
        threadID = builder.threadID;
        className = builder.className;
        methodName = builder.methodName;
        hostName = builder.hostName;
        applicationName = builder.applicationName;
        duration = builder.duration;
        request = builder.request;
        response = builder.response;
        packageName = builder.packageName;
        errorYN = builder.errorYN;
        errorStacktrace = builder.errorStacktrace;
    }

    public String getThreadID() {
        return threadID;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getHostName() {
        return hostName;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getDuration() {
        return duration;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public boolean isErrorYN() {
        return errorYN;
    }

    public String getErrorStacktrace() {
        return errorStacktrace;
    }

}
