package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.job.JobScheduler;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private int REQUEST_CODE_SCAN = 111;
    private Button bt_sc;
    private EditText occupyTime;
//    private int PHOTO_REQUEST_SAOYISAO = 1;

    //用于初始化界面展示的view
    private void initView() {
        bt_sc = (Button) findViewById(R.id.button1_1);
        occupyTime = (EditText) findViewById(R.id.text_time);
    }

    //设置扫描二维码事件
    private void setListener() {
        bt_sc.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    @Override
    public void onClick(View v) {
        AndPermission.with(this)
                .permission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        //跳转到CaptureActivity页面
                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                        ZxingConfig config = new ZxingConfig();
                        config.setPlayBeep(true);
                        config.setShake(true);
                        //将此页面设置的config对象传递给intent_zxing_config静态常量，CapureActivity中使用
                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                        startActivityForResult(intent, REQUEST_CODE_SCAN);
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Uri packageURI = Uri.parse("package:" + MainActivity.this.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "没有权限扫描哟", Toast.LENGTH_LONG).show();
                    }
                })
                .start();
    }

    //处理扫描的结果，需要多线程处理网络请求，将网络请求新建一个线程
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                final String seat = data.getStringExtra(Constant.CODED_CONTENT);
                final String time = occupyTime.getText().toString();
                try {
                    Log.e(TAG, "seat------" + seat);
                    Toast.makeText(MainActivity.this, "扫描结果" + seat, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, seat, Toast.LENGTH_SHORT).show();
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        String response;
                        response = Send.checkStatus("http://59.46.220.242:9092/system/seat", seat, time);
                        if (response.equals("ok")) {
                            Intent intent = new Intent();
                            intent.putExtra("seat", seat);
                            intent.putExtra("time", time);
                            intent.setClass(MainActivity.this, SuccessActivity.class);
                            MainActivity.this.startActivity(intent);
                            MainActivity.this.finish();
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, FailedActivity.class);
                            MainActivity.this.startActivity(intent);
                        }
                    }
                };
            }
        }
    }
}
