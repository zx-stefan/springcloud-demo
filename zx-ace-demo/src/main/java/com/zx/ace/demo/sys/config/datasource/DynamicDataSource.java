package com.zx.ace.demo.sys.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDBType();
    }
}
