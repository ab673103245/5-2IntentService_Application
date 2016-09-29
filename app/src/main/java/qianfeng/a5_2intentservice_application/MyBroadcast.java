package qianfeng.a5_2intentservice_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import java.io.File;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {


       // 在DownIntentService发送一个广播的时候，会有一个Intent，
        // 利用这个Intent传递消息，sendBroadcast（）是Context的方法，换言之，就是在四大组件里面都可以直接使用这个方法，而不是只在Activity内使用而已
        final String filepath = intent.getStringExtra("filepath"); // 从这里拿到Service那边传过来的intent,因为这个intent是从发送广播的那个组件的sendBroadcast这个方法传过来的

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("是否播放？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setDataAndType(Uri.fromFile(new File(filepath)), "video/*");
                        context.startActivity(intent1);
                        //context.startService() , 还可以利用这个上下文开启一个启动式的Service,如果Service已经启动过了，那就是执行里面startCommand()方法
                    }
                })
                .create();

        alertDialog.show();

    }
}
