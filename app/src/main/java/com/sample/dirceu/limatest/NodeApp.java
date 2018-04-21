package com.sample.dirceu.limatest;

import android.app.Application;
import android.content.Context;

import com.sample.dirceu.limatest.components.AppComponent;
import com.sample.dirceu.limatest.components.DaggerAppComponent;
import com.sample.dirceu.limatest.module.AppModule;
import com.sample.dirceu.limatest.module.ServiceManagerModule;

public class NodeApp extends Application {

    private AppComponent appComponent;

    private static NodeApp instance;

    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppComponents();
    }

    public static NodeApp get(Context context) {
        return (NodeApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void initAppComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .serviceManagerModule(new ServiceManagerModule())
                .build();
    }
}
