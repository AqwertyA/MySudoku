package org.mysudoku.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * @author V
 */
public class Levels {

    private List<LevelsEntity> levels;
    private static Gson gson;

    public void setLevels(List<LevelsEntity> levels) {
        this.levels = levels;
    }

    public List<LevelsEntity> getLevels() {
        return levels;
    }

    /**
     * 返回一个指定关卡的copy.
     *
     * @param level
     * @return
     */
    public String[][] getLevel(int level) {
        List<List<String>> lv = levels.get(level).getLevel();
        String[][] l = new String[9][9];
        for (int i = 0; i < lv.size(); i++) {
            for (int j = 0; j < lv.get(i).size(); j++) {
                l[i][j] = lv.get(i).get(j);
            }
        }
        return l;
    }

    public int getLevelNum() {
        return levels == null ? 0 : levels.size();
    }

    public static Levels parse(String json) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(json, Levels.class);
    }

    public static class LevelsEntity {
        private List<List<String>> level;

        public void setLevel(List<List<String>> level) {
            this.level = level;
        }

        public List<List<String>> getLevel() {
            return level;
        }
    }
}
