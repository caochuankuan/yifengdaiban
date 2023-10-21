package com.yifeng.daiban;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class DateActivity extends Activity {

    Button bt1;
    int y,m,d;
    DatePicker dp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        bt1 = (Button) findViewById(R.id.bt1);

        Intent it2 = getIntent();
        Bundle bd2 = it2.getExtras();
        y = Integer.parseInt(bd2.getString("y"));
        m = Integer.parseInt(bd2.getString("m"))-1;
        d = Integer.parseInt(bd2.getString("d"));
        bt1.setText(y+"年"+(m+1)+"月"+d+"日 确定");

//        Toast.makeText(this, ""+y+m+d, Toast.LENGTH_SHORT).show();

        dp1 = (DatePicker) findViewById(R.id.dp1);
        dp1.init(y, m, d, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear;
                d = dayOfMonth;
                bt1.setText(y+"年"+(m+1)+"月"+d+"日 确定");
            }
        });



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it3 = new Intent(DateActivity.this,MainActivity.class);
                Bundle bd3 = new Bundle();
                bd3.putCharSequence("y",""+y);
                bd3.putCharSequence("m",""+(m+1));
                bd3.putCharSequence("d",""+d);
                it3.putExtras(bd3);
                setResult(0x11,it3);
                finish();
            }
        });

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