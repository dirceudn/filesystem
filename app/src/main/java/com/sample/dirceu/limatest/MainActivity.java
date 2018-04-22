package com.sample.dirceu.limatest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sample.dirceu.limatest.adapters.NodeAdapter;
import com.sample.dirceu.limatest.components.DaggerNodeComponent;
import com.sample.dirceu.limatest.interfaces.RecyclerViewClickListener;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.module.NodeModule;
import com.sample.dirceu.limatest.players.AudioPlayerActivity;
import com.sample.dirceu.limatest.players.DocumentViewActivity;
import com.sample.dirceu.limatest.players.ImagePlayerActivity;
import com.sample.dirceu.limatest.players.VideoPlayerActivity;
import com.sample.dirceu.limatest.presenter.NodePresenter;
import com.sample.dirceu.limatest.util.Utility;
import com.sample.dirceu.limatest.view.MainView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView,
        RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, View.OnClickListener {

    @Inject
    NodePresenter presenter;
    private Node currentNode;
    private String rootPath = "/nodes";
    private String childPath = "";
    private String currentPath = "";
    private String directoryName = "";
    private RecyclerView recyclerView;
    private NodeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private RelativeLayout relativeLayoutOptions;
    private TextView textViewFileNameOpt;
    private TextView textViewFileSizeOpt;
    private TextView textViewFileTypeOpt;


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

    private void attachUI() {
        swipeRefreshLayout = findViewById(R.id.refresh);
        relativeLayoutOptions = findViewById(R.id.option);
        textViewFileNameOpt = findViewById(R.id.text_opt_name);
        textViewFileSizeOpt = findViewById(R.id.text_opt_size);
        textViewFileTypeOpt = findViewById(R.id.text_opt_type);

        TextView textViewOpenWith = findViewById(R.id.btn_open_with);
        TextView textViewShareWith = findViewById(R.id.btn_share);
        TextView textViewShareLink = findViewById(R.id.btn_share_link);

        ImageView imageViewCloseOpt = findViewById(R.id.image_close);
        imageViewCloseOpt.setOnClickListener(this);

        textViewOpenWith.setOnClickListener(this);
        textViewShareWith.setOnClickListener(this);
        textViewShareLink.setOnClickListener(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.recycler_view_nodes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager)
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);

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
        if (currentPath.equals(rootPath) || currentPath.isEmpty()) {
            super.onBackPressed();
        } else {
            backToParentDirectory();
        }

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
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra(Utility.VIDEO, node);
        startActivity(intent);

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
    public void openFileOptionsMenu(Node node) {
        relativeLayoutOptions.setVisibility(View.VISIBLE);
        textViewFileNameOpt.setText(node.getName());
        textViewFileSizeOpt.setText(Utility.getFileSize(node.getSize()));
        textViewFileTypeOpt.setText(node.getMimetype());

    }

    @Override
    public void hideFileOptionsMenu() {
        textViewFileNameOpt.setText(null);
        textViewFileSizeOpt.setText(null);
        textViewFileTypeOpt.setText(null);
        relativeLayoutOptions.setVisibility(View.GONE);


    }

    @Override
    public void shareFileWithFriends(Node node) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, node.getUrl());
        shareIntent.setType("text/plain");
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(shareIntent,
                    "Share link with..."));
        }

    }

    @Override
    public void openFileWith(Node node) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(node.getUrl()), node.getMimetype());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    @Override
    public void recyclerViewClick(View view, int position, String tag) {
        currentNode = adapter.getNodeList().get(position);
        if ("OPTIONS".equals(tag)) {
            openFileOptionsMenu(currentNode);
            relativeLayoutOptions.setVisibility(View.VISIBLE);

        } else if (!relativeLayoutOptions.isShown()) {
            if ("inode/directory".equals(currentNode.getMimetype())) {
                currentPath = rootPath + currentNode.getPath();
                childPath = currentNode.getPath();
                presenter.loadListOfNode(currentPath);

            } else {
                presenter.openNodeWithPlayer(currentNode);


            }
        }


    }

    @Override
    public void onRefresh() {
        presenter.loadListOfNode(currentPath);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share:
                shareFileWithFriends(currentNode);
                break;
            case R.id.btn_share_link:
                shareFileWithFriends(currentNode);
                break;
            case R.id.image_close:
                hideFileOptionsMenu();
                break;
            case R.id.btn_open_with:
                openFileWith(currentNode);
                break;
            default:
                break;
        }

    }
}
