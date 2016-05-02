package org.mysudoku.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import org.mysudoku.R;
import org.mysudoku.app.SudokuApp;
import org.mysudoku.callback.LevelPassedListener;
import org.mysudoku.callback.StateChangedListener;
import org.mysudoku.entity.RankInfo;
import org.mysudoku.util.Utils;
import org.mysudoku.widget.EnterUserNameDialog;
import org.mysudoku.widget.SelectLevelDialog;
import org.mysudoku.widget.Sudoku;
import org.xutils.ex.DbException;
import org.xutils.x;

public class GameActivity extends BaseActivity implements StateChangedListener,
        View.OnClickListener, SelectLevelDialog.LevelSelectedListener,
        LevelPassedListener, DialogInterface.OnDismissListener,
        DialogInterface.OnClickListener, EnterUserNameDialog.OnNameSetListener {

    public static final String KEY_LEVEL = "level";
    private Sudoku view_sudoku;
    private View lastBtn;
    private Button selectLevelBtn;
    private Button restartBtn;
    private Button exitBtn;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnD;
    private Chronometer chronometer;
    private int tempLevelPassedState;
    private String formatTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initAll();
    }

    @Override
    protected void initData() {
        int level = getIntent().getIntExtra("level", 1);
        view_sudoku.setCurrentLevel(level);
        chronometer.setFormat("本关用时: %s");
        chronometer.start();
        formatTitle = getString(R.string.app_name) + ": 第%s关";
        setTitle(String.format(formatTitle, view_sudoku.getCurrentLevel()));
    }

    @Override
    protected void initView() {
        view_sudoku = (Sudoku) findViewById(R.id.view_sudoku);
        selectLevelBtn = (Button) findViewById(R.id.btn_select_level);
        restartBtn = (Button) findViewById(R.id.btn_restartLv);
        exitBtn = (Button) findViewById(R.id.btn_exit);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btnD = (Button) findViewById(R.id.btn_D);
        chronometer = (Chronometer) findViewById(R.id.soduku_chronometer);
    }

    @Override
    protected void setListener() {
        view_sudoku.setStateChangedListener(this);
        view_sudoku.setLevelPassedListener(this);
        selectLevelBtn.setOnClickListener(this);
        restartBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    /**
     * 在点击某个Btn之后将其状态改变一下表示是当前这个按钮所代表的状态
     *
     * @param v
     */
    private void changeBtnState(View v) {
        v.setEnabled(false);
        if (lastBtn != null) {
            lastBtn.setEnabled(true);
        }
        lastBtn = v;
    }

    /**
     * @param currentState
     */
    @Override
    public void onStateChanged(int currentState) {

    }

    @Override
    public void onStateChanged(int currentState, Type type) {
        switch (type) {
            case ENTER_STATE:
                if (currentState == view_sudoku.STATE_NONE && lastBtn != null) {
                    lastBtn.setEnabled(true);
                    lastBtn = null;
                }
                break;
            case GAME_STATE:
                if (currentState == Sudoku.GAME_STATE_RUNNING) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }
                if (currentState == Sudoku.GAME_STATE_WIN) {
                    chronometer.stop();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_level:
                showSelectLevelDialog(this);
                break;
            case R.id.btn_restartLv:
                view_sudoku.restartLevel();
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_1:
                view_sudoku.setCurrentState(view_sudoku.STATE_1);
                changeBtnState(v);
                break;
            case R.id.btn_2:
                view_sudoku.setCurrentState(view_sudoku.STATE_2);
                changeBtnState(v);
                break;
            case R.id.btn_3:
                view_sudoku.setCurrentState(view_sudoku.STATE_3);
                changeBtnState(v);
                break;
            case R.id.btn_4:
                view_sudoku.setCurrentState(view_sudoku.STATE_4);
                changeBtnState(v);
                break;
            case R.id.btn_5:
                view_sudoku.setCurrentState(view_sudoku.STATE_5);
                changeBtnState(v);
                break;
            case R.id.btn_6:
                view_sudoku.setCurrentState(view_sudoku.STATE_6);
                changeBtnState(v);
                break;
            case R.id.btn_7:
                view_sudoku.setCurrentState(view_sudoku.STATE_7);
                changeBtnState(v);
                break;
            case R.id.btn_8:
                view_sudoku.setCurrentState(view_sudoku.STATE_8);
                changeBtnState(v);
                break;
            case R.id.btn_9:
                view_sudoku.setCurrentState(view_sudoku.STATE_9);
                changeBtnState(v);
                break;
            case R.id.btn_D:
                view_sudoku.setCurrentState(view_sudoku.STATE_DELETE);
                changeBtnState(v);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLevelSelected(int level) {
        if (!Utils.isLegalLevel(level)) {
            return;
        }

        setTitle(String.format(formatTitle, view_sudoku.getCurrentLevel()));

    }

    public void setCurrentLevel(int level) {
        view_sudoku.setCurrentLevel(level);
    }

    @Override
    public void levelPassed(int state) {
        this.tempLevelPassedState = state;
        chronometer.stop();
        saveRecord();
    }

    private void nextLevel() {
        new AlertDialog.Builder(this)
                .setTitle("恭喜过关")
                .setMessage("是否进入下一关？")
                .setPositiveButton("是", this)
                .setNegativeButton("否", this)
                .show();
    }

    private void gameOver() {
        Utils.showToast("You passed all levels!全部通关~");
        new AlertDialog.Builder(this)
                .setTitle("恭喜通关")
                .setNegativeButton("确定", this)
                .show();
    }

    private void saveRecord() {
        RankInfo info = new RankInfo();
        info.setLevel(view_sudoku.getCurrentLevel() - 1);
        info.setTime(SystemClock.elapsedRealtime() - chronometer.getBase());
        info.setName(SudokuApp.getUserName());
        saveRecord(info);
    }


    private void saveRecord(RankInfo info) {
        if (SudokuApp.getUserName() == null) {
            EnterUserNameDialog dialog = showEnterUserNameDialog();
            dialog.setOnNameSetListener(this);
            dialog.setOnDismissListener(this);
            return;
        }
        try {
            x.getDb(SudokuApp.daoConfig).save(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
        goNextLevel();
    }

    private void goNextLevel() {
        switch (tempLevelPassedState) {
            case STATE_GAME_OVER:
                gameOver();
                break;
            case STATE_NEXT_LEVEL:
                nextLevel();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!checkUserName()) {
            Utils.showToast("未输入用户名，不会保存纪录");
            goNextLevel();
        }
    }

    private boolean checkUserName() {
        return SudokuApp.getUserName() != null;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                view_sudoku.setCurrentLevel(view_sudoku.getCurrentLevel() + 1);
                setTitle(String.format(formatTitle, view_sudoku.getCurrentLevel()));
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                finish();
                break;
        }
    }

    @Override
    public void onNameSet(String name, Dialog dialog) {
        saveRecord();
        dialog.dismiss();
    }
}
