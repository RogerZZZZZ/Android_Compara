package com.example.rogerzzzz.compara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.rogerzzzz.compara.models.ProductItem;

import java.util.List;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by rogerzzzz on 16/11/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {
    protected Activity mActivity = this;
    private RequestQueue      queue;
    private List<ProductItem> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(final String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(mActivity, io.github.xudaojie.qrcodelib.R.string.scan_failed, Toast.LENGTH_SHORT).show();
            restartPreview();
        } else {
            showResultWindow(resultString);
        }
    }

    private void showResultWindow(String detail){
        Intent intent = new Intent(SimpleCaptureActivity.this, ProductListActivity.class);
        intent.putExtra("code", detail);
        this.setResult(1, intent);
        finish();
    }
}
