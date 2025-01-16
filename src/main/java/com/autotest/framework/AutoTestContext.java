package com.autotest.framework;

import com.autotest.framework.context.UserTestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.HttpCookie;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class AutoTestContext {
    public UserTestContext userTestContext;
    public Map<String, Object> variables;
    public Map<String, String> headers;
    public Collection<HttpCookie> cookies;
    public String taskId;

    public AutoTestContext(UserTestContext userTestContext, Map<String, Object> variables,
                           Map<String, String> headers, Collection<HttpCookie> cookies) {
        this.userTestContext = userTestContext;
        this.variables = variables;
        this.headers = headers;
        this.cookies = cookies;
        this.taskId = RandomStringUtils.randomNumeric(25);
        log.info("任务id: {}", this.taskId);
    }

    public void putHttpHeader(String key, String value) {
        headers.put(key, value);
    }

    public void putHttpCookie(HttpCookie cookie) {
        cookies.add(cookie);
    }

    public void putVariableObject(String key, Object value) {
        variables.put(key, value);
    }

    public String getVariableString(String key) {
        if (variables.get(key) == null) {
            log.error("变量 [{}] 的值是 null", key);
            return null;
        }
        return variables.get(key).toString();
    }

    public Object getVariableObject(String key) {
        return variables.get(key);
    }


    public void putGlobalVariableObject(String key, Object value) {
        userTestContext.putVariableObject(key, value);
    }

    public String getGlobalVariableString(String key) {
        return userTestContext.getVariableString(key);
    }

    public Object getGlobalVariableObject(String key) {
        return userTestContext.getVariableObject(key);
    }
}
