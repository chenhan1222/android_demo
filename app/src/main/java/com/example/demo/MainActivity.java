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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final  String TAG="MainActivity";
    /*
        private static final  String TAG="MainActivity";
        private  int REQUEST_CODE_SCAN=111;
       二维码扫描
       */
    private Button bt_sc;
    private int PHOTO_REQUEST_SAOYISAO = 1;
    private int REQUEST_CODE_SCAN = 111;

    //用于初始化界面展示的view
    private void initView() {
        bt_sc = (Button) findViewById(R.id.button1_1);
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


    /**
     * 重写申请权限操作返回值的方法
     **/

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 权限申请成功，扫一扫

                    Intent intent = new Intent(MainActivity.this,

                            CaptureActivity.class);

                    startActivityForResult(intent, REQUEST_CODE_SCAN);

                } else {

                    Toast.makeText(this, "无相机调用权限，扫一扫功能无法使用，", Toast.LENGTH_SHORT).show();

                }
        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            // 扫一扫返回值

            case 1:

                if (resultCode == RESULT_OK) {

                    String content = intent.getStringExtra(Constant.CODED_CONTENT);

                    Log.d(TAG, "扫一扫返回成功！扫码结果为：" + content);

                }

                break;

            default:

        }

    }
*/
    public void onClick(View v) {
        //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连e接两个不同的组件。
        //在存放资源代码的文件夹下

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, PHOTO_REQUEST_SAOYISAO);

        } else {
            Intent i = new Intent(MainActivity.this, CaptureActivity.class);
            //启动
            startActivity(i);
        }
    }
}
