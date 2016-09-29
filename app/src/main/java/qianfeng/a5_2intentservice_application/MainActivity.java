package qianfeng.a5_2intentservice_application;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent service;
    private MyBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Intent(this,MyIntentService.class);



    }

    @Override
    protected void onStart() {
        super.onStart();
        // 在onStart方法中，注册一个广播
        // 在MainActivity的onCreate方法中，一开始就动态注册一个广播，用于接收Service里传过来的广播，再进行处理

        IntentFilter filter = new IntentFilter();
        filter.addAction("play");
        broadcast = new MyBroadcast();
        registerReceiver(broadcast,filter);

    }

    public void start(View view) {
        startService(service);
    }


    public void downLoad(View view) {
        startService(new Intent(this,DownIntentService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}
