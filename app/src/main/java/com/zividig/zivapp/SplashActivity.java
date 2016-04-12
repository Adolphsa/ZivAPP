package com.zividig.zivapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

/**
 * 闪屏页面
 * Created by Administrator on 2016-04-12.
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },2000);

    }

    /**
     * 得到版本名称和版本号
     * @return
     */
    public void getVersion(){

        PackageManager packageManager = getPackageManager();
        //获取包的信息
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(getPackageName(), 0);
        int versionCode = packageInfo.versionCode; //版本号
        String versionName = packageInfo.versionName; //版本名称
        System.out.println(String.valueOf(versionCode) + versionName);

    }
}
