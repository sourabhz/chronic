package com.cholo.sourabhzalke.gotcha;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by sourabhzalke on 29/12/16.
 */

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;

    private static SoundPool soundPool;
    private static int hitSound;
    private static int hit2Sound;
    private static int overSound;
    private static int coolSound;
    private static int bellSound;
    private static int trySound;
    public SoundPlayer(Context context) {

        //SoundPool is deprecated in API level 21. (Lollipop)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            //SoundPool (int maxStreams, int streamType, int srcQuality)
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
            hitSound = soundPool.load(context, R.raw.hit, 1);
            hit2Sound = soundPool.load(context,R.raw.hit2,1);
            overSound = soundPool.load(context, R.raw.over, 1);
            bellSound= soundPool.load(context,R.raw.bell,1);
            trySound= soundPool.load(context,R.raw.tryagain,1);


    }

    public void playHitSound()
    {
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playOverSound()
    {
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playCoolSound() {soundPool.play(coolSound,1.0f,1.0f,1,0,1.0f);}
    public void playHit2Sound() {soundPool.play(hit2Sound,1.0f,1.0f,1,0,1.0f);}
    public void playBellSound() {soundPool.play(bellSound,1.0f,1.0f,1,0,1.0f);}
    public void playTrySound() {soundPool.play(trySound,1.0f,1.0f,1,0,1.0f);}



}
