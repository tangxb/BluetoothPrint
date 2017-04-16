package com.yq.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.yq.yqwater.R;


/**
 * Created by gbh on 16/11/9.
 */

public class PlayRing {
    /**
     * 播放音乐
     */
    public static synchronized void ring(Context context) {
        // TODO Auto-generated method stub
        try {
            MediaPlayer player = MediaPlayer.create(context, R.raw.error);
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
