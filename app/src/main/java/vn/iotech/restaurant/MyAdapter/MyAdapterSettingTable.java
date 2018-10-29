package vn.iotech.restaurant.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;
import vn.iotech.restaurant.ObjectOriented.OjectForGridViewSettingTable;
import vn.iotech.restaurant.R;

public class MyAdapterSettingTable extends BaseAdapter {

    private Context context;
    private int layout;
    private List<OjectForGridViewSettingTable> tableConfigList;

    public MyAdapterSettingTable(Context context, int layout, List<OjectForGridViewSettingTable> tableConfigList) {
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
        ImageView imageViewBooking;
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
            viewHolder.imageViewBooking = (ImageView) view.findViewById(R.id.imageViewBookingTable);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        OjectForGridViewSettingTable ojectTableConfig = tableConfigList.get(i);
        viewHolder.textViewName.setText(ojectTableConfig.getNameTable());
        //viewHolder.textViewName.setBackgroundResource(ojectTableConfig.getBackgroundTable());
        viewHolder.imageViewBooking.setImageResource(ojectTableConfig.getImageBooking());
        viewHolder.relativeLayout.setBackgroundResource(ojectTableConfig.getBackgroundTable());
        return view;
    }
}
