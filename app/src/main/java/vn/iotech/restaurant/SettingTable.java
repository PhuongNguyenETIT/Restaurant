package vn.iotech.restaurant;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.iotech.restaurant.Adapters.AdapterSettingTable;
import vn.iotech.restaurant.Models.FoodWrap;
import vn.iotech.restaurant.Models.ObjectForGridViewSettingTable;
import vn.iotech.rxwebsocket.RxWebSocket;

public class SettingTable extends AppCompatActivity {

    Toolbar toolbar;
    private RxWebSocket rxWebSocketTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_table);

        buttonBackToolbar();

        rxWebSocketTable(ConfigsStatic.domainWSTable + ConfigsStatic.restaurantID);

        gridViewTabbleSetting();

        Button buttonSave = (Button) findViewById(R.id.buttonSaveSettingTable);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConfigsStatic.numberTable >= 0){
                    SharedPreferences.Editor editor = ConfigsStatic.sharedPreferences.edit();
                    editor.putInt("numbertable", ConfigsStatic.numberTable);
                    editor.commit();
                    Toast.makeText(SettingTable.this, "Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SettingTable.this,
                            "You have not set the number of tables", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void rxWebSocketTable(String url){
        rxWebSocketTable = new RxWebSocket(url);
        rxWebSocketTable.onOpen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketOpenEvent -> {
                    //Log.i("TAG", "socketStart");
                }, Throwable::printStackTrace);

        rxWebSocketTable.onClosed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosedEvent -> {
                    //Log.i("TAG", socketClosedEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketTable.onClosing()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketClosingEvent -> {
                    //Log.i("TAG", socketClosingEvent.toString());
                }, Throwable::printStackTrace);

        rxWebSocketTable.onTextMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketMessageEvent -> {
                    Log.i("TAG", socketMessageEvent.getText().toString());
                });

        rxWebSocketTable.onFailure()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketFailureEvent -> {
                    //Log.i("TAG", socketFailureEvent.getResponse().toString());
                }, Throwable::printStackTrace);

        rxWebSocketTable.connect();
    }

    private void disconnectWSTable(){
        rxWebSocketTable.close()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {}
                        ,Throwable::printStackTrace);
    }

    private void gridViewTabbleSetting(){
        GridView gridView = (GridView) findViewById(R.id.gridViewDialogConfigTable);
        final ArrayList<ObjectForGridViewSettingTable> arrayList;
        final AdapterSettingTable adapterConfigTable;
        arrayList = new ArrayList<>();
        for (int i = 1; i < 20; i++){
            for (int j = 1; j < 5; j++){
                arrayList.add(new ObjectForGridViewSettingTable(i*100 + j + "", R.drawable.custom_button_background_white_dark,
                        R.drawable.ic_shopping_cart_c9c9c9_24dp));
            }
        }
        if(ConfigsStatic.numberTable >= 0){
            arrayList.get(ConfigsStatic.numberTable).setBackgroundTable(R.drawable.custom_button_background_reddark);
        }
        adapterConfigTable = new AdapterSettingTable(this, R.layout.custom_table_setting, arrayList);
        gridView.setAdapter(adapterConfigTable);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SettingTable.this, arrayList.get(i).getNameTable(), 100).show();
                arrayList.get(i).setBackgroundTable(R.drawable.custom_button_background_reddark);
                if(ConfigsStatic.numberTable >= 0 && ConfigsStatic.numberTable != i){
                    arrayList.get(ConfigsStatic.numberTable).setBackgroundTable(R.drawable.custom_button_background_white_dark);
                }
                ConfigsStatic.numberTable = i;
                adapterConfigTable.notifyDataSetChanged();
            }
        });
    }

    private void buttonBackToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSettingTable);
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
                Intent intent = new Intent(SettingTable.this, Setting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectWSTable();
    }
}
