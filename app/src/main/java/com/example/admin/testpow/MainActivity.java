package com.example.admin.testpow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化view
        initView();
        checkWindowFloatPermisson(MainActivity.this);
    }
    //初始化view
    private void initView() {
        mFloatingButton = (Button) findViewById(R.id.floating_btn);
        mFloatingButton.setOnClickListener(this);
    }

    public void startFloatingButtonService(View view) {
        Log.e("TAG", "测试流程");
        if (FloatingServiceButton.isStarted) {
            Log.e("TAG", "测试流程2");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                Log.e("TAG", "测试流程3");
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                Log.e("TAG", "测试流程4");
                startService(new Intent(MainActivity.this, FloatingServiceButton.class));
            }
        } else {
            startService(new Intent(MainActivity.this, FloatingServiceButton.class));
        }

    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Log.e("TAG", "测试流程5");
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("TAG", "测试流程6");
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                startService(new Intent(MainActivity.this, FloatingServiceButton.class));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floating_btn :
                startFloatingButtonService(v);
                break;
        }
    }

    //查询悬浮窗权限是否已开启
    public static final int OVERLAY_PERMISSION_REQ_CODE = 10001;
    public static void checkWindowFloatPermisson(final Activity context){
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_MyDialog);
            AlertDialog dialog = builder.setCancelable(false)
                    .setTitle("需要申请悬浮窗权限")
                    .setMessage("悬浮窗权限未开启，将影响对讲功能使用，请先开启悬浮窗")
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + context.getPackageName()));
                            context.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
    }
}
