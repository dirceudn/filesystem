package com.sample.dirceu.limatest.module;


import com.sample.dirceu.limatest.presenter.BasePresenter;
import com.sample.dirceu.limatest.presenter.NodePresenter;
import com.sample.dirceu.limatest.presenter.NodePresenterImpl;
import com.sample.dirceu.limatest.services.ServiceManager;
import com.sample.dirceu.limatest.view.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class NodeModule {

    private MainView mainView;

    public NodeModule(MainView view) {
        mainView = view;
    }

    @ActivityScope
    @Provides
    public MainView provideILoginView() {
        return mainView;
    }

    @ActivityScope
    @Provides
    public BasePresenter provideBasePresenter() {
        return new NodePresenterImpl(new ServiceManager());
    }
}
