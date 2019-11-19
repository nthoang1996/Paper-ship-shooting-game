package hoangnt.student.paper_battleship;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterGridViewMap  extends BaseAdapter {
    private Context context;
    private int layout;
    private Integer[] backgroundSea;
    LayoutInflater inflater;



    public AdapterGridViewMap(Context context, int layout, Integer[] backgroundSea)
    {
        this.context = context;
        this.layout = layout;
        this.backgroundSea = backgroundSea;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private class ViewHolder{
        TextView cell;
    }

    @Override
    public int getCount() {
        return backgroundSea.length;
    }

    @Override
    public Object getItem(int position) {
        return backgroundSea[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            holder.cell = (TextView) convertView.findViewById(R.id.cell_grid);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.cell.setBackgroundColor(Color.TRANSPARENT);
        return convertView;
    }
}

