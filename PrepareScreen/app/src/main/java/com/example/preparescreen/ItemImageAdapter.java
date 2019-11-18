package com.example.preparescreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

class Item{
    private int idImg;

    public Item(){

    }
    public Item(int id){
        idImg = id;
    }

    public int getItem()
    {
        return idImg;
    }
    public void setItem(int id){
        idImg = id;
    }


}

public class ItemImageAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Item> idImage;



    public ItemImageAdapter(Context context, int layout, List<Item> idImage)
    {
        this.context = context;
        this.layout = layout;
        this.idImage = idImage;
    }
    @Override
    public int getCount() {
        return idImage.size();
    }

    @Override
    public Item getItem(int i) {
        return idImage.get(i);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    class ViewHolder{
        private ImageView imageItem;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

         ViewHolder holder = new ViewHolder();

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate( R.layout.item_image, viewGroup, false);
            holder.imageItem = (ImageView) view.findViewById(R.id.img_item);
            view.setTag(holder);

        }
        else{
            holder = (ViewHolder) view.getTag();
        }


        holder.imageItem.setImageResource(idImage.get(i).getItem());

        return view;
    }
}
