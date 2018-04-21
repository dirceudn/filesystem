package com.sample.dirceu.limatest.module;

import com.sample.dirceu.limatest.services.ServiceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceManagerModule {

    public ServiceManagerModule(){ }
    @Singleton
    @Provides
    ServiceManager provideServiceWrapper() {
        return new ServiceManager();
    }


}
