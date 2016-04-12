package org.mysudoku.callback;

/**
 * 用来监听SodukuView中的状态改变的监听器
 *
 * @author V
 */
public interface StateChangedListener {

    void onStateChanged(int currentState);

    void onStateChanged(int currentState, Type type);

    enum Type {
        GAME_STATE,
        ENTER_STATE
    }

}
