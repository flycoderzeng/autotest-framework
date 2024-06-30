package com.autotest.framework.context;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.alibaba.fastjson.JSONObject;
import com.autotest.framework.common.entities.JdbcConfig;
import com.autotest.framework.jdbc.JdbcDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserTestContext {
    private static volatile UserTestContext userTestContext;
    private final JdbcDataSourceFactory jdbcDataSourceFactory = JdbcDataSourceFactory.getInstance();
    // 全局变量
    public Map<String, Object> variables = new HashMap<>();
    public String testEnvName;
    // 应用配置文件application.yml
    public Dict applicationVariablesDict;
    // 环境配置文件 test_env_config_xxx.yml
    public Dict testVariablesDict;

    public String platformMasterServerIp;
    public String platformMasterServerAdminPort;
    public String platformMasterRootPassword;

    public static UserTestContext getInstance() {
        if(userTestContext == null) {
            synchronized (UserTestContext.class) {
                if(userTestContext == null) {
                    userTestContext = new UserTestContext();
                }
            }
        }
        return userTestContext;
    }

    protected UserTestContext() {
        init();
    }

    public void init() {
        initTestConfig();
        initTestEnvConfig();
    }

    public void initTestConfig() {
        URL resource = this.getClass().getClassLoader().getResource("application.yml");
        if(resource == null) {
            throw new RuntimeException("application.yml 配置文件不存在");
        }
        File file = new File(resource.getFile());
        String path = file.getAbsolutePath();
        log.info("配置文件路径: {}", path);
        applicationVariablesDict = YamlUtil.loadByPath(path);
        Object objectEnvName = applicationVariablesDict.getByPath("test.envName");
        if(objectEnvName == null || StringUtils.isBlank(objectEnvName.toString())) {
            throw new RuntimeException("[test.envName]环境名称不能为空");
        }
        testEnvName = objectEnvName.toString();
        log.info("测试环境名称: {}", testEnvName);
    }

    public void initTestEnvConfig() {
        String configFileName = "test_env_config_" + testEnvName + ".yml";
        URL resource = this.getClass().getClassLoader().getResource(configFileName);
        if(resource == null) {
            throw new RuntimeException("环境配置文件[" + configFileName +"]不存在");
        }
        File file = new File(resource.getFile());
        String path = file.getAbsolutePath();
        log.info("环境配置文件路径: {}", path);
        testVariablesDict = YamlUtil.loadByPath(path);
        Object objectServerIp = testVariablesDict.getByPath("cipherPlatform.master.serverIp");
        if(objectServerIp != null) {
            this.platformMasterServerIp = objectServerIp.toString();
        }
        Object objectServerAdminPort = testVariablesDict.getByPath("cipherPlatform.master.serverAdminPort");
        if(objectServerAdminPort != null) {
            this.platformMasterServerAdminPort = objectServerAdminPort.toString();
        }
        Object objectRootPassword = testVariablesDict.getByPath("cipherPlatform.master.rootPassword");
        if(objectServerAdminPort != null) {
            this.platformMasterRootPassword = objectRootPassword.toString();
        }
    }

    public String getTestEnvName() {
        return testEnvName;
    }

    public Connection getConnection(String datasourceName) {
        log.info("datasourceName: {}", datasourceName);
        return jdbcDataSourceFactory.getConnection(getJdbcConfig(datasourceName));
    }

    public void putVariableObject(String key, Object value) {
        variables.put(key, value);
    }

    public String getVariableString(String key) {
        if (variables.get(key) == null) {
            log.error("全局变量 [{}] 的值是 null", key);
            return null;
        }
        return variables.get(key).toString();
    }

    public Object getVariableObject(String key) {
        return variables.get(key);
    }

    public JdbcConfig getJdbcConfig(String datasourceName) {
        return JSONObject.parseObject(JSONObject.toJSONString(testVariablesDict.getByPath(datasourceName)), JdbcConfig.class);
    }

    public Object getApplicationConfig(String path) {
        return applicationVariablesDict.getByPath(path);
    }

    public Object getTestConfig(String path) {
        return testVariablesDict.getByPath(path);
    }
}
