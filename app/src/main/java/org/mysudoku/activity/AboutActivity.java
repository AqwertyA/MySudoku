package org.mysudoku.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.mysudoku.R;

public class AboutActivity extends BaseActivity implements View.OnLongClickListener {

    private ImageView iv;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initAll();
    }

    @Override
    protected void initData() {
        String version;
        try {
            version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = this.getString(R.string.cannot_find_version_name);
        }
        textView.setText("版本" + version);
    }

    @Override
    protected void setListener() {
        iv.setOnLongClickListener(this);
    }

    @Override
    protected void initView() {
        textView = (TextView) findViewById(R.id.tv_version_about);
        iv = (ImageView) findViewById(R.id.iv_logo);
    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(this).setTitle("关于我").setMessage("我的QQ：307886220").show();
        return true;
    }
}
