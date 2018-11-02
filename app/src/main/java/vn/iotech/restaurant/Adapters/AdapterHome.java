package vn.iotech.restaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.iotech.restaurant.ConfigsStatic;
import vn.iotech.restaurant.DetailItemRestaurant;
import vn.iotech.restaurant.Models.Food;
import vn.iotech.restaurant.Models.FoodWrap;
import vn.iotech.restaurant.Models.ObjectForRecyclerViewHome;
import vn.iotech.restaurant.R;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{

    ArrayList<Food> arrayListFoods;
    Context context;

    public AdapterHome(ArrayList<Food> arrayListFoods, Context context){
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
        Picasso.get().load(ConfigsStatic.domainImage+arrayListFoods.get(position).getImage())
                .into(holder.imageViewFood);

        holder.textViewDetail.setText(arrayListFoods.get(position).getName());
        holder.textViewTime.setText(arrayListFoods.get(position).getDuring() + " (minutes)");
        holder.person.setText(arrayListFoods.get(position).getPeople() + " (person)");
        holder.money.setText(arrayListFoods.get(position).getPrice() +" "+ arrayListFoods.get(position).getUnitPrice());
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
