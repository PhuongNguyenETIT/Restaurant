package vn.iotech.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import vn.iotech.restaurant.Adapters.AdapterInvoice;
import vn.iotech.restaurant.Models.ObjectForRecyclerViewInVoice;

public class Invoice extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        buttonBackToolbar();

        InitRecyclerView();
    }

    private void buttonBackToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarInvoice);
        setSupportActionBar(toolbar);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_48dp);

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        final Drawable drawbleNew = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap,48, 48, true));
        drawbleNew.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawbleNew);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Invoice.this, Cart.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    private void InitRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewInvoice);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CustomDivider customDivider = new CustomDivider(recyclerView.getContext(), 30, 30);
        recyclerView.addItemDecoration(customDivider);
        ArrayList<ObjectForRecyclerViewInVoice> arrayList = new ArrayList<>();
        for (int i = 1; i <=50; i++){
            arrayList.add(new ObjectForRecyclerViewInVoice(i, "French Fries Recipe | Steffi's Recipes French Fries Recipe | Steffi's Recipes",
                    34, 423.02));
        }
        AdapterInvoice adapter = new AdapterInvoice(arrayList, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}

