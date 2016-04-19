package com.zividig.zivapp.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.zividig.zivapp.R;
import com.zividig.zivapp.view.ToggleButton;

/**
 * 设置选项细节
 * Created by Administrator on 2016-04-15.
 */
public class SettingItem extends Activity {

    ToggleButton toggleBtn; //滑动开关
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_item);

        toggleBtn = (ToggleButton) findViewById(R.id.toggle_button);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean auto_update = sp.getBoolean("auto_update", true);

        if (auto_update){
            System.out.println("自动更新开启");
            toggleBtn.setToggleOn();
        }else {
            System.out.println("自动更新默认关闭");
            toggleBtn.setToggleOff();
        }

        toggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {

                if (on){
                    System.out.println("打开了");
                    sp.edit().putBoolean("auto_update",true).apply();
                }else {
                    System.out.println("关闭了");
                    sp.edit().putBoolean("auto_update",false).apply();
                }
            }
        });
    }
}
