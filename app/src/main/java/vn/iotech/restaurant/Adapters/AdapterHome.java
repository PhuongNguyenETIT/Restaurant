package vn.iotech.restaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.iotech.restaurant.DetailItemRestaurant;
import vn.iotech.restaurant.Models.ObjectForRecyclerViewHome;
import vn.iotech.restaurant.R;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{

    ArrayList<ObjectForRecyclerViewHome> arrayListFoods;
    Context context;

    public AdapterHome(ArrayList<ObjectForRecyclerViewHome> arrayListFoods, Context context){
        this.arrayListFoods = arrayListFoods;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewFood;
        TextView textViewDetail;
        TextView textViewTime;
        TextView person;
        TextView money;
        RelativeLayout relativeLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageViewFood = (ImageView) itemView.findViewById(R.id.imageFood);
            textViewDetail = (TextView) itemView.findViewById(R.id.textViewDetailed);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTimeChoose);
            person = (TextView) itemView.findViewById(R.id.textViewPerson);
            money = (TextView) itemView.findViewById(R.id.textViewMoney);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layoutClickRecylerView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, DetailItemFood.class);
//                    view.getContext().startActivity(intent);
//                }
//            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_recyclerview_home, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageViewFood.setImageResource(arrayListFoods.get(position).getImageFoods());
        holder.textViewDetail.setText(arrayListFoods.get(position).getDetailed());
        holder.textViewTime.setText(arrayListFoods.get(position).getTime() + " (minutes)");
        holder.person.setText(arrayListFoods.get(position).getPerson() + " (person)");
        holder.money.setText(arrayListFoods.get(position).getMoney() + " USD");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailItemRestaurant.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListFoods.size();
    }

}