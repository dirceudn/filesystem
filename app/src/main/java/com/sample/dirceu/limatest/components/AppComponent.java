package com.sample.dirceu.limatest.components;

import android.app.Application;

import com.sample.dirceu.limatest.module.AppModule;
import com.sample.dirceu.limatest.module.ServiceManagerModule;
import com.sample.dirceu.limatest.services.ServiceManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ServiceManagerModule.class})
public interface AppComponent {

    Application application();
    ServiceManager serviceManager();

}
