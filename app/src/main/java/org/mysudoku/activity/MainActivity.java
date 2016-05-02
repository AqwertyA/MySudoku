package org.mysudoku.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.mysudoku.R;
import org.mysudoku.app.SudokuApp;
import org.mysudoku.widget.EnterUserNameDialog;
import org.mysudoku.widget.SelectLevelDialog;

/**
 * 主界面Activity
 *
 * @author V
 */
public class MainActivity extends BaseActivity implements DialogInterface.OnClickListener, SelectLevelDialog.LevelSelectedListener, EnterUserNameDialog.OnNameSetListener {

    private TextView nickNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();
    }

    @Override
    protected void initData() {
        nickNameTv.setText(String.format(getResources().getString(R.string.nick_name), "unknown"));
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initView() {
        nickNameTv = (TextView) findViewById(R.id.tv_nick_name);

    }

    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void exitGame(View v) {
        showExitDialog(this);
    }


    public void aboutMe(View v) {
        startActivity(new Intent(this, AboutActivity.class));
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
            showExitDialog(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void rankList(View view) {
        startActivity(new Intent(this, RankListActivity.class));
    }

    public void selectLevel(View view) {
        showSelectLevelDialog(this);
    }

    public void enterName(View view) {
        showEnterUserNameDialog().setOnNameSetListener(this);
    }

    @Override
    public void onLevelSelected(int level) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    @Override
    public void onNameSet(String name, Dialog dialog) {
        nickNameTv.setText(String.format(getResources().getString(R.string.nick_name), SudokuApp.getUserName()));
        dialog.dismiss();
    }
}