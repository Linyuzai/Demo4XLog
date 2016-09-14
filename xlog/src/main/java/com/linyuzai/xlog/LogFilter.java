package com.linyuzai.xlog;

/**
 * Created by Administrator on 2016/9/14 0014.
 * <p/>
 * 过滤掉
 */
public interface LogFilter {
    boolean filter(String tag, String msg);
}
