package hoangnt.student.paper_battleship;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListViewHistory extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<BattleHistory> listBattleHistory;
    LayoutInflater inflater;
    Resources resources;
    String packageName;

    public AdapterListViewHistory(Context context, int layout, ArrayList<BattleHistory> listBattleHistory, Resources resources, String packageName) {
        this.context = context;
        this.layout = layout;
        this.listBattleHistory = listBattleHistory;
        this.resources = resources;
        this.packageName = packageName;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder{
        TextView txtViewEnemyName;
        TextView txtViewResult;
    }

    @Override
    public int getCount() {
        return listBattleHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return listBattleHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterListViewHistory.ViewHolder holder;

        if(convertView == null){
            holder = new AdapterListViewHistory.ViewHolder();
            convertView = inflater.inflate(layout, null);
            holder.txtViewEnemyName = (TextView) convertView.findViewById(R.id.txtview_enemy_name);
            holder.txtViewResult = (TextView) convertView.findViewById(R.id.txtview_result);
            convertView.setTag(holder);
        }else{
            holder = (AdapterListViewHistory.ViewHolder)convertView.getTag();
        }
        holder.txtViewEnemyName.setText(listBattleHistory.get(position).getEnemy());
        if (listBattleHistory.get(position).getResult()== 1) {
            Integer id = resources.getIdentifier("win_text", "string", packageName);
            holder.txtViewResult.setText(resources.getString(id));
            holder.txtViewResult.setTextColor(Color.BLUE);
        }
        else{
            Integer id = resources.getIdentifier("lose_text", "string", packageName);
            holder.txtViewResult.setText(resources.getString(id));
            holder.txtViewResult.setTextColor(Color.RED);
        }
        return convertView;
    }
}
