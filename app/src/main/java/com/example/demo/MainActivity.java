package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.yzq.zxinglibrary.android.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*
    private static final  String TAG="MainActivity";
    private  int REQUEST_CODE_SCAN=111;
   二维码扫描
   */
    private Button bt_sc;
    //用于初始化界面展示的view
    private void initView(){
        bt_sc=(Button)findViewById(R.id.button1_1);
    }

    //设置扫描二维码事件
    private  void setListener(){
        bt_sc.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }
    public void onClick(View v){
        //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连e接两个不同的组件。
        //在存放资源代码的文件夹下下，
        Intent i = new Intent(MainActivity.this ,CaptureActivity.class);
        //启动
        startActivity(i);
    }
}
