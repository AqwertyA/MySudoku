package org.mysudoku.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import org.mysudoku.widget.EnterUserNameDialog;
import org.mysudoku.widget.SelectLevelDialog;

/**
 * @author V
 */
public abstract class BaseActivity extends Activity {
    /**
     * 退出的Dialog对话框
     */
    private AlertDialog exitDialog;
    private SelectLevelDialog selectLevelDialog;
    private EnterUserNameDialog enterUserNameDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initAll() {
        initView();
        setListener();
        initData();
    }

    protected abstract void initData();

    protected abstract void setListener();

    protected abstract void initView();

    protected SelectLevelDialog showSelectLevelDialog(SelectLevelDialog.LevelSelectedListener listener) {
        if (selectLevelDialog == null) {
            selectLevelDialog = new SelectLevelDialog(listener, this, this);
        }
        if (selectLevelDialog.isShowing()) {
            selectLevelDialog.dismiss();
        }
        selectLevelDialog.show();
        return selectLevelDialog;
    }

    protected EnterUserNameDialog showEnterUserNameDialog() {
        if (enterUserNameDialog == null) {
            enterUserNameDialog = new EnterUserNameDialog(this);
        }
        if (enterUserNameDialog.isShowing()) {
            enterUserNameDialog.dismiss();
        }
        enterUserNameDialog.show();
        return enterUserNameDialog;
    }

    protected void showExitDialog(DialogInterface.OnClickListener listener) {
        if (exitDialog == null) {
            exitDialog = new AlertDialog.Builder(this)
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("是的", listener)
                    .setNegativeButton("并不是", listener).create();
        }
        exitDialog.show();
    }

}
