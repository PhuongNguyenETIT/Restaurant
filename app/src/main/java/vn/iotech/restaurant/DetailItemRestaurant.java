package vn.iotech.restaurant;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotech.restaurant.Models.DetailFoodWrap;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;

public class DetailItemRestaurant extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewDetailFood, textViewMoney,
            textViewPerson, textViewDuring, nameOfFood;
    private ImageView imageViewDetailFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_restaurant);

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
                    nameOfFood.setText(response.body().getData().getName());
                    Picasso.get().load(ConfigsStatic.domainImage + response.body().getData().getImage())
                            .error(R.drawable.default_image)
                            .into(imageViewDetailFood);
                    textViewMoney.setText(response.body().getData().getPrice()+" "
                            +response.body().getData().getUnitPrice());
                    textViewDuring.setText(response.body().getData().getDuring()+" (minutes)");
                    textViewPerson.setText(response.body().getData().getPeople()+" (person)");
                    textViewDetailFood.setText(response.body().getData().getDescription());
                }
            }

            @Override
            public void onFailure(Call<DetailFoodWrap> call, Throwable t) {
                Log.i("TAG", t.getMessage());
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
                Intent intent = new Intent(DetailItemRestaurant.this, Home.class);
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
    }

}
