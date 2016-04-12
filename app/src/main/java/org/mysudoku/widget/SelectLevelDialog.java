package org.mysudoku.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.mysudoku.R;
import org.mysudoku.activity.BaseActivity;
import org.mysudoku.activity.GameActivity;
import org.mysudoku.app.SudokuApp;

/**
 * 选择关卡的自定义Dialog，返回选择的关卡数字
 *
 * @author V
 */
public class SelectLevelDialog extends AlertDialog implements View.OnClickListener {
    private LevelSelectedListener listener;
    private BaseActivity activity;
    private NumberPicker numberPicker;

    public SelectLevelDialog(LevelSelectedListener listener, BaseActivity activity, Context context) {
        super(context, 0);
        this.listener = listener;
        this.activity = activity;
        init();
    }

    private void init() {
        View root = View.inflate(getContext(), R.layout.layout_select_level, null);
        numberPicker = (NumberPicker) root.findViewById(R.id.number_picker_level);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(SudokuApp.levels.getLevelNum());
        Button btnOk = (Button) root.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        setView(root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                int level;
                level = numberPicker.getValue();
                if (activity instanceof GameActivity) {
                    ((GameActivity) activity).setCurrentLevel(level);
                } else {
                    Intent intent = new Intent(activity, GameActivity.class);
                    intent.putExtra(GameActivity.KEY_LEVEL, level);
                    activity.startActivity(intent);
                }
                if (listener != null) {
                    listener.onLevelSelected(level);
                }
                break;
        }
    }

    public interface LevelSelectedListener {
        void onLevelSelected(int level);
    }
}
