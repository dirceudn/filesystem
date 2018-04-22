package com.sample.dirceu.limatest.players;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sample.dirceu.limatest.R;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.util.Utility;

import java.util.Objects;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        loadBundleData();

    }

    private void loadBundleData() {

        Bundle bundle = getIntent().getExtras();
        Node videoNode = (Node) Objects.requireNonNull(bundle).getSerializable(Utility.VIDEO);
        VideoView videoView = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(Objects.requireNonNull(videoNode).getUrl()));
        videoView.start();
    }


}
