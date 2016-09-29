package qianfeng.a5_2intentservice_application;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() { // 给一个无参的构造方法，这样在xml中注册时，就不会报错
        super("");
    }



    // 以下是生命周期方法
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("google-my:", "onCreate: " + Thread.currentThread());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("google-my:", "onStartCommand: " + Thread.currentThread());
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("google-my:", "onStart: " + Thread.currentThread());
    }

  
    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 10; i++) {
            SystemClock.sleep(1000);
            Log.d("google-my:", "onHandleIntent: " + Thread.currentThread() + ";" + i);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("google-my:", "onDestroy: " + Thread.currentThread());
    }
}
