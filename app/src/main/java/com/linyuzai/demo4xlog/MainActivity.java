package com.linyuzai.demo4xlog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linyuzai.xlog.LogFilter;
import com.linyuzai.xlog.XLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loggable, v, d, i, w, e, class_method, filter, my_class, reset, log;
    LogFilter logFilter = new LogFilter() {
        @Override
        public boolean filter(String tag, String msg) {
            if (msg.contains("m") || tag.contains("i"))
                return false;
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loggable = (Button) findViewById(R.id.loggable);
        v = (Button) findViewById(R.id.v);
        d = (Button) findViewById(R.id.d);
        i = (Button) findViewById(R.id.i);
        w = (Button) findViewById(R.id.w);
        e = (Button) findViewById(R.id.e);
        class_method = (Button) findViewById(R.id.class_method);
        filter = (Button) findViewById(R.id.filter);
        my_class = (Button) findViewById(R.id.my_class);
        reset = (Button) findViewById(R.id.reset);
        log = (Button) findViewById(R.id.log);

        loggable.setOnClickListener(this);
        v.setOnClickListener(this);
        d.setOnClickListener(this);
        i.setOnClickListener(this);
        w.setOnClickListener(this);
        e.setOnClickListener(this);
        class_method.setOnClickListener(this);
        filter.setOnClickListener(this);
        my_class.setOnClickListener(this);
        reset.setOnClickListener(this);
        log.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loggable:
                XLog.setLoggable(!XLog.isLoggable());
                break;
            case R.id.v:
                XLog.setVerbose(!XLog.isVerbose());
                break;
            case R.id.d:
                XLog.setDebug(!XLog.isDebug());
                break;
            case R.id.i:
                XLog.setInfo(!XLog.isInfo());
                break;
            case R.id.w:
                XLog.setWarn(!XLog.isWarn());
                break;
            case R.id.e:
                XLog.setError(!XLog.isError());
                break;
            case R.id.class_method:
                XLog.setLogClassAndMethod(!XLog.isLogClassAndMethod());
                break;
            case R.id.filter:
                XLog.setLogFilter(logFilter);
                break;
            case R.id.my_class:
                XLog.filterClass(MyClass.class);
                break;
            case R.id.reset:
                XLog.reset();
                break;
            case R.id.log:
                XLog.v("v", "msg");
                XLog.d("d", "debug");
                XLog.i("i", "info");
                XLog.w("w", "msg");
                XLog.e("e", "error");
                new MyClass().log();
                break;
        }
    }
}
