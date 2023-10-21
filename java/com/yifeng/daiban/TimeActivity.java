package com.yifeng.daiban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TimeActivity extends AppCompatActivity {

    TimePicker tp1;
    Button bt1;
    int mm,h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        bt1 = (Button) findViewById(R.id.bt1);

        Intent it6 = getIntent();
        Bundle bd6 = it6.getExtras();
        h = Integer.parseInt(bd6.getString("h"));
        mm = Integer.parseInt(bd6.getString("mm"));
        hhmm();


        tp1 = (TimePicker) findViewById(R.id.tp1);
        tp1.setIs24HourView(true);

        tp1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mm = minute;
                hhmm();
            }
        });




        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it7 = new Intent(TimeActivity.this,MainActivity.class);
                Bundle bd7 = new Bundle();
                bd7.putCharSequence("h",""+h);
                bd7.putCharSequence("mm",""+mm);
                it7.putExtras(bd7);
                setResult(0x12,it7);
                finish();
            }
        });
    }

    //处理时分小于10的情况
    private void hhmm(){
        if (h < 10 && mm > 10){
            bt1.setText("0"+h+"时"+mm+"分 确定");
        }
        if (h > 10 && mm < 10){
            bt1.setText(h+"时0"+mm+"分 确定");
        }
        if (h < 10 && mm < 10){
            bt1.setText("0"+h+"时0"+mm+"分 确定");
        }else {
            bt1.setText(h+"时"+mm+"分 确定");
        }
    }

    //返回事件
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}