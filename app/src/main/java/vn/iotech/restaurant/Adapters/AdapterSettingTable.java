package vn.iotech.restaurant.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;

import vn.iotech.restaurant.ConfigsStatic;
import vn.iotech.restaurant.Models.ObjectForGridViewSettingTable;
import vn.iotech.restaurant.Models.Table;
import vn.iotech.restaurant.Models.TableWrap;
import vn.iotech.restaurant.R;

public class AdapterSettingTable extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Table> tableConfigList;

    public AdapterSettingTable(Context context, int layout, List<Table> tableConfigList) {
        this.context = context;
        this.layout = layout;
        this.tableConfigList = tableConfigList;
    }

    @Override
    public int getCount() {
        return tableConfigList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewName;
        RelativeLayout relativeLayout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.textViewName = (TextView) view.findViewById(R.id.textViewNameTable);
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.layoutTabblSetting);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Table table = tableConfigList.get(i);
        viewHolder.textViewName.setText(table.getName());
        if(table.getSetting()){
            viewHolder.textViewName.setTextColor(Color.argb(255, 153, 0, 51));
            if(table.getId().equals(ConfigsStatic.idTable)){
                viewHolder.textViewName.setTextColor(Color.GREEN);
                viewHolder.relativeLayout.setBackgroundResource(R.drawable.custom_button_background_reddark);
            }
        }
        else {
            viewHolder.relativeLayout.setBackgroundResource(R.drawable.custom_button_background_white_dark);
            viewHolder.textViewName.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        if (tableConfigList.get(position).getSetting()) {
            return false;
        }
        return true;
    }
}
