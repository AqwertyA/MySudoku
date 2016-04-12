package org.mysudoku.activity;

import android.app.Activity;
import android.os.Bundle;

import org.mysudoku.widget.EnterUserNameDialog;
import org.mysudoku.widget.SelectLevelDialog;

/**
 * @author V
 */
public abstract class BaseActivity extends Activity {

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

}
