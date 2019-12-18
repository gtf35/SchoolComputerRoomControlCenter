package top.gtf35.controlcenter.common.utils;

/*
 *log工具类
 *博客：https://blog.csdn.net/jdsjlzx/article/details/51500292
 */

import android.util.Log;

import top.gtf35.controlcenter.BuildConfig;


public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    static LocalLog localLog = new LocalLog();

    private LogUtils(){
        /* Protect from instantiations */
    }

    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("日志：");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message){

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.e(className, context);
        localLog.writeLocalLog("等级：E|" + context);
    }


    public static void i(String message){

        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.i(className, context);
        localLog.writeLocalLog("等级：I|" + context);
    }

    public static void d(String message){

        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.d(className, context);
        localLog.writeLocalLog("等级：D|" + context);
    }

    public static void v(String message){

        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.v(className, context);
        localLog.writeLocalLog("等级：V|" + context);
    }

    public static void w(String message){

        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.w(className, context);
        localLog.writeLocalLog("等级：W|" + context);
    }

    public static void wtf(String message){

        getMethodNames(new Throwable().getStackTrace());
        String context = createLog(message);
        if(isDebuggable()) Log.wtf(className, context);
        localLog.writeLocalLog("等级：WTF|" + context);
    }

}
