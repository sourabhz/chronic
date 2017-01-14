package com.cholo.sourabhzalke.gotcha;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView scorelabel;
    private TextView startlabel;
    private ImageView black;
    private ImageView orange;
    private ImageView pink;
    private ImageView box;



    //Score
    private int score=0;
    //Size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //Position
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;

    //speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    //intialize Class
    private Handler handler=new Handler();
    private Timer timer= new Timer();
    private SoundPlayer sound;
    MediaPlayer player;

    //Status Check
    private boolean action_flg= false;
    private boolean start_flg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        sound= new SoundPlayer(this);

        //wiring up

        scorelabel=(TextView)findViewById(R.id.scorelabel);
        startlabel=(TextView)findViewById(R.id.startlabel);
        box=(ImageView)findViewById(R.id.box);
        black=(ImageView)findViewById(R.id.black);
        pink=(ImageView)findViewById(R.id.pink);
        orange=(ImageView)findViewById(R.id.orange);

        //Get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;

        //Now according to device screedwidth and screen height
        boxSpeed= Math.round(screenHeight/60F);   //1184 / 60 = 19.733.....= 20
        orangeSpeed=Math.round(screenWidth/60F);
        blackSpeed=Math.round(screenWidth/45F);
        pinkSpeed= Math.round(screenWidth/36F);

        //Move to out of screen
        orange.setX(-80.0f);
        orange.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);

        //Temporary
        boxY=500;
        scorelabel.setText("Score : 0");

    }
    public void changePos()
    {
        hitCheck();
        //Orange
        if(score<=200)
        orangeX-=orangeSpeed;
        else if(score<=500 && score >200)
            orangeX-=(1.25)*orangeSpeed;
        else
           orangeX-=(1.5)*orangeSpeed;

        if(orangeX<0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);


        //Black
        if(score<=200)
        blackX-=blackSpeed;
        else if(score>200 && score<=500)
            blackX-=(1.25)*blackSpeed;
        else
         blackX-=(1.5)*blackSpeed;

        if(blackX<0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        //pink
        if(score<=200)
        pinkX-=pinkSpeed;
        else if(score>200 && score<=500)
            pinkX-=(1.25)*pinkSpeed;
        else
        pinkX-=(1.5)*pinkSpeed;
        if(pinkX<0)
        {
            pinkX=screenWidth+5001;
            pinkY=(int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        //Move Box
        if(action_flg==true) {
            //touching

            boxY -= boxSpeed;

        }
        else{
            //Releasing
            boxY+=boxSpeed;
        }
        if(boxY<0)
            boxY=0;
        if(boxY>frameHeight-boxSize)
            boxY=frameHeight-boxSize;
        box.setY(boxY);


        scorelabel.setText("SCORE : " + score);

    }
    public void hitCheck()
    {
        //If the center of ball is in the box,it counts as a hit.


        //Orange


        int orangeCenterX= orangeX + orange.getWidth()/2;
        int orangeCenterY= orangeY + orange.getWidth()/2;

        if(0<=orangeCenterX && orangeCenterX<= boxSize && boxY<=orangeCenterY && orangeCenterY <=boxY+boxSize)
        {
            score+=10;
            orangeX=-10;
            sound.playHitSound();

        }

        //Pink


        int pinkCenterX= pinkX + pink.getWidth()/2;
        int pinkCenterY = pinkY + pink.getWidth()/2;


        if(0<=pinkCenterX && pinkCenterX<= boxSize && boxY<=pinkCenterY && pinkCenterY <=boxY+boxSize)
        {
            score+=30;
            pinkX=-10;
            sound.playHit2Sound();
        }

        //black
        int blackCenterX = blackX + black.getWidth()/2;
        int blackCenterY = blackY + black.getWidth()/2;

        if(0<=blackCenterX && blackCenterX<= boxSize && boxY<=blackCenterY && blackCenterY <=boxY+boxSize)
        {
            //StopTimer!!

            timer.cancel();
            timer=null;
            sound.playOverSound();
            player.stop();
            player.release();
            //show result
            Intent intent = new Intent(getApplicationContext(),result.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent me)
    {
        if(start_flg==false)
        {
            start_flg=true;

            //Why get frame height and box height here?
            //Because this is the place where interface actually build

            FrameLayout frame=(FrameLayout)findViewById(R.id.frame);
            frameHeight= frame.getHeight();

            boxY= (int)box.getY();
            boxSize=box.getHeight();
            player = MediaPlayer.create(this, R.raw.haha);
            player.setLooping(true); // Set looping
            player.setVolume(100,100);
            player.start();
            startlabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);
        }
        else{
        if(me.getAction()==MotionEvent.ACTION_DOWN)
        {
            action_flg=true;
        }
        else if(me.getAction()==MotionEvent.ACTION_UP) {
            action_flg = false;
        }
        }

        return true;
    }
    //Disable Return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if(event.getAction()== KeyEvent.ACTION_DOWN)
        {
            switch (event.getKeyCode())
            {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

}
