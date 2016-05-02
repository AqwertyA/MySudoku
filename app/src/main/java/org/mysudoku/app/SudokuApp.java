package org.mysudoku.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import org.mysudoku.entity.Levels;
import org.mysudoku.R;
import org.mysudoku.util.Utils;
import org.xutils.DbManager;
import org.xutils.x;

/**
 * @author V
 */
public class SudokuApp extends Application {
    public static Context mContext;
    public static Levels levels;
    public static DbManager.DaoConfig daoConfig;
    private static String userName;

    public static void setUserName(String userName) {
        SudokuApp.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void initLevels() {
        String json = Utils.getFromRaw(R.raw.levels);
        levels = Levels.parse(json);
    }

    private void init() {
        mContext = getApplicationContext();
        Utils.init(this);
        initX();
        initLevels();

    }

    private void initX() {
        x.Ext.init(this);
        daoConfig = new DbManager.DaoConfig()
                .setAllowTransaction(true).setDbName("sudoku_db_v1.0");
        try {
            daoConfig.setDbVersion(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            daoConfig.setDbVersion(1);
        }

    }
}
