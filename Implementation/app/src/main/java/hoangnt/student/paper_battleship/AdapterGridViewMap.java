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

import java.util.ArrayList;
import java.util.List;

public class AdapterGridViewMap  extends BaseAdapter {
    private int layout;
    LayoutInflater inflater;
    Ship[] listShip;



    public AdapterGridViewMap(Context context, int layout, Ship[] listShip)
    {
        this.listShip= new Ship[7];
        this.listShip = listShip;
        this.layout = layout;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    private class ViewHolder{
        TextView cell;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Integer idImage = new Integer(-1);
        boolean isFound=false;
        if(listShip[0] != null){
            for (int i = 0 ; i< listShip.length ; i++)
            {
                for (int j =0 ; j<listShip[i].getPosition().size(); j++){
                    if(position == listShip[i].getPosition().get(j)){
                        idImage = listShip[i].getId_part().get(j);
                        isFound = true;
                        break;
                    }
                }
                if(isFound){
                    break;
                }
            }
        }

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            holder.cell = (TextView) convertView.findViewById(R.id.cell_grid);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(idImage != -1){
            holder.cell.setBackgroundResource(getIdImage(idImage));
        }
        else {
            holder.cell.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    public Integer getIdImage(int value){
        switch (value){
            case 1: return R.drawable.ship_1;
            case 2: return R.drawable.ship_2_1;
            case 3: return R.drawable.ship_2_2;
            case 4: return R.drawable.ship_3_1;
            case 5: return R.drawable.ship_3_2;
            case 6: return R.drawable.ship_3_3;
            case 7: return R.drawable.ship_4_1;
            case 8: return R.drawable.ship_4_2;
            case 9: return R.drawable.ship_4_3;
            case 10: return R.drawable.ship_4_4;
        }
        return 0;
    }
}

