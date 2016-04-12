package org.mysudoku.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import org.mysudoku.R;

/**
 * 用来选择数字的dialog
 *
 * @author V
 */
public class NumberPickDialog extends AlertDialog implements View.OnClickListener {

    private NumberPicker numberPicker;
    private OnNumberSelectedListener listener;
    private Button okBtn;

    protected NumberPickDialog(Context context) {
        this(context, 0);
    }

    protected NumberPickDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.layout_number_pick, null);
        okBtn = (Button) view.findViewById(R.id.btn_ok);
        okBtn.setOnClickListener(this);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(9);
        setView(view);
    }

    @Override
    public void onClick(View v) {
        int value = numberPicker.getValue();
        if (listener != null) {
            listener.onNumberSelected(value);
        }
        dismiss();
    }

    public void setOnNumberSelectedListener(OnNumberSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnNumberSelectedListener {
        void onNumberSelected(int number);
    }
}