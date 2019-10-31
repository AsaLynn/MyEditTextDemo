package com.zxn.edittext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private EditText editText;
    private TextView tv_update;
    private FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fl_content = findViewById(R.id.fl_content);
        findViewById(R.id.btn_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move();
            }
        });

        editText = findViewById(R.id.et_input);
        tv_update = findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange: "+hasFocus);
//                if (hasFocus) {
//                    tv_update.setVisibility(View.GONE);
//                }else {
//                    tv_update.setVisibility(View.VISIBLE);
//                }
                tv_update.setVisibility(hasFocus ? View.GONE:View.VISIBLE);
            }
        });
    }

    private void move() {
        FrameLayout.LayoutParams params
                = (FrameLayout.LayoutParams) fl_content.getLayoutParams();
        params.rightMargin = 100;
        params.bottomMargin = 100;
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        fl_content.setLayoutParams(params);
    }

    /**
     * 分发点击除 EditText 外区域，当前焦点在 ET 上，EditText 取消焦点
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        if (v != null && v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }
}
