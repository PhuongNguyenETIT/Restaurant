package vn.iotech.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.iotech.restaurant.MyAdapter.MyAdapterRecyclerViewHome;
import vn.iotech.restaurant.ObjectOriented.ObjectForCategoriesRealtime;
import vn.iotech.restaurant.ObjectOriented.ObjectForRecyclerViewHome;
import vn.iotech.rxwebsocket.RxWebSocket;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    Button buttonViewCart;
    TextView textViewItems;
    NavigationView navigationView;

    RxWebSocket rxWebSocketForHome, rxWebSocketForCategories;


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

        //initRxWebSocketForHome("ws://13.67.35.142:8686/categories");

        initRxWebSocketForCategories("ws://13.67.35.142:8682/api/realtime/categories?resId=5bd6cf7a2741e02c68de5028");

        buttonBackToolbar();

        initRecyclerView();
    }

    private void SelectItemsNavigationView(){
        navigationView = findViewById(R.id.menuNaviga);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                    Toast.makeText(Home.this, item.getItemId()+"", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                return true;

            }
        });
    }

    public void initRxWebSocketForHome(String url){
        rxWebSocketForHome = new RxWebSocket(url);
        rxWebSocketForHome.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {

                }, Throwable::printStackTrace);

        rxWebSocketForHome.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketForHome.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketForHome.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    Log.i("TAG", socketMessageEvent.toString());
                });

        rxWebSocketForHome.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocketForHome.connect();
    }

    public void initRxWebSocketForCategories(String url){
        rxWebSocketForCategories = new RxWebSocket(url);
        rxWebSocketForCategories.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {
                    Log.i("TAG", socketOpenEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketForCategories.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketForCategories.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketForCategories.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    ObjectForCategoriesRealtime categoriesRealtime =
                            new Gson().fromJson(socketMessageEvent.getText(),
                                    ObjectForCategoriesRealtime.class);

                    for (int i = 0; i < categoriesRealtime.getData().size(); i++) {
                        navigationView.getMenu().add(Menu.FIRST, i, Menu.FIRST,
                                categoriesRealtime.getData().get(i).getName());
                    }

                });

        rxWebSocketForCategories.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocketForCategories.connect();
    }

    private void buttonBackToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarFoods);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerMenuHome);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Home.this, drawerLayout, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
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
        ArrayList<ObjectForRecyclerViewHome> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(new ObjectForRecyclerViewHome(R.drawable.thuc_don, "Ditail View Ditail View Ditail View Ditail View Ditail View Ditail View Ditail View", 15, 4, 45.76));
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
