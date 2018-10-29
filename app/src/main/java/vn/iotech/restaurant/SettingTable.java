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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import java.util.ArrayList;

import vn.iotech.restaurant.MyAdapter.MyAdapterSettingTable;
import vn.iotech.restaurant.ObjectOriented.OjectForGridViewSettingTable;

public class SettingTable extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_table);

        ButtonBackToolbar();

        GridViewTabbleSetting();
    }

    private void GridViewTabbleSetting(){
        GridView gridView = (GridView) findViewById(R.id.gridViewDialogConfigTable);
        final ArrayList<OjectForGridViewSettingTable> arrayList;
        final MyAdapterSettingTable adapterConfigTable;
        arrayList = new ArrayList<>();
        for (int i = 1; i < 20; i++){
            for (int j = 1; j < 5; j++){
                arrayList.add(new OjectForGridViewSettingTable(i*100 + j + "", R.drawable.custom_button_background_white_dark,
                        R.drawable.ic_shopping_cart_c9c9c9_24dp));
            }
        }
        adapterConfigTable = new MyAdapterSettingTable(this, R.layout.custom_table_setting, arrayList);
        gridView.setAdapter(adapterConfigTable);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SettingTable.this, arrayList.get(i).getNameTable(), Toast.LENGTH_SHORT).show();
                arrayList.get(i).setBackgroundTable(R.drawable.custom_button_background_reddark);
                adapterConfigTable.notifyDataSetChanged();
            }
        });
    }

    private void ButtonBackToolbar() {
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
}