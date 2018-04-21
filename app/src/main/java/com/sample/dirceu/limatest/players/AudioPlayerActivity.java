package com.sample.dirceu.limatest.players;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sample.dirceu.limatest.R;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.services.IAudioPlayer;
import com.sample.dirceu.limatest.util.Utility;

import java.io.IOException;
import java.util.Objects;

import static com.sample.dirceu.limatest.players.MediaPlayerManager.getInstance;

public class AudioPlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener
        , SeekBar.OnSeekBarChangeListener, IAudioPlayer {

    private Toolbar toolbar;
    private SeekBar musicProgressBar;
    private TextView musicTitleLabel;
    private TextView musicCurrentDurationLabel;
    private TextView musicTotalDurationLabel;
    MediaPlayerManager mediaPlayerManager = getInstance();
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        attachUI();
        loadBundleData();

    }

    private void loadBundleData() {
        Bundle bundle = getIntent().getExtras();
        Node node = (Node) Objects.requireNonNull(bundle).getSerializable(Utility.AUDIO);
        musicTitleLabel.setText(node.getName());
        playSong(Objects.requireNonNull(node).getUrl());
        mediaPlayerManager.audioPlayer.setOnCompletionListener(this); // Important

    }

    private void attachUI() {
        musicProgressBar = findViewById(R.id.songProgressBar);
        musicTitleLabel = findViewById(R.id.text_music_title);
        musicTotalDurationLabel = findViewById(R.id.songTotalDurationLabel);
        musicCurrentDurationLabel = findViewById(R.id.songCurrentDurationLabel);


        musicProgressBar.setOnSeekBarChangeListener(this); // Important


    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void playSong(String songUrl) {
        if (mediaPlayerManager.audioPlayer == null) {
            mediaPlayerManager.audioPlayer = new MediaPlayer();
            mediaPlayerManager.audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } else {
            mediaPlayerManager.audioPlayer.reset();
        }

        try {
            mediaPlayerManager.audioPlayer.setDataSource(songUrl);
            mediaPlayerManager.audioPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayerManager.audioPlayer.start();

        musicProgressBar.setProgress(0);
        musicProgressBar.setMax(100);
        updateProgressBar();


    }

    private void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);


    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mediaPlayerManager.audioPlayer != null) {
                long totalDuration = mediaPlayerManager.audioPlayer.getDuration();
                long currentDuration = mediaPlayerManager.audioPlayer.getCurrentPosition();

                // Displaying Total Duration time
                musicTotalDurationLabel.setText(String.format("%s",
                        Utility.INSTANCE.milliSecondsToTimer(totalDuration)));
                // Displaying time completed playing
                musicCurrentDurationLabel.setText(String.format("%s",
                        Utility.INSTANCE.milliSecondsToTimer(currentDuration)));

                // Updating progress bar
                int progress = (int) (Utility.INSTANCE.getProgressPercentage(currentDuration, totalDuration));
                musicProgressBar.setProgress(progress);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);
            }

        }
    };

    @Override
    public void stopSong() {
        if (mediaPlayerManager.audioPlayer != null) {
            mediaPlayerManager.audioPlayer.reset();
            mediaPlayerManager.audioPlayer.release();
            mediaPlayerManager.audioPlayer = null;
        }

    }

    @Override
    public void pauseSong() {

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopSong();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSong();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
        stopSong();
    }
}
