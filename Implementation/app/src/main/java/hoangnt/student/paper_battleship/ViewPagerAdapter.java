package hoangnt.student.paper_battleship;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    Resources resources;
    ArrayList<Item> listItem;
    ArrayList<BattleHistory> listBattleHistory;
    Integer[] listImageId;
    Integer[] listDescriptionId;
    String packageName;
    public ViewPagerAdapter(FragmentManager fm, Resources resources, ArrayList<Item> listItem, Integer[] listImageId, Integer[] listDescriptionId, ArrayList<BattleHistory> listBattleHistory, Context context, String packageName) {
        super(fm);
        this.resources = resources;
        this.listItem = listItem;
        this.listImageId = listImageId;
        this.listDescriptionId = listDescriptionId;
        this.listBattleHistory = listBattleHistory;
        this.context = context;
        this.packageName = packageName;
    }

    @Override
    public Fragment getItem(int position) {
        position = position + 1;
        switch (position){
            case 1: return new FragmentItem(listItem, listImageId, listDescriptionId , context, resources);
            case 2: return new FragmentSkin();
            case 3: return new FragmentBattleHistory(listBattleHistory, resources, context, packageName);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position + 1;
        switch (position){
            case 1: return resources.getString(R.string.item_tab_name);
            case 2: return resources.getString(R.string.skin_tab_name);
            case 3: return resources.getString(R.string.battle_history_tab_name);
        }
        return "Fragment " + position;
    }

}
