package com.autotest.framework.common.entities;


import lombok.Data;

@Data
public class JdbcConfig {
    private String jdbcUrl;
    private String driverClassName;
    private String username;
    private String password;
    private int maxPoolSize = 100;
    private int minPoolSize = 3;

    public JdbcConfig() {
    }

    public JdbcConfig(String jdbcUrl, String driverClassName, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.driverClassName = driverClassName;
        this.username = username;
        this.password = password;
    }
}
