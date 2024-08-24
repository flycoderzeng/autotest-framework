package com.autotest.framework.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.autotest.framework.common.entities.JdbcConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class JdbcDataSource extends BaseDataSource {
    private Boolean isInit = false;
    private final JdbcConfig jdbcConfig;

    public JdbcDataSource(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

    public void initDataSource() {
        if (Boolean.TRUE.equals(isInit)) {
            destroy();
        }
        if (jdbcConfig == null) {
            throw new NullPointerException();
        }
        initDruidDataSource(jdbcConfig);
    }

    public void initDruidDataSource(JdbcConfig jdbcConfig) {
        log.info(JSONObject.toJSONString(jdbcConfig));
        dataSource = new DruidDataSource();
        dataSource.setUrl(jdbcConfig.getJdbcUrl());
        dataSource.setUsername(jdbcConfig.getUsername());
        dataSource.setPassword(jdbcConfig.getPassword());
        dataSource.setDriverClassName(jdbcConfig.getDriverClassName());
        dataSource.setInitialSize(3);
        dataSource.setMaxActive(jdbcConfig.getMaxPoolSize());
        dataSource.setMinIdle(1);
        // 配置获取连接等待超时的时间，单位毫秒。
        dataSource.setMaxWait(60000);
        if (jdbcConfig.getJdbcUrl().toLowerCase().contains("oracle")) {
            dataSource.setValidationQuery("select 1 from dual");
        } else {
            dataSource.setValidationQuery("select 1");
        }
        // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        dataSource.setTestWhileIdle(true);
        // 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        dataSource.setTestOnBorrow(false);
        // 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        dataSource.setTestOnReturn(false);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(600000000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);
        // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
        dataSource.setPoolPreparedStatements(false);
        dataSource.setDefaultAutoCommit(true);
        try {
            dataSource.init();
        } catch (SQLException exception) {
            log.error("初始话druid连接池失败, ", exception);
        }
    }

    @Override
    public Connection getConnection() {
        if (Boolean.FALSE.equals(isInit)) {
            initDataSource();
            isInit = true;
        }
        return super.getConnection();
    }

    public void destroy() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

