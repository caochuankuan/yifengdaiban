package com.yifeng.daiban;

//1.0
//正式发布
//1.1
//加入返回逻辑，更新logo,增加背景
//1.2
//加入启动页面

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    LinearLayout ll1,ll2,ll3,ll5;
    TextView tv1,tv_y,tv_m,tv_d,tv_h,tv_mm,tv_s;
    Switch sw1;
    Button bt6,bt7,bt8;
    EditText et1;
    int a=0;
    private Handler handler = new Handler();
    public Boolean ischecked = false;
    private MediaPlayer mediaPlayer;
    public Toast mytoast;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMediaPlayer();
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        tv1 = (TextView) ll1.getChildAt(0);
        sw1 = (Switch) ll1.getChildAt(1);
        tv_y = (TextView) findViewById(R.id.year);
        tv_m = (TextView) findViewById(R.id.month);
        tv_d = (TextView) findViewById(R.id.day);
        tv_h = (TextView) findViewById(R.id.hour);
        tv_mm = (TextView) findViewById(R.id.min);
        bt6 = (Button) findViewById(R.id.bt6);
        bt7 = (Button) findViewById(R.id.bt7);
        bt8 = (Button) findViewById(R.id.bt8);
        et1 = (EditText) findViewById(R.id.et1);

        //初始隐藏
        novible();

        //switch开关
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String op = "已开启"+tv1.getText();
                    setToast(op);
                    isvible();//显示
                    bt8.setVisibility(View.INVISIBLE);

                }else{
                    String cl = "已关闭"+tv1.getText();
                    setToast(cl);
                    novible();//隐藏
                    bt8.setVisibility(View.VISIBLE);
                }
            }
        });



        //日期点击监听，将当前view的时间传到DateActivity
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it1 = new Intent(MainActivity.this,DateActivity.class);
                Bundle bd1 = new Bundle();
                bd1.putCharSequence("y",tv_y.getText());
                bd1.putCharSequence("m",tv_m.getText());
                bd1.putCharSequence("d",tv_d.getText());
                it1.putExtras(bd1);
                startActivityForResult(it1,0x11);
            }
        });

        //时间点击监听，将当前view的时间传到TimeActivity
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it5 = new Intent(MainActivity.this,TimeActivity.class);
                Bundle bd5 = new Bundle();
                bd5.putCharSequence("h",tv_h.getText());
                bd5.putCharSequence("mm",tv_mm.getText());
                it5.putExtras(bd5);
                startActivityForResult(it5,0x12);
            }
        });

        //获取当前时间显示在对应的view上
        Calendar cal = Calendar.getInstance();
        tv_y.setText(""+(cal.get(Calendar.YEAR)));
        tv_m.setText(""+(cal.get(Calendar.MONTH)+1));
        tv_d.setText(""+cal.get(Calendar.DAY_OF_MONTH));
        //处理小时位数
        if (cal.get(Calendar.HOUR_OF_DAY)<10){
            tv_h.setText("0"+cal.get(Calendar.HOUR_OF_DAY));
        }else {
            tv_h.setText(""+cal.get(Calendar.HOUR_OF_DAY));
        }
        //处理分钟位数
        if (cal.get(Calendar.MINUTE)<10){
            tv_mm.setText("0"+cal.get(Calendar.MINUTE));
        }else{
            tv_mm.setText(""+cal.get(Calendar.MINUTE));
        }


        //让函数每秒执行一遍
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this,1000);
                if (ischecked){
                    String dateStr = ""+tv_y.getText()+"-"+tv_m.getText()+"-"+tv_d.getText()+" "+tv_h.getText()+":"+tv_mm.getText();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        Date setDate = formatter.parse(dateStr);
                        Date currentDate = new Date();
                        if (currentDate.after(setDate)){
                            if (et1.getText() == null || et1.equals("") || et1.length() == 0){
                                et1.setText("空");
                            }
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("您有一个待办")
                                    .setMessage("时间："+tv_y.getText()+"年"+tv_m.getText()+"月"+tv_d.getText()+"日"+tv_h.getText()
                                            +"时"+tv_mm.getText()+"分\n内容："+et1.getText())
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            pausePlay();
                                            ischecked = false;
                                        }
                                    }).show();
                            startPlay();
                            String tishi = "设置的待办："+et1.getText()+"时间已到，\n请回到遇见待办操作";
                            setToast(tishi);
                            ischecked =false;
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        handler.post(runnable);

        //确定按钮监听事件
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked =true;
                String t = "已成功设置待办";
                setToast(t);
            }
        });
        //取消按钮监听事件
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ischecked =false;
                sw1.setChecked(false);
                novible();
                String c = "已取消待办";
                setToast(c);
            }
        });

        //新建按钮监听事件
        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw1.setChecked(true);
                bt8.setVisibility(View.INVISIBLE);
            }
        });

    }

    //初始化MediaPlayer
    private void initMediaPlayer(){
        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            //设置音频播放完毕后的回调函数
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        });
    }

    //开始播放音乐
    private void startPlay(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    //暂停播放音乐
    private void pausePlay(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //释放音频资源
    private void releaseMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //显示相应view
    private void isvible(){

        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        ll5.setVisibility(View.VISIBLE);
        bt6.setVisibility(View.VISIBLE);
        bt7.setVisibility(View.VISIBLE);
    }

    //隐藏相应view
    private void novible(){
        ll2.setVisibility(View.INVISIBLE);
        ll3.setVisibility(View.INVISIBLE);
        ll5.setVisibility(View.INVISIBLE);
        bt6.setVisibility(View.INVISIBLE);
        bt7.setVisibility(View.INVISIBLE);
    }

    //获取日期时间activity设置的年月日时分并显示出来，requestCode要resultCode一致
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bd4 = data.getExtras();
        if (requestCode == 0x11 && resultCode == 0x11){
            tv_y.setText(bd4.getString("y"));
            tv_m.setText(bd4.getString("m"));
            tv_d.setText(bd4.getString("d"));
        }
        if (requestCode == 0x12 && resultCode == 0x12){
            if (bd4.getString("h").length() == 1){
                tv_h.setText("0"+bd4.getString("h"));
            }else {
                tv_h.setText(bd4.getString("h"));
            }
            if (bd4.getString("mm").length() == 1){
                tv_mm.setText("0"+bd4.getString("mm"));
            }else {
                tv_mm.setText(bd4.getString("mm"));
            }
        }
    }

    //解决toast延迟问题
    public void setToast(String sss){
        if (mytoast != null){
            mytoast.setText(sss);
            mytoast.setDuration(Toast.LENGTH_SHORT);
        }else {
            mytoast = Toast.makeText(MainActivity.this, sss, Toast.LENGTH_SHORT);
        }
        mytoast.setGravity(Gravity.TOP,0,30);
        mytoast.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("确定退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("取消", null).show();
    }
}