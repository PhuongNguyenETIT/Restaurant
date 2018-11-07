package vn.iotech.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotech.restaurant.Models.DetailFoodWrap;
import vn.iotech.restaurant.Models.PreferencesCart;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;

public class DetailItemFood extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewDetailFood, textViewMoney,
            textViewPerson, textViewDuring, nameOfFood;
    private ImageView imageViewDetailFood;
    private Button buttonAddToCart;
    private ArrayList<PreferencesCart> cartArrayList = new ArrayList<>();
    private PreferencesCart preferencesCart = new PreferencesCart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_food);

        mapped();
        buttonBackToolbar();

        Intent intent = getIntent();
        String id = intent.getStringExtra("byID");
        RetrofitDataClient client = APIRetrofitUtils.getData();
        Call<DetailFoodWrap> call = client.getDetialFood(id);
        call.enqueue(new Callback<DetailFoodWrap>() {
            @Override
            public void onResponse(Call<DetailFoodWrap> call, Response<DetailFoodWrap> response) {
                if(response.isSuccessful()){

                    String name = response.body().getData().getName();
                    Double price = response.body().getData().getPrice();
                    String unitPrice = response.body().getData().getUnitPrice();
                    Integer during = response.body().getData().getDuring();
                    Integer people = response.body().getData().getPeople();

                    nameOfFood.setText(name);
                    Picasso.get().load(ConfigsStatic.domainImage + response.body().getData().getImage())
                            .error(R.drawable.default_image)
                            .into(imageViewDetailFood);
                    textViewMoney.setText(price + " "
                            + unitPrice);
                    textViewDuring.setText(during + " (minutes)");
                    textViewPerson.setText(people +" (person)");
                    textViewDetailFood.setText(response.body().getData().getDescription());

                    preferencesCart.setId(response.body().getData().getId());
                    preferencesCart.setName(name);
                    preferencesCart.setState("book");
                    preferencesCart.setAmount(0);
                    preferencesCart.setPrice(price);
                    preferencesCart.setUnitPrice(unitPrice);
                    preferencesCart.setDuring(during);
                    preferencesCart.setPeople(people);
                }
            }

            @Override
            public void onFailure(Call<DetailFoodWrap> call, Throwable t) {
                Log.i("TAG", t.getMessage());
            }
        });

        addToCart();

    }


    private void addToCart(){
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                cartArrayList.clear();
            }
        });
    }
    private void buttonBackToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarDetailFood);
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_48dp);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        final Drawable newDrawble = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap,48, 48, true));
        newDrawble.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newDrawble);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailItemFood.this, Home.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    private void mapped(){
        nameOfFood = (TextView)findViewById(R.id.nameOfFood);
        textViewDetailFood = (TextView) findViewById(R.id.textViewDetailFood);
        imageViewDetailFood = (ImageView)findViewById(R.id.imageDetailFood);
        textViewMoney = (TextView) findViewById(R.id.textViewMoneyDetailFood);
        textViewDuring = (TextView) findViewById(R.id.textViewTimeDetailFood);
        textViewPerson = (TextView) findViewById(R.id.textViewPersonDetailFood);
        buttonAddToCart = (Button) findViewById(R.id.buttonAddToCartDetailFood);
    }

}
