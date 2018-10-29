package vn.iotech.restaurant.MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import vn.iotech.restaurant.ObjectOriented.OjectForRecyclerViewInVoice;
import vn.iotech.restaurant.R;

public class MyAdapterForRecylerViewInvoice extends RecyclerView.Adapter<MyAdapterForRecylerViewInvoice.ViewHolder>{

    ArrayList<OjectForRecyclerViewInVoice> ojectForListViewInVoices;
    Context context;

    public MyAdapterForRecylerViewInvoice(ArrayList<OjectForRecyclerViewInVoice> ojectForListViewInVoices, Context context) {
        this.ojectForListViewInVoices = ojectForListViewInVoices;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNoNumber;
        TextView textViewName;
        TextView textViewQuality;
        TextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNoNumber = (TextView) itemView.findViewById(R.id.textViewNumberNo);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewQuality = (TextView) itemView.findViewById(R.id.textViewQuality);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_recycler_view_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewNoNumber.setText("#"+ojectForListViewInVoices.get(position).getNumberNo());
        holder.textViewName.setText(ojectForListViewInVoices.get(position).getNameRestaurant());
        holder.textViewQuality.setText(ojectForListViewInVoices.get(position).getQuality()+"");
        holder.textViewPrice.setText(ojectForListViewInVoices.get(position).getPrice()+" USD");
    }

    @Override
    public int getItemCount() {
        return ojectForListViewInVoices.size();
    }

}
