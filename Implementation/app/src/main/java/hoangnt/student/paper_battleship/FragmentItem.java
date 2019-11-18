package hoangnt.student.paper_battleship;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentItem extends Fragment {
    Context context;
    Resources resources;
    private TextView textView;
    private ArrayList<Item> listItem;
    private String[] arrItemName;
    private Integer[] listImageId;
    private Integer[] listDescriptionId;
    private GridView gridViewItem;
    AdapterGridViewItem adapter;
    TextView txtViewItemName;
    TextView txtViewItemDescription;
    Button btnClose;

    public FragmentItem(ArrayList<Item> listItem, Integer[] listImageId, Integer[] listDescriptionId, Context context, Resources resources) {
        // Required empty public constructor
        this.listItem = listItem;
        arrItemName = new String[listItem.size()];
        this.listImageId = listImageId;
        this.context= context;
        this.listDescriptionId = listDescriptionId;
        this.resources = resources;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_item, container, false);
        for(int i = 0; i<listItem.size(); i ++){
            arrItemName[i] = listItem.get(i).getSpellName();
        }
        gridViewItem = (GridView) view.findViewById(R.id.gridViewItem);
        adapter = new AdapterGridViewItem(context, R.layout.grid_view_item, listImageId);
        gridViewItem.setAdapter(adapter);

        gridViewItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(view,position);
                return false;
            }
        });

        return view;
    }

    public void showPopup(View view, int position){
        final Dialog popupItemInfo;
        popupItemInfo = new Dialog(context);
        popupItemInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupItemInfo.setContentView(R.layout.popup_item_info);
        txtViewItemName = (TextView) popupItemInfo.findViewById(R.id.item_name);
        if (position < arrItemName.length){
            txtViewItemName.setText(arrItemName[position]);
        }
        else {
            txtViewItemName.setText("Unreached Item");
        }
        txtViewItemDescription = (TextView) popupItemInfo.findViewById(R.id.item_description);
        txtViewItemDescription.setText(resources.getString(listDescriptionId[position]));
        btnClose = (Button) popupItemInfo.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupItemInfo.dismiss();
            }
        });
        popupItemInfo.show();

    }

}
