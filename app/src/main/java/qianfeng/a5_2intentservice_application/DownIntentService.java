package qianfeng.a5_2intentservice_application;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownIntentService extends IntentService {

    private static final String VIDEOURL = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";

    private static final String FILEPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+ File.separator + "123.flv";
    private NotificationManager manager;
    private Notification.Builder builder;


    public DownIntentService() {
        super("DownIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //1.创建Notification
        builder = new Notification.Builder(this)
         // setSmallIcon()这个方法一定要有，否则通知栏的这个通知就显示不出来了!
        .setSmallIcon(R.drawable.a7u)
        .setContentTitle("正在下载...")
        .setContentText("已下载0%");

        Notification notification = builder.build();

        //2.使用Notification管理器显示Notification
        manager = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));

       //第一个参数表示Notification的唯一标识符
        manager.notify(1,notification);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

//        downVideo();

        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setDataAndType(Uri.fromFile(new File(FILEPATH)),"video/*");

        builder.setContentTitle("下载完成");

        // 第二个参数代表：请求码。
        // 第四个参数代表：该PendingIntent的有效次数为 1 次 ,点击1次之后就无效
        PendingIntent intent2 = PendingIntent.getActivity(this, 1, intent1, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(intent2); //最关键的通知栏点击播放事件。 这个方法可以处理 点击通知的播放视频事件!!

        builder.setAutoCancel(true); // 设置这个为true，代表点击一次之后，该通知就消失在通知栏中
        // 最后一步，更新通知栏
        manager.notify(1,builder.build());


        // 下载完成后，广播通知打开这个视频,
        // 下载完成后，发送广播
        Intent play = new Intent("play");
        play.putExtra("filepath",FILEPATH);
        sendBroadcast(play);

    }

    private void downVideo() {
        HttpURLConnection con = null;
        try {
            URL url = new URL(VIDEOURL);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            if(con.getResponseCode() == 200)
            {
                FileOutputStream fos = new FileOutputStream(new File(FILEPATH));
                InputStream inputStream = con.getInputStream();
                int len = 0;
                byte[] bytes = new byte[1024];
                int contentLength = con.getContentLength();
                int currentLength = 0;
                while((len = inputStream.read(bytes))!=-1)
                {

                    currentLength += len;

                    // 第一个参数：进度条的最大值
                    // 第二个参数：进度条的当前值
                    // 第三个参数：是否使用模糊进度条，false为不使用
                    builder.setProgress(contentLength,currentLength,false); // 这里是设置并显示一个精确进度条
                    fos.write(bytes,0,len);
                    fos.flush();
                    Notification build = builder
                            .setContentText("已下载:" + (int)(currentLength*1.0/contentLength*100) + "%").build();
                    manager.notify(1, build);// 实时在子线程中更新通知，利用系统服务的 通知管理器的notify方法，
                                    // 在子线程中更新RemoveView，这个RemoveView并不是继承自View，是少数几个可以在子线程中更新的器件。

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
