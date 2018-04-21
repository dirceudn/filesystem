package com.sample.dirceu.limatest.players;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sample.dirceu.limatest.R;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.util.Utility;

import java.util.Objects;

public class ImagePlayerActivity extends AppCompatActivity {

    private ImageView imageView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        attachUI();
        loadBundleData();

    }

    private void loadBundleData() {
        Bundle bundle = getIntent().getExtras();

        Node imageNode = (Node) Objects.requireNonNull(bundle).getSerializable(Utility.IMAGE);

        //TODO implements the GlideImageLoader to handle with large images, progress and cache.
        Glide.with(this)
                .load(Objects.requireNonNull(imageNode).getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        setToolBarTitle(imageNode.getName());

    }

    private void setToolBarTitle(String name) {
        toolbar.setTitle(name);
    }

    private void attachUI() {
        imageView = findViewById(R.id.image_photo);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

}
