package org.mysudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends Activity implements DialogInterface.OnClickListener {
    /**
     * 退出的Dialog对话框
     */
    private AlertDialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitDialog = new AlertDialog.Builder(this).setMessage("确定要退出吗？").setPositiveButton("是的", this).setNegativeButton("并不是", this).create();
    }

    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void exitGame(View v) {
        exitDialog.show();
    }

    public void aboutMe(View v) {
        new AlertDialog.Builder(this).setTitle("关于").setMessage("作者：魏巍\r\nqq:307886220").show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}