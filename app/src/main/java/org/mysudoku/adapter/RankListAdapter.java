package org.mysudoku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mysudoku.R;
import org.mysudoku.comparator.RankInfoComparator;
import org.mysudoku.entity.RankInfo;
import org.mysudoku.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 排行榜列表的adapter
 *
 * @author V
 */
public class RankListAdapter extends BaseAdapter {

    private Context context;
    private List<RankInfo> dataAll;
    private List<RankInfo> levelData;
    private int currentLevel = 0;

    public RankListAdapter(Context context, List<RankInfo> dataAll) {
        this.context = context;
        this.dataAll = dataAll;
        Collections.sort(dataAll, new RankInfoComparator());
        levelData = getLevelData(currentLevel);
    }

    /**
     * 获取某一关的数据
     */
    private List<RankInfo> getLevelData(int currentLevel) {
        List<RankInfo> data = new ArrayList<>();
        if (dataAll != null) {
            for (RankInfo info : dataAll) {
                if (info.getLevel() == currentLevel) {
                    data.add(info);
                }
            }
        }
        return data;
    }

    public void setLevel(int level) {
        currentLevel = level - 1;
        levelData = getLevelData(currentLevel);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return levelData.size();
    }

    @Override
    public Object getItem(int position) {
        return levelData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RankInfo info = levelData.get(position);
        String nameFormat = "%s. %s";
        holder.name.setText(String.format(nameFormat, position + 1, info.getName()));
        int textColor;
        switch (position) {
            case 0:
                textColor = context.getResources().getColor(R.color.red);
                break;
            case 1:
                textColor = context.getResources().getColor(R.color.yellow);
                break;
            case 2:
                textColor = context.getResources().getColor(R.color.blue);
                break;
            default:
                textColor = context.getResources().getColor(R.color.black);
                break;
        }
        holder.name.setTextColor(textColor);
        holder.time.setText(Utils.formatTime(info.getTime()));
        return convertView;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setData(List<RankInfo> data) {
        this.dataAll = data;
        setLevel(currentLevel);
    }

    static class ViewHolder {
        TextView name;
        TextView time;

        public ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.tv_name);
            time = (TextView) convertView.findViewById(R.id.tv_time);
        }

    }

}
