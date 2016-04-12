package org.mysudoku.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.mysudoku.app.SudokuApp;
import org.mysudoku.callback.LevelPassedListener;
import org.mysudoku.callback.StateChangedListener;
import org.mysudoku.util.Utils;

public class Sudoku extends View {
    /**
     * 存放每个位置上的数字的字符串数组
     */
    private String[][] numText = new String[9][9];
    /**
     * 存放载入关卡时就存在的不可修改的数字的列表
     */
    private List<Integer> unChangeableList;
    /**
     * 画细线的画笔
     */
    private Paint mPaintThin;
    /**
     * 画粗线的画笔
     */
    private Paint mPaintThick;
    /**
     * 写字用的画笔-黑色
     */
    private Paint mPaintTextBlack;
    /**
     * 写字用的画笔--红色
     */
    private Paint mPaintTextRed;
    /**
     * View的高度
     */
    private int mHeight;
    /**
     * View的宽度
     */
    private int mWidth;
    /**
     * 每个小块的宽度
     */
    private float blockWidth;

    /**
     * 表示当前填写状态的值
     */
    public final int STATE_NONE = 0;
    public final int STATE_1 = 1;
    public final int STATE_2 = 2;
    public final int STATE_3 = 3;
    public final int STATE_4 = 4;
    public final int STATE_5 = 5;
    public final int STATE_6 = 6;
    public final int STATE_7 = 7;
    public final int STATE_8 = 8;
    public final int STATE_9 = 9;
    public final int STATE_DELETE = 10;
    private int currentState = 0;

    /**
     * Dialog显示时出现的数字
     */
    private String[] items = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * 游戏的运行状态
     */
    public static final int GAME_STATE_RUNNING = 0x10000000;
    public static final int GAME_STATE_WIN = 0x10000001;
    public static final int GAME_STATE_PAUSE = 0x10000002;
    private int gameState = GAME_STATE_PAUSE;

    /**
     * 使用这个View的Activity的Context
     * 好像不用这样写，使用View的{@link #getContext()}就可以得到一个context
     */
    private Context context;

    /**
     * 当前关卡，表示在Levels数组中当前关卡的位置，所以从0开始
     */
    private int currentLevel = 0;
    private StateChangedListener stateChangedListener;
    private LevelPassedListener levelPassedListener;
    private NumberPickDialog numberPickDialog;

    /**
     * 设置当前的state的方法
     *
     * @param currentState 将currentState参数设置为该值
     */
    public void setCurrentState(int currentState) {
        if (currentState >= 0 && currentState <= 10) {
            this.currentState = currentState;
            if (stateChangedListener != null) {
                stateChangedListener.onStateChanged(currentState, StateChangedListener.Type.ENTER_STATE);
            }
        } else {
            throw new RuntimeException("传入的currentState值必须在指定范围内！(0~10)传入的值为 "
                    + currentState);
        }
    }

