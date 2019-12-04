package hoangnt.student.paper_battleship;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterGridViewMap  extends BaseAdapter {
    private int layout;
    LayoutInflater inflater;
    Ship[] listShip;
    int[] statusMap;
    int idMap;



    public AdapterGridViewMap(Context context, int layout, Ship[] listShip, int[] statusMap, int idMap)
    {
        this.listShip= new Ship[7];
        this.listShip = listShip;
        this.layout = layout;
        this.statusMap = statusMap;
        this.idMap = idMap;
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
//        if(listShip[0] != null){
//            for (int i = 0 ; i< listShip.length ; i++)
//            {
//                for (int j =0 ; j<listShip[i].getPosition().size(); j++){
//                    if(position == listShip[i].getPosition().get(j)){
//                        idImage = listShip[i].getId_part().get(j);
//                        isFound = true;
//                        break;
//                    }
//                }
//                if(isFound){
//                    break;
//                }
//            }
//        }

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            holder.cell = (TextView) convertView.findViewById(R.id.cell_grid);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(statusMap[position] > 0){
            if(statusMap[position] > 0 && statusMap[position] <= 10){
                if(idMap == 1){
                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]));
                }
            }
            else if(statusMap[position] > 100){
                if(idMap == 0){
                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]));
                }
                else {
                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]-11));
                }
            }
            else {
                holder.cell.setBackgroundResource(R.drawable.x);
            }
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
            case 101: return R.drawable.shipbr_1;
            case 102: return R.drawable.shipbr_2_1;
            case 103: return R.drawable.shipbr_2_2;
            case 104: return R.drawable.shipbr_3_1;
            case 105: return R.drawable.shipbr_3_2;
            case 106: return R.drawable.shipbr_3_3;
            case 107: return R.drawable.shipbr_4_1;
            case 108: return R.drawable.shipbr_4_2;
            case 109: return R.drawable.shipbr_4_3;
            case 110: return R.drawable.shipbr_4_4;
        }
        return 0;
    }
}

