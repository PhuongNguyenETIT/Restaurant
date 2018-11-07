package vn.iotech.restaurant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import vn.iotech.restaurant.ConfigsStatic;
import vn.iotech.restaurant.DetailItemFood;
import vn.iotech.restaurant.Home;
import vn.iotech.restaurant.Models.Food;
import vn.iotech.restaurant.Models.PreferencesCart;
import vn.iotech.restaurant.R;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{

    ArrayList<Food> arrayListFoods;
    Context context;
    PreferencesCart preferencesCart = new PreferencesCart();
    ArrayList<PreferencesCart> cartArrayList = new ArrayList<>();

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
        Button buttonAddToCart;
        RelativeLayout relativeLayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            imageViewFood = (ImageView) itemView.findViewById(R.id.imageFood);
            textViewDetail = (TextView) itemView.findViewById(R.id.textViewDetailed);
            textViewTime = (TextView) itemView.findViewById(R.id.textViewTimeChoose);
            person = (TextView) itemView.findViewById(R.id.textViewPerson);
            money = (TextView) itemView.findViewById(R.id.textViewMoney);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.layoutClickRecylerView);
            buttonAddToCart = (Button) itemView.findViewById(R.id.buttonAdtoCart);
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
                .error(R.drawable.default_image)
                .into(holder.imageViewFood);

        holder.textViewDetail.setText(arrayListFoods.get(position).getName());
        holder.textViewTime.setText(arrayListFoods.get(position).getDuring() + " (minutes)");
        holder.person.setText(arrayListFoods.get(position).getPeople() + " (person)");
        holder.money.setText(arrayListFoods.get(position).getPrice() +" "+ arrayListFoods.get(position).getUnitPrice());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailItemFood.class);
                intent.putExtra("byID", arrayListFoods.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });

        holder.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencesCart.setId(arrayListFoods.get(position).getId());
                preferencesCart.setName(arrayListFoods.get(position).getName());
                preferencesCart.setState("book");
                preferencesCart.setAmount(0);
                preferencesCart.setPrice(arrayListFoods.get(position).getPrice());
                preferencesCart.setUnitPrice(arrayListFoods.get(position).getUnitPrice());
                preferencesCart.setDuring(arrayListFoods.get(position).getDuring());
                preferencesCart.setPeople(arrayListFoods.get(position).getPeople());
                Gson gson = new Gson();
                String stringJson = ConfigsStatic.preferencesCart.getString("objectCart", "");
                if(stringJson != "") {
                    Type type = new TypeToken<List<PreferencesCart>>(){}.getType();
                    ArrayList<PreferencesCart> arrayList = gson.fromJson(stringJson, type);
                    for (int i = 0; i < arrayList.size(); i++) {
                        cartArrayList.add(arrayList.get(i));
                    }
                }
                cartArrayList.add(preferencesCart);
                String conJson = gson.toJson(cartArrayList);
                SharedPreferences.Editor editor = ConfigsStatic.preferencesCart.edit();
                editor.putString("objectCart", conJson);
                editor.commit();
                String txt = "There are <font color='red'>" + cartArrayList.size() + "</font> items in Cart";
                Home.textViewItems.setText(Html.fromHtml(txt), TextView.BufferType.SPANNABLE);
                cartArrayList.clear();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListFoods.size();
    }

}
