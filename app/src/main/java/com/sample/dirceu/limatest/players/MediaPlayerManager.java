package com.sample.dirceu.limatest.players;

import android.media.MediaPlayer;

public class MediaPlayerManager {

    MediaPlayer audioPlayer;
    private static MediaPlayerManager instance = null;

    private MediaPlayerManager() {
    }

     static MediaPlayerManager getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerManager.class) {
                if (instance == null) {
                    instance = new MediaPlayerManager();
                }
            }
        }

        return instance;
    }
}
