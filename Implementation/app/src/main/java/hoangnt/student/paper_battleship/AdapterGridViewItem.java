package hoangnt.student.paper_battleship;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdapterGridViewItem extends BaseAdapter {
    private Context context;
    private int layout;
    private Integer[] listImageItem;
    LayoutInflater inflater;

    public AdapterGridViewItem(Context context, int layout, Integer[] listImageItem) {
        this.context = context;
        this.layout = layout;
        this.listImageItem = listImageItem;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder{
        ImageView imgItem;
    }

    @Override
    public int getCount() {
        return listImageItem.length;
    }

    @Override
    public Object getItem(int position) {
        return listImageItem[position];
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
            holder.imgItem = (ImageView) convertView.findViewById(R.id.imageViewItem);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Log.d("My-debbuger", "print map:"+position);
        holder.imgItem.setImageResource(listImageItem[position]);
        return convertView;
    }
}
