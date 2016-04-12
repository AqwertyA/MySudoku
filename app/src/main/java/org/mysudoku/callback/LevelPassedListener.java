package org.mysudoku.callback;

/**
 * @author V
 */
public interface LevelPassedListener {
    int STATE_GAME_OVER = 0;
    int STATE_NEXT_LEVEL = 1;

    void levelPassed(int state);
}
