package org.mysudoku;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends Activity implements StateChangedListener {

    private Sudoku view_sudoku;
    private View lastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
    }

    private void initView() {
        view_sudoku = (Sudoku) findViewById(R.id.view_sudoku);
        view_sudoku.setStateChangedListener(this);
    }

    public void onNumBtnClick(View v) {
        switch (v.getId()) {
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

    /**
     * 为了增强用户体验，在点击某个Btn之后将其状态改变一下表示是当前这个按钮所代表的状态
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
        if (currentState == view_sudoku.STATE_NONE && lastBtn != null) {
            lastBtn.setEnabled(true);
            lastBtn = null;
        }
    }


}
