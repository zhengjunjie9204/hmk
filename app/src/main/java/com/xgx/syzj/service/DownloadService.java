package com.xgx.syzj.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Response;
import com.xgx.syzj.R;
import com.xgx.syzj.datamodel.UserDataModel;


public class DownloadService extends Service {

    private Notification notification;
    private RemoteViews contentView;
    private NotificationManager notificationManager;
    private long lasttime=0;


    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String url = intent.getStringExtra("url");
            createNotification();
            Response.ProgressListener listener = new Response.ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    long currentTime=System.currentTimeMillis();
                    if (currentTime-lasttime>1000||transferredBytes==totalSize){
                        lasttime=currentTime;
                        notifyNotification(transferredBytes,totalSize);
                    }
                }
            };
            UserDataModel.downloadApk(url, listener);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(R.mipmap.ic_launcher,//应用的图标
                "安装包正在下载...", System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setProgressBar(R.id.progress, 100, 0, false);
        contentView.setTextViewText(R.id.tv_progress, "0%");
        notification.contentView = contentView;

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(notification==null){
            Log.e("==","notification is null");
        }else if(notificationManager==null){
            Log.e("==","notificationManager is null");
        }else {
            notificationManager.notify(R.layout.notification_item, notification);
        }
    }

    private void notifyNotification(long percent, long length) {

        contentView.setTextViewText(R.id.tv_progress, (percent * 100 / length) + "%");
        contentView.setProgressBar(R.id.progress, (int) length, (int) percent, false);
        notificationManager.notify(R.layout.notification_item, notification);
        if(percent==length){
            notificationManager.cancelAll();
        }
    }

}
