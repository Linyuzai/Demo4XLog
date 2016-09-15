# Demo4XLog
just a log util

```
<dependency>
  <groupId>com.linyuzai</groupId>
  <artifactId>xlog</artifactId>
  <version>0.91</version>
  <type>pom</type>
</dependency>

or

compile 'com.linyuzai:xlog:0.91'
```

```
/**
* 是否允许log
*
* @param l
*/
public static void setLoggable(boolean l);

/**
* 是否输出调用类和方法
*
* @param logClassAndMethod
*/
public static void setLogClassAndMethod(boolean logClassAndMethod);

/**
* 是否允许log.v
*
* @param v
*/
public static void setVerbose(boolean v);

**
* 是否允许log.d
*
* @param d
*/
public static void setDebug(boolean d);

/**
* 是否允许log.i
*
* @param i
*/
public static void setInfo(boolean i);

/**
* 是否允许log.w
*
* @param w
*/
public static void setWarn(boolean w);

/**
* 是否允许log.e
*
* @param e
*/
public static void setError(boolean e);

/**
* 设置过滤器
*
* @param filter
*/
public static void setLogFilter(LogFilter filter);

LogFilter logFilter = new LogFilter() {
    @Override
    public boolean filter(String tag, String msg) {
        if (msg.contains("m") || tag.contains("i"))
            return false;
        return true;
    }
};

/**
* 设置过滤类，只显示此类的log
*
* @param filterClass
*/
public static void filterClass(Class filterClass);

/**
* 重置，重置之后的初始效果和普通Log一样
*/
ublic static void reset();
```
