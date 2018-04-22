package com.sample.dirceu.limatest.presenter;

import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.view.MainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NodePresenter {

    private MainView mainView;
    private BasePresenter basePresenter;


    @Inject
    NodePresenter(MainView view, BasePresenter presenter) {
        this.basePresenter = presenter;
        this.mainView = view;

    }


    public void loadListOfNode(String url) {
        Observable<List<Node>> observable =
                basePresenter.getNode(url).subscribeOn(Schedulers.io()) // "work" on io thread
                        .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<List<Node>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Node> nodes) {
                if (!nodes.isEmpty()) {
                    mainView.showNodes(nodes);
                }

            }
        });


    }

    public void openNodeWithPlayer(Node node) {
        if (node.getMimetype().startsWith("image")) {
            mainView.openImagePlayer(node);
        } else if (node.getMimetype().startsWith("audio")) {
            mainView.openAudioPlayer(node);
        } else if (node.getMimetype().startsWith("video")) {
            mainView.openVideoPlayer(node);
        } else if (node.getMimetype().startsWith("application") ||
                node.getMimetype().startsWith("txt")
                || node.getMimetype().startsWith("text")) {
            mainView.openDocument(node);
        }


    }


}
