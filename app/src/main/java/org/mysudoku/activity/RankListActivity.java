package org.mysudoku.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.mysudoku.R;
import org.mysudoku.adapter.RankListAdapter;
import org.mysudoku.app.SudokuApp;
import org.mysudoku.entity.RankInfo;
import org.mysudoku.util.Utils;
import org.mysudoku.widget.SelectLevelDialog;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 排行榜Activity
 */
public class RankListActivity extends BaseActivity implements View.OnClickListener, SelectLevelDialog.LevelSelectedListener, PopupMenu.OnMenuItemClickListener {

    private ListView listView;
    private RankListAdapter adapter;
    private Button selectLevelBtn;
    private Button clearDataBtn;
    private PopupMenu popupMenu;
    private TextView emptyView;
    private String formatTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
        initAll();
    }

    @Override
    protected void setListener() {
        selectLevelBtn.setOnClickListener(this);
        clearDataBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        adapter = new RankListAdapter(this, getData());
        listView.setAdapter(adapter);
        emptyView.setText(String.format(getString(R.string.level_no_data), adapter.getCurrentLevel() + 1));
        listView.setEmptyView(emptyView);
        formatTitle = getString(R.string.app_name) + ": 第%s关";
        setTitle(String.format(formatTitle, adapter.getCurrentLevel() + 1));
    }

    private List<RankInfo> getData() {
        List<RankInfo> data = null;
        try {
            data = x.getDb(SudokuApp.daoConfig).findAll(RankInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_rank);
        selectLevelBtn = (Button) findViewById(R.id.btn_select_level);
        clearDataBtn = (Button) findViewById(R.id.btn_clear_data);
        emptyView = (TextView) findViewById(R.id.tv_empty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_level:
                showSelectLevelDialog(this);
                break;
            case R.id.btn_clear_data:
                if (popupMenu == null) {
                    popupMenu = new PopupMenu(this, v);
                    popupMenu.inflate(R.menu.menu);
                    popupMenu.setOnMenuItemClickListener(this);
                }
                popupMenu.show();
                break;
        }
    }

    @Override
    public void onLevelSelected(int level) {
        if (!Utils.isLegalLevel(level)) {
            return;
        }
        adapter.setLevel(level);
        setTitle(String.format(formatTitle, adapter.getCurrentLevel() + 1));
        emptyView.setText(String.format(getString(R.string.level_no_data), level));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_level:
                try {
                    x.getDb(SudokuApp.daoConfig).executeUpdateDelete(
                            "delete from " + RankInfo.getTableName()
                                    + " where level = " + adapter.getCurrentLevel());
                } catch (DbException e) {
                    e.printStackTrace();
                }
                adapter.setData(getData());
                break;
            case R.id.clear_all:
                try {
                    x.getDb(SudokuApp.daoConfig).delete(RankInfo.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                adapter.setData(getData());
                break;
        }
        popupMenu.dismiss();
        return true;
    }
}
