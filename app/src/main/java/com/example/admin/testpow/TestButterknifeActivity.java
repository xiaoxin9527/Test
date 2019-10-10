package com.example.admin.testpow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：${kangfeimin} on 2019/9/16
 * 邮箱：498708074@qq.com
 */
public class TestButterknifeActivity extends Activity {

    @BindView(R.id.butto1)
    Button butto1;
    @BindView(R.id.butto4)
    Button butto4;
    @BindView(R.id.butto3)
    Button butto3;
    @BindView(R.id.butto2)
    Button butto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testbutterknife);
        ButterKnife.bind(this);

    }
}
