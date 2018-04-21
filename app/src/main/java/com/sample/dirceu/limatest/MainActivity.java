package com.sample.dirceu.limatest;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sample.dirceu.limatest.adapters.NodeAdapter;
import com.sample.dirceu.limatest.components.DaggerNodeComponent;
import com.sample.dirceu.limatest.interfaces.RecyclerViewClickListener;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.module.NodeModule;
import com.sample.dirceu.limatest.players.AudioPlayerActivity;
import com.sample.dirceu.limatest.players.DocumentViewActivity;
import com.sample.dirceu.limatest.players.ImagePlayerActivity;
import com.sample.dirceu.limatest.presenter.NodePresenter;
import com.sample.dirceu.limatest.util.Utility;
import com.sample.dirceu.limatest.view.MainView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView,
        RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NodePresenter presenter;
    private String rootPath = "/nodes";
    private String childPath = "";
    private String currentPath = "";
    private String directoryName = "";
    private RecyclerView recyclerView;
    private NodeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);


        DaggerNodeComponent.builder()
                .appComponent(((NodeApp) getApplicationContext())
                        .getAppComponent())
                .nodeModule(new NodeModule(this))
                .build()
                .inject(this);


        attachUI();
        presenter.loadListOfNode(rootPath);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (currentPath != null) {
                backToParentDirectory();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


    }

    private void backToParentDirectory() {

        if (!currentPath.equals(rootPath)) {
            currentPath = currentPath.replace(childPath, "");
            presenter.loadListOfNode(currentPath);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (currentPath.equals(rootPath)) {
            super.onBackPressed();
        } else {
            backToParentDirectory();
        }

    }

    private void attachUI() {
        swipeRefreshLayout = findViewById(R.id.refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.recycler_view_nodes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }


    @Override
    public void showNodes(List<Node> nodeList) {
        adapter = new NodeAdapter(nodeList, this);
        recyclerView.setAdapter(adapter);
        setToolBarTitle(currentPath);


    }

    private void setToolBarTitle(String currentPath) {
        toolbar.setTitle(currentPath);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void openVideoPlayer(Node node) {

    }

    @Override
    public void openAudioPlayer(Node node) {
        Intent intent = new Intent(this, AudioPlayerActivity.class);
        intent.putExtra(Utility.AUDIO, node);
        startActivity(intent);


    }

    @Override
    public void openDocument(Node node) {
        Intent intent = new Intent(this, DocumentViewActivity.class);
        intent.putExtra(Utility.DOCUMENT, node);
        startActivity(intent);


    }

    @Override
    public void openImagePlayer(Node node) {

        Intent intent = new Intent(this, ImagePlayerActivity.class);
        intent.putExtra(Utility.IMAGE, node);
        startActivity(intent);

    }

    @Override
    public void recyclerViewClick(View view, int position) {
        Node currentNode = adapter.getNodeList().get(position);
        if ("inode/directory".equals(currentNode.getMimetype())) {
            currentPath = rootPath + currentNode.getPath();
            childPath = currentNode.getPath();
            presenter.loadListOfNode(currentPath);

        } else {
            presenter.openNodeWithPlayer(currentNode);


        }

    }

    @Override
    public void onRefresh() {
        presenter.loadListOfNode(currentPath);
        swipeRefreshLayout.setRefreshing(false);

    }
}
