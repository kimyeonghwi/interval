package com.example.interval;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class Startinterval extends AppCompatActivity implements View.OnClickListener {


    int set_title, set, min_run, sec_run, min_rest, sec_rest;
    MyHandler handler = new MyHandler();
    TimerThread timerThread;
    boolean loop = true;
    boolean run = true;
    boolean is_first = false;
    boolean waiting_time;
    boolean run_time;
    boolean rest_time;
    boolean click_Btn = true;
    MediaPlayer mediaPlayer ;
    Intent intent;
    ConstraintLayout background, background2;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    TextView txt_min, txt_sec, txt_state, txt_set;
    ImageButton btn_pause, img_back, btn_restart, img_back2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startinterval);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sdfsdfdsf);

        img_back = findViewById(R.id.img_back);
        txt_set = findViewById(R.id.txt_set);
        txt_min = findViewById(R.id.txt_min_run_2);
        txt_sec = findViewById(R.id.txt_sec_run_2);
        txt_state = findViewById(R.id.text_state);
        btn_pause = findViewById(R.id.img_pause);
        background = findViewById(R.id.layout_back);
        background2 = findViewById(R.id.layout_back2);
        btn_restart = findViewById(R.id.img_restart);
        img_back2 = findViewById(R.id.img_back2);
        intent = getIntent();

        img_back.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_restart.setOnClickListener(this);
        img_back2.setOnClickListener(this);

        start();


    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            timerThread.interrupt();
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르시면 종료 됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void start() {
        reset_value();
        txt_min.setText("00");
        txt_sec.setText("05");
        txt_state.setText("대기");
        txt_set.setText(Integer.toString(set));
        timerThread = new TimerThread();
        timerThread.start();
        set_title = set;

    }

    //처음 시작하거나 나중에 리스타트 할떄도 사용하기 위하여 메소드로 만들어 놓았다.
    public void reset_value() {
        loop = true;
        run = true;
        run_time = false;
        rest_time = false;
        waiting_time = true;
        is_first = false;
        set = intent.getIntExtra("set", 0);
        min_run = intent.getIntExtra("min_run", 0);
        sec_run = intent.getIntExtra("sec_run", 0);
        min_rest = intent.getIntExtra("min_rest", 0);
        sec_rest = intent.getIntExtra("sec_rest", 0);
//        Log.d("hi" ,"처음 가져온값 런타임 분 :" + min_run);
//        Log.d("hi" ,"처음 가져온값 런타임 초 :" + sec_run);
//        Log.d("hi" ,"처음 가져온값 휴식 분 :" + min_rest);
//        Log.d("hi" ,"처음 가져온값 휴식 초 :" + sec_rest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_back2:

            case R.id.img_back:
                timerThread.interrupt();
                finish();
                break;

            case R.id.img_pause:

                if (click_Btn) {
                    run = false;
                    click_Btn = false;
                    btn_pause.setImageResource(R.drawable.ic_play);
                } else {
                    btn_pause.setImageResource(R.drawable.ic_pause);
                    run = true;
                    click_Btn = true;
                }
                break;

            case R.id.img_restart:
                background.setVisibility(View.VISIBLE);
                background2.setVisibility(View.GONE);
                start();
                break;

        }
    }

    class TimerThread extends Thread {

        @Override
        public void run() {
            try {
                for (int i = 0; i < set; i++) {
                    //모든값들 초기화 시키기
                    reset_value();
                    if (is_first) {
                        waiting_time = false;
                        run_time = true;
                    }
                    int waiting = 5;
                    while (loop) {
                        if (run) { // 중간에 정지를 위한
                           //일단1초
                            Thread.sleep(1000);
                            // 첫 if 시작부분
                            if (waiting_time) {
                                Log.d("hi", "waiting_time:" + waiting);
                                Message message = handler.obtainMessage();
                                message.what = 1;
                                message.arg1 = waiting;
                                handler.sendMessage(message);
                                waiting--;
                                if (waiting == 0) {
                                    Thread.sleep(1000);
                                    waiting_time = false;
                                    run_time = true;
                                    is_first = true;
                                }
                            }
                            if (run_time) {
                                Message message = handler.obtainMessage();
                                message.what = 2;
                                message.arg1 = sec_run;
                                message.arg2 = min_run;
                                handler.sendMessage(message);
                                if (sec_run == 0 && min_run == 0) {
                                    Log.d("hi", "=======운동끝=======");
                                    run_time = false;
                                    rest_time = true;
                                }
                                if (sec_run != 0) {
                                    Log.d("hi", "run_time:" + min_run + ":" + sec_run);
                                    sec_run--;
                                } else if (min_run != 0) {
                                    Log.d("hi", "run_time:" + min_run + ":" + sec_run);
                                    sec_run = 60;
                                    sec_run--;
                                    min_run--;
                                }
                            }
                            if (rest_time) {

                                Message message = handler.obtainMessage();
                                message.what = 3;
                                message.arg1 = sec_rest;
                                message.arg2 = min_rest;
                                handler.sendMessage(message);

                                if (sec_rest == 0 && min_rest == 0) {
                                    message = new Message();
                                    message.what = 4;
                                    handler.sendMessage(message);
                                    Log.d("hi", "=======휴식끝=======");
                                    set_title--;
                                    loop = false;
                                }
                                if (sec_rest != 0) {
                                    Log.d("hi", "rest_time:" + min_rest + ":" + sec_rest);
                                    sec_rest--;

                                } else if (min_rest != 0) {
                                    Log.d("hi", "rest_time:" + min_rest + ":" + sec_rest);
                                    sec_rest = 60;
                                    sec_rest--;
                                    min_rest--;
                                }

                            }
                        }
                    }
                }

                Message message = handler.obtainMessage();
                message.what = 5;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                Log.d("hi" ,"스레드 뒤짐 ");
            }
        }

    }

    //핸들러 클래스
    class MyHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            //미디어플레이어로 한번만 소리 나게 해주기도 해야한다.... 이건 물어봐야할거 같다... 슈발

            switch (msg.what) {

                case 1:
                    mediaPlayer.start();
                    background.setBackgroundResource(R.color.wait);
                    txt_state.setText("대기");
                    txt_sec.setText("0" + msg.arg1);
                    break;


                case 2:
                    if (msg.what == 2) {
                        background.setBackgroundResource(R.color.run);
                        txt_state.setText("운동");
                        if (msg.arg1 <= 9) {
                            txt_sec.setText("0" + msg.arg1);
                        } else {
                            txt_sec.setText(Integer.toString(msg.arg1));
                        }
                        if (msg.arg2 <= 9) {
                            txt_min.setText("0" + msg.arg2);
                        } else {
                            txt_min.setText(Integer.toString(msg.arg2));
                        }

                        if (msg.arg1 <= 5) {
                            mediaPlayer.start();
                        }

                        break;
                    }

                case 3:
                    if (msg.what == 3) {
                        background.setBackgroundResource(R.color.rest);
                        txt_state.setText("휴식");
                        if (msg.arg1 <= 9) {
                            txt_sec.setText("0" + msg.arg1);
                        } else {
                            txt_sec.setText(Integer.toString(msg.arg1));
                        }
                        if (msg.arg2 <= 9) {
                            txt_min.setText("0" + msg.arg2);
                        } else {
                            txt_min.setText(Integer.toString(msg.arg2));
                        }

                        if (msg.arg1 <= 5 && msg.arg1 > 0) {
                            mediaPlayer.start();
                        }


                    }
                    break;
                case 4:
                    txt_set.setText(Integer.toString(set_title));

                    break;
                case 5:
                    mediaPlayer.start();
                    background.setBackgroundResource(R.color.wait);
                    background.setVisibility(View.GONE);
                    background2.setVisibility(View.VISIBLE);
            }


        }
    }
}


