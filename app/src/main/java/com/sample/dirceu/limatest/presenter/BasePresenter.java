package com.sample.dirceu.limatest.presenter;

import com.sample.dirceu.limatest.model.Node;

import java.util.List;

import rx.Observable;

public interface BasePresenter {

    Observable<List<Node>> getNode(String url);

}
