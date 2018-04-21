package com.sample.dirceu.limatest.view;

import com.sample.dirceu.limatest.model.Node;

import java.util.List;

public interface MainView {

    void showNodes(List<Node> nodeList);

    void showError(String message);

    void openVideoPlayer(Node node);

    void openAudioPlayer(Node node);

    void openDocument(Node node);

    void openImagePlayer(Node node);
}