    public Sudoku(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public Sudoku(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public Sudoku(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * 初始化的方法
     */
    private void init() {
        // 细线画笔
        mPaintThin = new Paint();
        mPaintThin.setAntiAlias(true);
        mPaintThin.setStyle(Paint.Style.STROKE);
        mPaintThin.setStrokeWidth(2);
        mPaintThin.setColor(Color.BLACK);
        // 粗线画笔
        mPaintThick = new Paint();
        mPaintThick.setAntiAlias(true);
        mPaintThick.setStyle(Paint.Style.STROKE);
        mPaintThick.setStrokeWidth(5);
        mPaintThick.setColor(Color.BLACK);
        // 写字的画笔--黑色
        mPaintTextBlack = new Paint();
        mPaintTextBlack.setAntiAlias(true);
        mPaintTextBlack.setStyle(Paint.Style.STROKE);
        mPaintTextBlack.setColor(Color.BLACK);
        // 写字的画笔--红色
        mPaintTextRed = new Paint();
        mPaintTextRed.setAntiAlias(true);
        mPaintTextRed.setStyle(Paint.Style.STROKE);
        mPaintTextRed.setColor(Color.RED);

        // 初始化一下numText，初始化为""空字符。
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                numText[i][j] = "";
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        blockWidth = (float) mWidth / 9;
        // 在获得View的宽高之后再设置字体大小
        mPaintTextBlack.setTextSize(mWidth / 9 * 0.7f);
        mPaintTextRed.setTextSize(mWidth / 9 * 0.7f);
        System.out.println("W" + mWidth);
        System.out.println("H" + mHeight);
    }

    /**
     * 画出数独的网格，十条竖线十条横线，并且每三格的分割线用粗画笔画
     *
     * @param canvas
     */
    private void drawGrid(Canvas canvas) {
        for (int i = 0; i <= 9; i++) {
            float startX = blockWidth * i;
            float startY = 0;
            float stopX = blockWidth * i;
            float stopY = mWidth;
            if (i % 3 == 0) {
                canvas.drawLine(startX, startY, stopX, stopY, mPaintThick);
                canvas.drawLine(startY, startX, stopY, stopX, mPaintThick);
            } else {
                canvas.drawLine(startX, startY, stopX, stopY, mPaintThin);
                canvas.drawLine(startY, startX, stopY, stopX, mPaintThin);
            }

        }
    }

    /**
     * 画出数字的方法，
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        // TODO 这样算逻辑上有点不对 但结果显示还可以
        float padding = (blockWidth - mPaintTextBlack.getTextSize());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (isLevelData(i, j)) {
                    canvas.drawText(numText[i][j],
                            blockWidth * i + padding, blockWidth
                                    * (j + 1) - padding, mPaintTextBlack);
                } else {
                    canvas.drawText(numText[i][j],
                            blockWidth * i + padding, blockWidth
                                    * (j + 1) - padding, mPaintTextRed);
                }
            }
        }
    }

    public boolean isLevelData(int xPos, int yPos) {
        return unChangeableList.contains(xPos * numText.length + yPos);
    }

    /**
     * 设置给定坐标的数字
     *
     * @param xPos
     * @param yPos
     * @param text
     */
    private void setTextInPosition(int xPos, int yPos, String text) {
        // 判断如果该位置在unChangableList中存在则不可修改
        if (isLevelData(xPos, yPos)) {
            System.out.println("关卡数据不可修改");
            return;
        }
        numText[xPos][yPos] = text;
        setCurrentState(STATE_NONE);
        invalidate();
        judge(xPos, yPos);
    }

    /**
     * 在{@link Sudoku#setTextInPosition(int, int, String)}
     * 方法之后使用，用于判断输入的数字是否合法，以及填入该数字之后是否填满，填满则判断游戏结束，弹出对话框做下一步操作
     *
     * @param yPos
     * @param xPos
     */
    private void judge(int xPos, int yPos) {
        // 判断数字在横竖和方格内是否有重复，以及游戏是否胜利
        // 如果当前格子为空，那么肯定没有游戏结束，也不用判断重复，所以直接return（执行删除操作后当前格子为""空字符）
        if (numText[xPos][yPos].equals("")) {
            System.out.println("空字符");
            return;
            // else就是不为空，然后在判断重复
        } else if (isRepeat(xPos, yPos)) {
            Utils.showToast("有重复，请检查");
            setTextInPosition(xPos, yPos, "");
            // 没有重复也不为空，那么这个数字就被填进去了，这时还要判断是否已经填满，若填满则游戏结束
        } else {
            System.out.println("填入的数字应该没错好像，再判断一下是否填满吧");
            //先试着将gameState设置为GAMESTATE_WIN
            gameState = GAME_STATE_WIN;
            //循环找为空的值，如果有，就把gameState又改为GAMESTATE_RUNNING
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (numText[i][j].equals("")) {
                        gameState = GAME_STATE_RUNNING;
                    }
                }
            }
            //然后再判断gameState的状态
            if (gameState == GAME_STATE_WIN) {
                setGameState(GAME_STATE_WIN);
                // 这个地方就简单点，弹出Toast提示过关并载入下一关
                Utils.showToast("Congratulations! You win the game!过关！~");
                levelPassed();
            }
        }
    }

    /**
     * 将本关数据保存到排行榜数据库
     */
    private void levelPassed() {
        if (levelPassedListener != null) {
            if (currentLevel == SudokuApp.levels.getLevelNum() - 1) {
                levelPassedListener.levelPassed(LevelPassedListener.STATE_GAME_OVER);
            } else {
                levelPassedListener.levelPassed(LevelPassedListener.STATE_NEXT_LEVEL);
            }
        }
    }

    /**
     * 判断指定坐标的数字是否和其他的数字有重复的
     *
     * @param xPos
     * @param yPos
     * @return 如果有重复返回true，否则false
     */
    private boolean isRepeat(int xPos, int yPos) {
        System.out.println("判断" + numText[xPos][yPos] + "在" + xPos + "," + yPos
                + "上的重复");
        for (int i = 0; i < 9; i++) {
            // 横向一行
            if (numText[i][yPos].equals(numText[xPos][yPos]) && i != xPos) {
                return true;
            }
            // 纵向一行
            if (numText[xPos][i].equals(numText[xPos][yPos]) && i != yPos) {
                return true;
            }
        }
        // 所属的9个格子内
        int x = xPos / 3 * 3;
        int y = yPos / 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numText[x + i][y + j].equals(numText[xPos][yPos])
                        && (x + i) != xPos && (y + j) != yPos) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 加载一个关卡时调用此方法， 将所有的数字重新设置一遍。
     *
     * @param level
     */
    private void loadGameLevel(int level) {
        currentLevel = level - 1;
        this.numText = SudokuApp.levels.getLevel(currentLevel);
        unChangeableList = new ArrayList<>();
        for (int i = 0; i < numText.length; i++) {
            for (int j = 0; j < numText[i].length; j++) {
                if (!numText[i][j].equals("")) {
                    unChangeableList.add(i * numText.length + j);
                }
            }
        }
        setGameState(GAME_STATE_RUNNING);
        invalidate();
    }

    /**
     * 设置关卡，可用于跳关等操作
     *
     * @param level
     */
    public void setCurrentLevel(int level) {
        currentLevel = level - 1;
        loadGameLevel(level);
    }

    public void restartLevel() {
        loadGameLevel(currentLevel);
    }

    public int getCurrentLevel() {
        return currentLevel + 1;
    }

    @Override
    /**
     * onDraw方法，绘制网格和数字
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawText(canvas);
    }

    @Override
    /**
     * 触摸事件处理方法，只需要写按下动作触发时的逻辑
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (gameState != GAME_STATE_RUNNING) {
            return false;
        }
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int xPos = (int) event.getX();
                int yPos = (int) event.getY();
                final int i = xPos / (int) blockWidth;
                final int j = yPos / (int) blockWidth;
                if (i >= 9 || j >= 9) {
                    return true;
                }
                switch (currentState) {
                    case STATE_NONE:
                        if (isLevelData(i, j)) {
                            break;
                        }
                        if (numberPickDialog == null) {
                            numberPickDialog = new NumberPickDialog(getContext());
                        }
                        numberPickDialog.setOnNumberSelectedListener(new NumberPickDialog.OnNumberSelectedListener() {
                            @Override
                            public void onNumberSelected(int number) {
                                setTextInPosition(i, j, number + "");
                            }
                        });
                        numberPickDialog.show();
                        break;
                    case STATE_1:
                    case STATE_2:
                    case STATE_3:
                    case STATE_4:
                    case STATE_5:
                    case STATE_6:
                    case STATE_7:
                    case STATE_8:
                    case STATE_9:
                        setTextInPosition(i, j, currentState + "");
                        break;
                    case STATE_DELETE:
                        setTextInPosition(i, j, "");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void setStateChangedListener(StateChangedListener stateChangedListener) {
        this.stateChangedListener = stateChangedListener;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
        if (stateChangedListener != null) {
            stateChangedListener.onStateChanged(gameState, StateChangedListener.Type.GAME_STATE);
        }
    }

    public void setLevelPassedListener(LevelPassedListener levelPassedListener) {
        this.levelPassedListener = levelPassedListener;
    }
}
