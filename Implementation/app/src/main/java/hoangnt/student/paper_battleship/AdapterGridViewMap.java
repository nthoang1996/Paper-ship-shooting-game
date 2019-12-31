package hoangnt.student.paper_battleship;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView cell;
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
            holder.cell = (ImageView) convertView.findViewById(R.id.cell_grid);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(statusMap[position] > 0){
            if((statusMap[position] > 0 && statusMap[position] <= 10) || (statusMap[position] >= 21 && statusMap[position] <= 30)){
                if(idMap == 1){
                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]));
                }
            }
            else if(statusMap[position] > 100 && statusMap[position] < 200){
                if(idMap == 0 ){

                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]));
                }
                else {
                    holder.cell.setBackgroundResource(getIdImage(statusMap[position]-11));
                }
            }
            else if(statusMap[position] > 200)
            {
                if(statusMap[position] >= 212) holder.cell.setBackgroundResource(getIdImage(202));
            }
            else if(statusMap[position] == 20){
                holder.cell.setBackgroundResource(R.drawable.shooted);
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
            case 21: return R.drawable.n_ship_1;
            case 22: return R.drawable.n_ship_2_2;
            case 23: return R.drawable.n_ship_2_1;
            case 24: return R.drawable.n_ship_3_3;
            case 25: return R.drawable.n_ship_3_2;
            case 26: return R.drawable.n_ship_3_1;
            case 27: return R.drawable.n_ship_4_4;
            case 28: return R.drawable.n_ship_4_3;
            case 29: return R.drawable.n_ship_4_2;
            case 30: return R.drawable.n_ship_4_1;
            case 50: return R.drawable.x;
            case 20: return R.drawable.shooted;
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
            case 121: return R.drawable.n_shipbr_1;
            case 122: return R.drawable.n_shipbr_2_2;
            case 123: return R.drawable.n_shipbr_2_1;
            case 124: return R.drawable.n_shipbr_3_3;
            case 125: return R.drawable.n_shipbr_3_2;
            case 126: return R.drawable.n_shipbr_3_1;
            case 127: return R.drawable.n_shipbr_4_4;
            case 128: return R.drawable.n_shipbr_4_3;
            case 129: return R.drawable.n_shipbr_4_2;
            case 130: return R.drawable.n_shipbr_4_1;

            case 202: return R.drawable.skill_lvl2;
            case 192: return R.drawable.skill_lvl3;
            case 193: return R.drawable.skill_lvl4;
            case 194: return R.drawable.skill_lvl5;
            case 195: return R.drawable.skill_lvl6;
            case 198: return R.drawable.skill_lvl9;
            case 200: return R.drawable.skill_lvl11;
            case 201: return R.drawable.skill_lvl12;
        }
        return 0;
    }
}

