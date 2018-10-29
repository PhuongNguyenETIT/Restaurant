package vn.iotech.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import vn.iotech.restaurant.MyAdapter.MyAdapterRecyclerViewHome;
import vn.iotech.restaurant.ObjectOriented.OjectForRecyclerViewHome;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    Button buttonViewCart;
    TextView textViewItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonViewCart = (Button) findViewById(R.id.buttonViewCart);
        buttonViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Cart.class));
            }
        });


        ImageButton imgButtonSetting = (ImageButton) findViewById(R.id.buttonSetting);
        imgButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,Setting.class));
            }
        });

        textViewItems =(TextView) findViewById(R.id.totalItems);
        String txt = "There are <font color='red'>14</font> items in Cart";
        textViewItems.setText(Html.fromHtml(txt), TextView.BufferType.SPANNABLE);

        SelectItemsNavigationView();

        ButtonBackToolbar();

        initRecyclerView();
    }

    private void SelectItemsNavigationView(){
        NavigationView navigationView = findViewById(R.id.menuNaviga);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                item.setChecked(true);
//                switch (item.getItemId()) {
//                    case R.id.menuOutbox:
//                        Toast.makeText(Home.this, "Ok", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawers();
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    private void ButtonBackToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFoods);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMenuHome);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu_black_48dp);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        Drawable newDrawble = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap,48, 48, true));
        newDrawble.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newDrawble);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CustomDivider customDivider = new CustomDivider(recyclerView.getContext(), 147, 0);
        recyclerView.addItemDecoration(customDivider);
        ArrayList<OjectForRecyclerViewHome> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(new OjectForRecyclerViewHome(R.drawable.thuc_don, "Ditail View Ditail View Ditail View Ditail View Ditail View Ditail View Ditail View", 15, 4, 45.76));
        }
        MyAdapterRecyclerViewHome homeAdapter = new MyAdapterRecyclerViewHome(arrayList, getApplicationContext());
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
