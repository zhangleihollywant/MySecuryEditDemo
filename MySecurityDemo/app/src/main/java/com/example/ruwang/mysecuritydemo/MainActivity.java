package com.example.ruwang.mysecuritydemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SecurityView.MyEditListener {

    private SecurityView mSecurityView;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.argree);
        mSecurityView = (SecurityView) findViewById(R.id.edit);

        mSecurityView.setListener(this);
    }

    @Override
    public void inputComplete() {
        Toast.makeText(this, "验证码是：" + mSecurityView.getInputContent().toString(), Toast.LENGTH_SHORT).show();
        if (!mSecurityView.getInputContent().equals("1234")) {
            mTextView.setText("验证码错误");
            mTextView.setTextColor(Color.RED);
        }
    }

    @Override
    public void deleteListener(boolean isDelete) {
        if (isDelete) {
            mTextView.setText("输入验证码表示同意《用户协议》");
            mTextView.setTextColor(Color.BLACK);
        }
    }
}
