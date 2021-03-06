package vn.iotech.restaurant.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import vn.iotech.circleimageview.CircleImageView;
import vn.iotech.restaurant.Models.ObjectForRecyclerViewInCart;
import vn.iotech.restaurant.R;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    ArrayList<ObjectForRecyclerViewInCart> arrayList;
    Context context;

    public AdapterCart(ArrayList<ObjectForRecyclerViewInCart> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textViewDetail;
        TextView textViewTime;
        TextView person;
        TextView money;
        TextView amount;
        Button butonAdd;
        Button buttonReduction;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.imageRecyvlerViewInCart);
            textViewDetail = (TextView) itemView.findViewById(R.id.textViewDetailInCart);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTimeInCart);
            person = (TextView) itemView.findViewById(R.id.textViewPersonInCart);
            money = (TextView) itemView.findViewById(R.id.textViewMoneyInCart);
            amount = (TextView) itemView.findViewById(R.id.textViewAmountInCart);
            butonAdd = (Button) itemView.findViewById(R.id.buttonIncreaseCart);
            buttonReduction = (Button) itemView.findViewById(R.id.buttonReductionCart);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_recycler_view_cart, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.circleImageView.setImageResource(arrayList.get(position).getImageFoods());
        holder.textViewDetail.setText(arrayList.get(position).getDetailed());
        holder.textViewTime.setText(arrayList.get(position).getTime() + " (minute)");
        holder.person.setText(arrayList.get(position).getPerson() + " (person)");
        holder.money.setText(arrayList.get(position).getMoney() + " " + arrayList.get(position).getUnitPrice());
        holder.amount.setText(arrayList.get(position).getAmout() + "");

        holder.butonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getAmout() == 0){
                    holder.buttonReduction.setClickable(true);
                }
                arrayList.get(position).setAmout(arrayList.get(position).getAmout() + 1);
                holder.amount.setText(arrayList.get(position).getAmout() + "");
            }
        });

        holder.buttonReduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).getAmout() == 0){
                    arrayList.get(position).setAmout(0);
                    holder.buttonReduction.setClickable(false);
                }
                else {
                    arrayList.get(position).setAmout(arrayList.get(position).getAmout() - 1);
                }
                holder.amount.setText(arrayList.get(position).getAmout()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
