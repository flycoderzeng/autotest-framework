package com.autotest.framework.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;


@Slf4j
public class BaseDataSource {

    public DruidDataSource dataSource;

    public Connection getConnection() {
        if (dataSource == null) {
            return null;
        }
        try {
            return dataSource.getConnection(10000);
        } catch (SQLException sqlException) {
            log.error("获取数据库连接异常，", sqlException);
        }

        return null;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                log.error("关闭数据库连接异常，", sqlException);
            }
        }
    }
}
