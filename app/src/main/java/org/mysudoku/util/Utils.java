package org.mysudoku.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.mysudoku.app.SudokuApp;
import org.mysudoku.widget.Sudoku;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类，封装了一些常用的方法
 *
 * @author V
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    private static boolean isInitialized = false;
    private static Context context;
    private static Toast toast;

    public static void init(Context context) {
        Utils.context = context;
        isInitialized = true;
    }

    /**
     * 从指定id的文件下获取未经过解析的String类型的数据
     *
     * @param id
     * @return
     */
    public static String getFromRaw(int id) {
        if (isInitialized && context != null) {
            try {
                InputStreamReader inputReader = new InputStreamReader(context.getResources().openRawResource(id));
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line;
                String Result = "";
                while ((line = bufReader.readLine()) != null)
                    Result += line;
                return Result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 展示一个Toast，可复用正在展示的Toast以免连续出现好多Toast之后在队列中排了好久
     *
     * @param text
     */
    public static void showToast(String text) {
        if (!isInitialized) {
            Log.w(TAG, "Utils类没有初始化");
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 格式化时间为时分秒
     *
     * @param time long类型的时间
     * @return
     */
    public static String formatTime(long time) {
        long duration = time / 1000;
        if (duration / 60 == 0) {
            String second;
            if (duration < 10) {
                second = "0" + duration;
            } else {
                second = duration + "";
            }
            return "00:" + second;
        } else {
            String minute;
            String second;
            String hour = "00";
            if ((duration / 60) < 10) {
                minute = "0" + (duration / 60);
            } else {
                if ((duration / 60) > 59) {
                    if ((duration / 3600) < 10) {
                        hour = "0" + (duration / 3600);
                    } else {
                        hour = (duration / 3600) + "";
                    }
                    if ((duration / 60) % 60 < 10) {
                        minute = "0" + (duration / 60) % 60;
                    } else {
                        minute = (duration / 60) % 60 + "";
                    }
                } else {
                    minute = (duration / 60) + "";
                }
            }
            if ((duration % 60) < 10) {
                second = "0" + (duration % 60);
            } else {
                second = (duration % 60) + "";
            }
            if (hour.equals("00")) {
                return minute + ":" + second;
            } else {
                return hour + ":" + minute + ":" + second;
            }
        }
    }

    /**
     * 判断该关卡是否存在
     */
    public static boolean isLegalLevel(int level) {
        return level <= SudokuApp.levels.getLevelNum() && level > 0;
    }
}
