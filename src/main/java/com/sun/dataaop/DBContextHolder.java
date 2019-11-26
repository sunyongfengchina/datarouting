package com.sun.dataaop;


public class DBContextHolder {
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE);
    }

    /**
     * 请求结束前清空本地线程变量  防止内存泄漏
     */
    public static void clear() {
        if (contextHolder.get() != null) {
            contextHolder.remove();
        }
    }
}
