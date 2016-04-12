package org.mysudoku.comparator;

import org.mysudoku.entity.RankInfo;

import java.util.Comparator;

/**
 * 排行榜排名比较器
 *
 * @author V
 */
public class RankInfoComparator implements Comparator<RankInfo> {
    @Override
    public int compare(RankInfo lhs, RankInfo rhs) {
        if (lhs.getTime() > rhs.getTime()) {
            return 1;
        } else if (lhs.getTime() < rhs.getTime()) {
            return -1;
        } else {
            return 0;
        }

    }
}
