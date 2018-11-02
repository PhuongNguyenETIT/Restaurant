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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotech.restaurant.Adapters.AdapterHome;
import vn.iotech.restaurant.Models.CategoryWrap;
import vn.iotech.restaurant.Models.Food;
import vn.iotech.restaurant.Models.FoodWrap;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;
import vn.iotech.rxwebsocket.RxWebSocket;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    Button buttonViewCart;
    TextView textViewItems;
    NavigationView navigationView;

    RxWebSocket rxWebSocketHome, rxWebSocketCategories;
    CategoryWrap categoryWrap = null;
    ArrayList<Food> arrayListFood = new ArrayList<>();
    AdapterHome homeAdapter;

    Boolean connectedWSHome = false;

    //ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //viewPager = (ViewPager) findViewById(R.id.viewPagerHome);

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
                if(ConfigsStatic.statusAuthenticate) {
                    startActivity(new Intent(Home.this, Setting.class));
                }
                else {
                    startActivity(new Intent(Home.this, Login.class)
                            .putExtra("settingBack", true));
                }
            }
        });

        textViewItems =(TextView) findViewById(R.id.totalItems);
        String txt = "There are <font color='red'>14</font> items in Cart";
        textViewItems.setText(Html.fromHtml(txt), TextView.BufferType.SPANNABLE);

        selectItemsNavigationView();

        rxWebSocketHome(ConfigsStatic.domainWSHome + ConfigsStatic.restaurantID);

        rxWebSocketCategories(ConfigsStatic.domainWSCategory + ConfigsStatic.restaurantID);

        buttonBackToolbar();

        initRecyclerView();
    }

    private void selectItemsNavigationView(){
        navigationView = findViewById(R.id.menuNaviga);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuHome){
                    if(!connectedWSHome){
                        rxWebSocketHome(ConfigsStatic.domainWSHome + ConfigsStatic.restaurantID);
                    }
                }
                else {
                    RetrofitDataClient client = APIRetrofitUtils.getData();
                    Call<FoodWrap> call = client.getCategoryFood(categoryWrap.getData().get(item.getItemId()).getId());
                    call.enqueue(new Callback<FoodWrap>() {
                        @Override
                        public void onResponse(Call<FoodWrap> call, Response<FoodWrap> response) {
                            if(response.isSuccessful()){
                                disconnectWSHome();
                                arrayListFood.clear();
                                if(response.body().getData().size()>0) {
                                    for (int i=0; i < response.body().getData().size(); i++) {
                                        arrayListFood.add(response.body().getData().get(i));
                                    }
                                }
                                homeAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<FoodWrap> call, Throwable t) {
                            Log.i("TAG", "cateFail");
                        }
                    });
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void rxWebSocketHome(String url){
        rxWebSocketHome = new RxWebSocket(url);
        rxWebSocketHome.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {
                    //Log.i("TAG", "socketStart");
                    connectedWSHome = true;
                }, Throwable::printStackTrace);

        rxWebSocketHome.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    //Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketHome.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    //Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketHome.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    FoodWrap foodwrapRealtime = new Gson().fromJson(socketMessageEvent.getText(), FoodWrap.class);
                    if(foodwrapRealtime.getOperationType().equals("get")) {
                        arrayListFood.clear();
                        for (int i = 0; i < foodwrapRealtime.getData().size(); i++) {
                            arrayListFood.add(foodwrapRealtime.getData().get(i));
                        }
                    }
                    else if(foodwrapRealtime.getOperationType().equals("insert")){
                        arrayListFood.add(foodwrapRealtime.getData().get(0));
                    }
                    else if(foodwrapRealtime.getOperationType().equals("update")){
                        for (int i = 0; i < arrayListFood.size(); i++){
                            if(foodwrapRealtime.getData().get(0).getId().equals(arrayListFood.get(i).getId())){
                                arrayListFood.set(i, foodwrapRealtime.getData().get(0));
                            }
                        }
                    }
                    else if(foodwrapRealtime.getOperationType().equals("delete")){
                        for(int i = 0; i < arrayListFood.size(); i++) {
                            if(arrayListFood.get(i).getId().equals(foodwrapRealtime.getDocumentKey())){
                                arrayListFood.remove(i);
                                break;
                            }
                        }
                    }
                    homeAdapter.notifyDataSetChanged();
                });

        rxWebSocketHome.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    //Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocketHome.connect();
    }

    public void rxWebSocketCategories(String url){
        rxWebSocketCategories = new RxWebSocket(url);
        rxWebSocketCategories.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {
                    //Log.i("TAG", socketOpenEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketCategories.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    //Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketCategories.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    //Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketCategories.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    CategoryWrap categoriesRealtime =
                            new Gson().fromJson(socketMessageEvent.getText(),
                                    CategoryWrap.class);

                    String operationType = categoriesRealtime.getOperationType();

                    Menu menu = navigationView.getMenu();

                    if(operationType.equals("get")) {
                        for (int i = 0; i < categoriesRealtime.getData().size(); i++) {
                            menu.add(Menu.FIRST, i, Menu.FIRST,
                                    categoriesRealtime.getData().get(i).getName());
                        }
                        categoryWrap = categoriesRealtime;
                    }
                    else if(operationType.equals("insert")){
                        menu.add(Menu.FIRST, navigationView.getMenu().size()+1, Menu.FIRST,
                                categoriesRealtime.getData().get(0).getName());
                        categoryWrap.getData().add(categoriesRealtime.getData().get(0));
                    }
                    else if(operationType.equals("update")){
                        for(int i = 0; i < menu.size(); i++) {
                            if(categoriesRealtime.getData().get(0).getId()
                                    .equals(categoryWrap.getData().get(i).getId())){
                                categoryWrap.getData().set(i, categoriesRealtime.getData().get(0));
                                menu.getItem(i).setTitle(categoryWrap.getData().get(i).getName());
                                break;
                            }
                        }
                    }
                    else if(operationType.equals("delete")){
                        for(int i = 0; i < menu.size(); i++) {
                            if(categoriesRealtime.getDocumentKey()
                                    .equals(categoryWrap.getData().get(i).getId())){
                                categoryWrap.getData()
                                        .remove(categoryWrap.getData().get(i));
                                categoryWrap.getData().remove(i);
                                menu.removeItem(menu.getItem(i).getItemId());
                                break;
                            }
                        }
                    }
                });

        rxWebSocketCategories.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    //Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocketCategories.connect();
    }

    private void buttonBackToolbar() {
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
        homeAdapter = new AdapterHome(arrayListFood, getApplicationContext());
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disconnectWSHome(){
        rxWebSocketHome.close()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {connectedWSHome = false;}
                ,Throwable::printStackTrace);
    }

    private void disconnectWSCategories(){
        rxWebSocketCategories.close()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {}
                ,Throwable::printStackTrace);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectWSHome();
        disconnectWSCategories();
    }
}
