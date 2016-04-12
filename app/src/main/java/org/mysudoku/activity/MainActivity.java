package org.mysudoku.activity;

import android.app.AlertDialog;
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
import org.w3c.dom.Text;

/**
 * 主界面Activity
 *
 * @author V
 */
public class MainActivity extends BaseActivity implements DialogInterface.OnClickListener, SelectLevelDialog.LevelSelectedListener, EnterUserNameDialog.OnNameSetListener {
    /**
     * 退出的Dialog对话框
     */
    private AlertDialog exitDialog;
    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();
    }

    @Override
    protected void initData() {
        welcome.setText("Sudoku欢迎！");
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initView() {
        welcome = (TextView) findViewById(R.id.tv_welcome);

    }

    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void exitGame(View v) {
        if (exitDialog == null) {
            exitDialog = new AlertDialog.Builder(this)
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("是的", this)
                    .setNegativeButton("并不是", this).create();
        }
        exitDialog.show();
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
            if (exitDialog == null) {
                exitDialog = new AlertDialog.Builder(this)
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("是的", this)
                        .setNegativeButton("并不是", this).create();
            }
            exitDialog.show();
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
    public void onNameSet(String name) {
        setTitle(name + "  欢迎！");
    }
}