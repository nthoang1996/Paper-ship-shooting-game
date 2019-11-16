package hoangnt.student.paper_battleship;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBattleHistory extends Fragment {
    ListView listViewBattleHistory;
    ArrayList<BattleHistory> listBattleHistory;
    Resources resources;
    AdapterListViewHistory adapter;
    Context context;
    String packageName;
    TextView txtViewEnemyName;
    TextView txtViewTimeLable;
    TextView txtViewTime;
    TextView txtReceiveExpLable;
    TextView txtViewReceiveExp;
    Button btnOK;

    public FragmentBattleHistory(ArrayList<BattleHistory> listBattleHistory, Resources resources, Context context, String packageName) {
        // Required empty public constructor
        this.listBattleHistory = listBattleHistory;
        this.resources = resources;
        this.context = context;
        this.packageName = packageName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_battle_history, container, false);
        listViewBattleHistory = (ListView) view.findViewById(R.id.listview_battle_history);
        adapter = new AdapterListViewHistory(context, R.layout.list_view_battle_history, listBattleHistory, resources, packageName);
        listViewBattleHistory.setAdapter(adapter);

        listViewBattleHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    showPopup(view, position);
                } catch (ParseException ex) {
                    Log.d("Dialog-Ex", ex.getMessage());
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void showPopup(View view, int position) throws ParseException {
        final Dialog popupBattleHistoryInfo;
        popupBattleHistoryInfo = new Dialog(context);
        popupBattleHistoryInfo.setContentView(R.layout.popup_info_battle_history);
        txtViewEnemyName = (TextView) popupBattleHistoryInfo.findViewById(R.id.txtview_enemy_name);
        txtViewEnemyName.setText(listBattleHistory.get(position).getEnemy());
        if(listBattleHistory.get(position).getResult() == 1){
            txtViewEnemyName.setBackgroundColor(Color.BLUE);
        }else {
            txtViewEnemyName.setBackgroundColor(Color.GRAY);
        }
        txtViewTimeLable = (TextView) popupBattleHistoryInfo.findViewById(R.id.txtview_time_lable);
        Integer id = resources.getIdentifier("time_lable", "string", packageName);
        txtViewTimeLable.setText(resources.getString(id));
        txtViewTime = (TextView) popupBattleHistoryInfo.findViewById(R.id.txtview_time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        Date date = formatter.parse(listBattleHistory.get(position).getDatetime());
        String format = formatter.format(date);
        txtViewTime.setText(format);
        txtReceiveExpLable = (TextView) popupBattleHistoryInfo.findViewById(R.id.txtview_receive_exp_lable);
        id = resources.getIdentifier("receive_exp_lable", "string", packageName);
        txtReceiveExpLable.setText(resources.getString(id));
        txtViewReceiveExp = (TextView) popupBattleHistoryInfo.findViewById(R.id.txtview_receive_exp);
        id = resources.getIdentifier("exp_text", "string", packageName);
        txtViewReceiveExp.setText(""+listBattleHistory.get(position).getReceiveExp()+ " " + resources.getString(id));
        btnOK = (Button) popupBattleHistoryInfo.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupBattleHistoryInfo.dismiss();
            }
        });
        popupBattleHistoryInfo.show();

    }

}
