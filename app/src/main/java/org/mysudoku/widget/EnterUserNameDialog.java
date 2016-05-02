package org.mysudoku.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mysudoku.R;
import org.mysudoku.app.SudokuApp;

/**
 * @author V
 */
public class EnterUserNameDialog extends AlertDialog implements View.OnClickListener {

    private EditText nameEt;
    private OnNameSetListener listener;

    public EnterUserNameDialog(Context context) {
        super(context, 0);
        init();
    }

    private void init() {
        View root = View.inflate(getContext(), R.layout.layout_input_name, null);
        nameEt = (EditText) root.findViewById(R.id.et_user_name);
        Button btnOk = (Button) root.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        setView(root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String name = nameEt.getText().toString();
                if (name.equals("")) {
                    name = "佚名";
                }
                SudokuApp.setUserName(name);
                if (listener != null) {
                    listener.onNameSet(name, this);
                }
                break;
        }
    }

    public void setOnNameSetListener(OnNameSetListener listener) {
        this.listener = listener;
    }

    public interface OnNameSetListener {
        void onNameSet(String name, Dialog dialog);
    }

}
