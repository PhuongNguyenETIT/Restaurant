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
import android.view.View;
import android.widget.TextView;

public class DetailItemRestaurant extends AppCompatActivity {

    Toolbar toolbar;
    TextView textViewInforDetailFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_restaurant);
        mapped();

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
                startActivity(intent);
            }
        });
        textViewInforDetailFood.setText("Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography.Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography. Learn how to customize Material Design to change the look and feel of your UI, expressing brand and style through elements like color, shape, typography, and iconography.");
        textViewInforDetailFood.append("    ");
    }

    private void mapped(){
        textViewInforDetailFood = (TextView) findViewById(R.id.textViewInforDetailFood);
        toolbar = (Toolbar) findViewById(R.id.toolbarDetailFood);
    }
}
