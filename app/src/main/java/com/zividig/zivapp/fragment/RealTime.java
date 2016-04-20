package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zividig.zivapp.R;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 实时预览
 * Created by Administrator on 2016-03-18.
 */
public class RealTime extends Fragment {

    private static String[] mImgUrl = {"http://192.168.1.102:8080/picture/test_pic0.jpg",
                                        "http://192.168.1.102:8080/picture/test_pic1.jpg",
                                        "http://192.168.1.102:8080/picture/test_pic2.jpg",
                                        "http://192.168.1.102:8080/picture/test_pic3.jpg",
                                        "http://192.168.1.102:8080/picture/test_pic4.jpg",
    };
    private int i;
    private PhotoView photoView;
    private BitmapUtils bitmapUtils;
    private HttpUtils http;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_realtime,container,false);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("实时预览");

        BtnListener listener = new BtnListener();
        Button btRefresh = (Button) view.findViewById(R.id.bt_refresh); //图片刷新
        Button btDownImage = (Button) view.findViewById(R.id.bt_downImage); //图片下载
        btRefresh.setOnClickListener(listener);
        btDownImage.setOnClickListener(listener);

        bitmapUtils = new BitmapUtils(getContext());
        http = new HttpUtils();

        photoView = (PhotoView) view.findViewById(R.id.img);
        photoView.enable();

        getImage();

        return view;
    }

    /**
     * 获取图片
     */
    private void getImage(){

        bitmapUtils = new BitmapUtils(getContext());
        bitmapUtils.display(photoView,mImgUrl[i]);

        if (i >= 4){
            i = i%4;
            System.out.println("if语句运行" + i);
        }else {
            System.out.println("----" + i);
            i++;
        }

    }

    public void downImage(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            String target = Environment.getExternalStorageDirectory()  + "/" +getDateAndTime() + ".jpg";
            System.out.println(target);
            http.download(mImgUrl[i], target, false, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Toast.makeText(getContext(),"图片已下载",Toast.LENGTH_SHORT).show();
                    System.out.println(mImgUrl[i] + "---" + i);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(getContext(),"下载失败",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public String getDateAndTime(){
        SimpleDateFormat sDateFormat =  new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sDateFormat.format(new java.util.Date());
        System.out.println(date);
        return date;
    }

    class BtnListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_refresh:
                    getImage();
                    break;
                case R.id.bt_downImage:
                   downImage();
                    break;
            }
        }
    }
}
