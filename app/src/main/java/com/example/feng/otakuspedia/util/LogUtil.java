package com.example.feng.otakuspedia.util;

import android.util.Log;

// 日志单例工具类
public class LogUtil {

    private static LogUtil instance = null;

    /**
     * 改造方法私有化
     */
    private LogUtil() {}

    /**
     * 懒汉式单例, 无法保证多线程环境单例
     * @return
     */
    public static LogUtil lazilyGetInstance() {
        if (instance == null) {
            instance = new LogUtil();
        }
        return instance;
    }

    /**
     * 获取日志工具单例---双重检验锁实现
     * @return
     */
    public static LogUtil getSingleInstance() {
        if (instance == null) {
            synchronized (LogUtil.class) {
                if (instance == null) {
                    instance = new LogUtil();
                }
            }
        }
        return instance;
    }

    public void debug(String tag, String msg) {
        Log.d(tag, msg);
    }

    public void info(String tag, String msg) {
        Log.i(tag, msg);
    }

    public void error(String tag, String msg) {
        Log.e(tag, msg);
    }
}
