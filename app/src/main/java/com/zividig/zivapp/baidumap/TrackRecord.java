package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zividig.zivapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 轨迹记录
 * Created by Administrator on 2016-04-06.
 */
public class TrackRecord extends Activity implements View.OnClickListener{

    private TextView start;
    private TextView end;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String dateString;
    private String timeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_track_record);

        // 标题
        TextView txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText("轨迹查询");

        //返回按钮
        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //开始日期
        ImageButton startDate = (ImageButton) findViewById(R.id.imbtn_start_date_picker);
        ImageButton startTime = (ImageButton) findViewById(R.id.imbtn_start_time_picker);
        start = (TextView) findViewById(R.id.tv_start_time);

        startDate.setOnClickListener(this);
        startTime.setOnClickListener(this);

        //结束日期
        ImageButton endDate = (ImageButton) findViewById(R.id.imbtn_end_date_picker);
        ImageButton endtTime = (ImageButton) findViewById(R.id.imbtn_end_time_picker);
        end = (TextView) findViewById(R.id.tv_end_time);

        endDate.setOnClickListener(this);
        endtTime.setOnClickListener(this);

        initcalendar();
    }

    public void initcalendar(){
        //初始化Calendar日历对象
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        Date myDate=new Date(); //获取当前日期Date对象
        calendar.setTime(myDate);////为Calendar对象设置时间为当前日期

        year=calendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=calendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=calendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

    }

    public void createDatePicker(){

        DatePickerDialog dateDialog = new DatePickerDialog(TrackRecord.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateString = year + "/" + (monthOfYear+1)  + "/" + dayOfMonth;
                start.setText(dateString);
            }
        },year,month,day);

        dateDialog.show();

    }

    public void createTimePicker(){
        TimePickerDialog timeDialog = new TimePickerDialog(TrackRecord.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String myMinute;
                if (minute < 10){
                   myMinute = "0" + minute;
                }else {
                    myMinute = minute + "";
                }
                timeString = "  " + hourOfDay + ":" + myMinute ;
                start.setText(dateString + timeString);
                System.out.println(minute);
            }
        },hour,minute,true);

        timeDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imbtn_start_date_picker:
                System.out.println("开始日期被点击了");
                createDatePicker();
                break;
            case R.id.imbtn_start_time_picker:
                System.out.println("开始时间被点击了");
                createTimePicker();
                break;
            case R.id.imbtn_end_date_picker:
                System.out.println("结束日期被点击了");
                createDatePicker();
                break;
            case R.id.imbtn_end_time_picker:
                System.out.println("结束时间被点击了");
                createTimePicker();
                break;
        }
    }
}
