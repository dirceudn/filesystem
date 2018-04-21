package com.sample.dirceu.limatest.components;


import com.sample.dirceu.limatest.MainActivity;
import com.sample.dirceu.limatest.module.ActivityScope;
import com.sample.dirceu.limatest.module.NodeModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = NodeModule.class)
public interface NodeComponent {

    void inject(MainActivity activity);
}
