package com.zividig.zivapp.getui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.zividig.zivapp.MainActivity;
import com.zividig.zivapp.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 个推广播接收类
 * Created by Administrator on 2016-04-26.
 */
public class PushReceiver extends BroadcastReceiver {



    //透传过来的信息
    private String title;
    private String content;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("广播接收类");

        Bundle bundle = intent.getExtras();

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:

                String cid = bundle.getString("clientid");
                System.out.println("cid是:" + cid);
                // TODO:处理cid返回
                break;
            case PushConsts.GET_MSG_DATA:

                String taskid = bundle.getString("taskid");
                System.out.println("----taskid" + taskid);
                String messageid = bundle.getString("messageid");
                System.out.println("----messageid" + messageid);

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    // TODO:接收处理透传（payload）数据
                    System.out.println(data);
                    try {
                        JSONObject json = new JSONObject(data);
                        title = json.getString("title");
                        content = json.getString("content");
                        creatNotification(context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                break;
            default:
                break;
        }
    }

    /**
     * 创建通知栏
     */
    public void creatNotification(Context context){
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
            mBuilder.setContentTitle(title)  //获取标题
                    .setContentText(content) //获取内容
                    .setSmallIcon(R.mipmap.zividigxh) //获取图标
                    .setWhen(System.currentTimeMillis()) //设置时间
                    .setDefaults(Notification.DEFAULT_VIBRATE) //通知的方式
                    .setContentIntent(pendingIntent); //点击后启动应用
        }
        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0,notification);

    }
}
