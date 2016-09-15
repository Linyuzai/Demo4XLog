package com.linyuzai.xlog;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class XLog {
    public static final String TAG = XLog.class.getSimpleName();

    private static boolean LOGGING = true;
    private static boolean LOG_CLASS_AND_METHOD = false;

    private static boolean VERBOSE = true;
    private static boolean DEBUG = true;
    private static boolean INFO = true;
    private static boolean WARN = true;
    private static boolean ERROR = true;

    private static LogFilter filter;
    private static Class filterClass;

    private XLog() {
    }

    private static boolean judgeLogging(String tag, String msg, String className) {
        if (!LOGGING)
            return false;
        if (!filterClass(className))
            return false;
        if (filter != null)
            return filter.filter(tag, msg);
        return true;
    }

    private static boolean filterClass(String className) {
        if (filterClass == null)
            return true;
        else {
            //Log.e(TAG, filterClass.getName() + "," + className);
            if (filterClass.getName().equals(className))
                return true;
        }
        return false;
    }

    private static String[] getClassMethod() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        boolean firstFlag = false;
        boolean secondFlag = false;
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
            //Log.e(TAG, className + "," + methodName);
            if (className.startsWith("dalvik") || className.startsWith("java") || className.contains("$override")
                    || className.equals("com.android.tools.fd.runtime.AndroidInstantRuntime")) {
            } else if (className.equals("com.linyuzai.xlog.XLog") && methodName.equals("getClassMethod")) {
                firstFlag = true;
            } else if (className.equals("com.linyuzai.xlog.XLog") && (methodName.equals("v") || methodName.equals("d")
                    || methodName.equals("i") || methodName.equals("w") || methodName.equals("e"))) {
                secondFlag = true;
            } else if (firstFlag && secondFlag)
                return new String[]{className, methodName + ">(" + lineNumber + ")"};
        }
        return new String[]{"no class", " no method"};
    }

    private static String addClassMethodOnMsg(String msg, String className, String methodName) {
        String name = className + "<" + methodName;
        if (msg == null)
            msg = name;
        else
            msg = "[" + name + "]" + msg;
        return msg;
    }

    private static void log(String tag, String msg, Throwable tr, LogType type) {
        switch (type) {
            case V:
                if (tr == null)
                    Log.v(tag, msg);
                else
                    Log.v(tag, msg, tr);
                break;
            case D:
                if (tr == null)
                    Log.d(tag, msg);
                else
                    Log.d(tag, msg, tr);
                break;
            case I:
                if (tr == null)
                    Log.i(tag, msg);
                else
                    Log.i(tag, msg, tr);
                break;
            case W:
                if (tr == null && msg != null)
                    Log.w(tag, msg);
                else if (tr != null && msg == null) {
                    Log.w(tag, tr);
                } else
                    Log.w(tag, msg, tr);
                break;
            case E:
                if (tr == null)
                    Log.e(tag, msg);
                else
                    Log.e(tag, msg, tr);
                break;
        }
    }

    public static boolean isLoggable() {
        return LOGGING;
    }

    /**
     * 是否允许log
     *
     * @param l
     */
    public static void setLoggable(boolean l) {
        XLog.LOGGING = l;
    }

    public static boolean isLogClassAndMethod() {
        return LOG_CLASS_AND_METHOD;
    }

    /**
     * 是否输出调用类和方法
     *
     * @param logClassAndMethod
     */
    public static void setLogClassAndMethod(boolean logClassAndMethod) {
        LOG_CLASS_AND_METHOD = logClassAndMethod;
    }

    public static boolean isVerbose() {
        return VERBOSE;
    }

    /**
     * 是否允许log.v
     *
     * @param v
     */
    public static void setVerbose(boolean v) {
        XLog.VERBOSE = v;
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    /**
     * 是否允许log.d
     *
     * @param d
     */
    public static void setDebug(boolean d) {
        XLog.DEBUG = d;
    }

    public static boolean isInfo() {
        return INFO;
    }

    /**
     * 是否允许log.i
     *
     * @param i
     */
    public static void setInfo(boolean i) {
        XLog.INFO = i;
    }

    public static boolean isWarn() {
        return WARN;
    }

    /**
     * 是否允许log.w
     *
     * @param w
     */
    public static void setWarn(boolean w) {
        XLog.WARN = w;
    }

    public static boolean isError() {
        return ERROR;
    }

    /**
     * 是否允许log.e
     *
     * @param e
     */
    public static void setError(boolean e) {
        XLog.ERROR = e;
    }

    /**
     * 设置过滤器
     *
     * @param filter
     */
    public static void setLogFilter(LogFilter filter) {
        XLog.filter = filter;
    }

    /**
     * 设置过滤类，只显示此类的log
     *
     * @param filterClass
     */
    public static void filterClass(Class filterClass) {
        XLog.filterClass = filterClass;
    }

    /**
     * 重置，重置之后的初始效果和普通Log一样
     */
    public static void reset() {
        LOGGING = true;
        LOG_CLASS_AND_METHOD = false;
        VERBOSE = true;
        DEBUG = true;
        INFO = true;
        WARN = true;
        ERROR = true;
        filter = null;
        filterClass = null;
    }

    public static void v(String tag, String msg, Throwable tr) {
        String[] classMethod = getClassMethod();
        if (VERBOSE && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.V);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        String[] classMethod = getClassMethod();
        if (DEBUG && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.D);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        String[] classMethod = getClassMethod();
        if (INFO && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.I);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        String[] classMethod = getClassMethod();
        if (WARN && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.W);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        String[] classMethod = getClassMethod();
        if (ERROR && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.E);
        }
    }

    public static void v(String tag, String msg) {
        String[] classMethod = getClassMethod();
        if (VERBOSE && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, null, LogType.V);
        }
    }

    public static void d(String tag, String msg) {
        String[] classMethod = getClassMethod();
        if (DEBUG && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, null, LogType.D);
        }
    }

    public static void i(String tag, String msg) {
        String[] classMethod = getClassMethod();
        if (INFO && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, null, LogType.I);
        }
    }

    public static void w(String tag, String msg) {
        String[] classMethod = getClassMethod();
        if (WARN && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, null, LogType.W);
        }
    }

    public static void w(String tag, Throwable tr) {
        String[] classMethod = getClassMethod();
        String msg = null;
        if (WARN && judgeLogging(tag, null, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(null, classMethod[0], classMethod[1]);
            log(tag, msg, tr, LogType.W);
        }
    }

    public static void e(String tag, String msg) {
        String[] classMethod = getClassMethod();
        if (ERROR && judgeLogging(tag, msg, classMethod[0])) {
            if (LOG_CLASS_AND_METHOD)
                msg = addClassMethodOnMsg(msg, classMethod[0], classMethod[1]);
            log(tag, msg, null, LogType.E);
        }
    }
}
