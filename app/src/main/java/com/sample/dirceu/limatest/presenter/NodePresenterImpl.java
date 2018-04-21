package com.sample.dirceu.limatest.presenter;

import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.services.ServiceManager;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class NodePresenterImpl implements BasePresenter {

    private ServiceManager nodeApi;

    @Inject
    public NodePresenterImpl(ServiceManager serviceManager){
        nodeApi = serviceManager;
    }

    @Override
    public Observable<List<Node>> getNode(String url) {
        return nodeApi.getNode(url);
    }
}
