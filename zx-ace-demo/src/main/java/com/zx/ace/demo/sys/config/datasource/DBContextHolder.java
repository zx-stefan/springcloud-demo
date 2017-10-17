package com.zx.ace.demo.sys.config.datasource;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
public class DBContextHolder {
    public static final String DATA_SOURCE_A = "test";
    public static final String DATA_SOURCE_B = "cms_test";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setDBType(String dbType){
        contextHolder.set(dbType);
    }
    public static String getDBType(){
        return contextHolder.get();
    }
    public static void clearDBType(){
        contextHolder.remove();
    }

}
