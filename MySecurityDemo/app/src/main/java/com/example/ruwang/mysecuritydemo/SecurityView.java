package com.example.ruwang.mysecuritydemo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * <b>Create Date:</b> 17/3/28<br>
 * <b>Author:</b> Zhanglei<br>
 * <b>Description:</b> <br>
 */

public class SecurityView extends RelativeLayout {

    private EditText mEditText;
    private TextView[] mTextViews;

    private StringBuffer mStringBuffer = new StringBuffer();
    private int count = 4;
    private String inputContent;
    private MyEditListener mListener;

    public SecurityView(Context context) {
        this(context, null);
    }

    public SecurityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecurityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextViews = new TextView[4];
        View.inflate(context, R.layout.security, this);
        mEditText = (EditText) findViewById(R.id.item_edittext);
        mTextViews[0] = (TextView) findViewById(R.id.text1);
        mTextViews[1] = (TextView) findViewById(R.id.text2);
        mTextViews[2] = (TextView) findViewById(R.id.text3);
        mTextViews[3] = (TextView) findViewById(R.id.text4);

        mEditText.setCursorVisible(false);//设置光标隐藏
        setListener();

    }

    private void setListener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //当输入的内容不为空的时候才能继续操作
                if (!s.toString().equals("")) {
                    if (mStringBuffer.length() > 3) {
                        mEditText.setText("");
                        return;
                    } else {
                        //进行添加
                        mStringBuffer.append(s);
                        mEditText.setText("");//添加完毕后进行删除，错觉
                        count = mStringBuffer.length();
                        inputContent = mStringBuffer.toString();
                        if (mStringBuffer.length() == 4) {
                            if (mListener != null) {
                                mListener.inputComplete();
                            }
                        }

                    }

                    for (int i = 0; i < mStringBuffer.length(); i++) {
                        mTextViews[i].setText(String.valueOf(inputContent.charAt(i)));
                        mTextViews[i].setBackgroundResource(R.drawable.bg_verify_press);
                    }
                }
            }
        });

        /**
         * 删除的监听
         */
        mEditText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (okKeyDelete()) {
                        return true;
                    }
                }
                return false;
            }

        });

    }

    private boolean okKeyDelete() {
        if (count == 0) {
            count = 4;
            return true;
        }
        if (mStringBuffer.length() > 0) {
            mStringBuffer.delete(count - 1, count);
            count--;

            inputContent = mStringBuffer.toString();
            mTextViews[mStringBuffer.length()].setText("");
            mTextViews[mStringBuffer.length()].setBackgroundResource(R.drawable.bg_verify);
            if (mListener != null) {
                mListener.deleteListener(true);
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setListener(MyEditListener listener) {
        mListener = listener;
    }

    public interface MyEditListener {

        /**
         * 输入完成
         */
        void inputComplete();

        /**
         * 删除的监听
         */
        void deleteListener(boolean isDelete);

    }
}
