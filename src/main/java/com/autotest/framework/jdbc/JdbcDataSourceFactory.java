package com.autotest.framework.jdbc;


import com.autotest.framework.common.entities.JdbcConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class JdbcDataSourceFactory {
    private static volatile JdbcDataSourceFactory jdbcDataSourceFactory;
    public Map<Integer, BaseDataSource> dataSourceMaps = new ConcurrentHashMap<>();

    public static JdbcDataSourceFactory getInstance() {
        if(jdbcDataSourceFactory == null) {
            synchronized (JdbcDataSourceFactory.class) {
                jdbcDataSourceFactory = new JdbcDataSourceFactory();
            }
        }
        return jdbcDataSourceFactory;
    }

    private JdbcDataSourceFactory() {

    }

    public Connection getConnection(JdbcConfig jdbcConfig) {
        initDataSourceMap(jdbcConfig);
        return dataSourceMaps.get(jdbcConfig.hashCode()).getConnection();
    }

    synchronized public void initDataSourceMap(JdbcConfig jdbcConfig) {
        if (!dataSourceMaps.containsKey(jdbcConfig.hashCode())) {
            dataSourceMaps.put(jdbcConfig.hashCode(), new JdbcDataSource(jdbcConfig));
        }
    }
}
