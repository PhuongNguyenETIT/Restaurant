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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotech.restaurant.Adapters.AdapterSettingTable;
import vn.iotech.restaurant.Models.RequestWrap;
import vn.iotech.restaurant.Models.Table;
import vn.iotech.restaurant.Models.TableWrap;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;
import vn.iotech.rxwebsocket.RxWebSocket;

public class SettingTable extends AppCompatActivity {

    private Toolbar toolbar;
    private RxWebSocket rxWebSocketTable;
    private ArrayList<Table> arrayListTable = new ArrayList<>();
    private AdapterSettingTable adapterConfigTable;
    private GridView gridView;

    private Boolean changeTable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_table);

        ConfigsStatic.idTable = ConfigsStatic.sharedPreferences.getString("idTable", "");

        buttonBackToolbar();

        gridViewTabbleSetting();

        Button buttonSave = (Button) findViewById(R.id.buttonSaveSettingTable);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConfigsStatic.idTable != "" && changeTable){
                    JsonArray jsonArray = new JsonArray();
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("_id", ConfigsStatic.idTable);
                    jsonObject1.addProperty("_restaurantId", ConfigsStatic.restaurantID);
                    jsonObject1.addProperty("setting", true);
                    if(ConfigsStatic.sharedPreferences.getString("idTable", "") != ""){
                        JsonObject jsonObject2 = new JsonObject();
                        jsonObject2.addProperty("_id", ConfigsStatic.sharedPreferences.getString("idTable", ""));
                        jsonObject2.addProperty("_restaurantId", ConfigsStatic.restaurantID);
                        jsonObject2.addProperty("setting", false);
                        jsonArray.add(jsonObject2);
                    }
                    jsonArray.add(jsonObject1);

                    RetrofitDataClient client = APIRetrofitUtils.getData();
                    Call<RequestWrap> call = client.settingTable(ConfigsStatic.token, jsonArray);
                    call.enqueue(new Callback<RequestWrap>() {
                        @Override
                        public void onResponse(Call<RequestWrap> call, Response<RequestWrap> response) {
                            if(response.isSuccessful()) {
                                if(response.body().getData().getLength() == 1) {
                                    SharedPreferences.Editor editor = ConfigsStatic.sharedPreferences.edit();
                                    editor.putString("idTable", ConfigsStatic.idTable);
                                    editor.putString("nameTable", ConfigsStatic.nameTableConfig);
                                    editor.commit();
                                    Toast.makeText(SettingTable.this, "Saved", Toast.LENGTH_SHORT).show();
                                    changeTable = false;
                                }
                                else {
                                    Toast.makeText(SettingTable.this, "Save error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestWrap> call, Throwable t) {
                            Toast.makeText(SettingTable.this, "Save error. Can not connect to the system.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(SettingTable.this, "You have not set a new table.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        rxWebSocketTable(ConfigsStatic.domainWSTable + ConfigsStatic.restaurantID);

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
                    TableWrap tableWrap = new Gson().fromJson(socketMessageEvent.getText(), TableWrap.class);
                    if(tableWrap.getOperationType().equals("get")){
                        arrayListTable.clear();
                        for (int i = 0; i < tableWrap.getData().size(); i++) {
                            arrayListTable.add(tableWrap.getData().get(i));
                        }
                    }
                    else if(tableWrap.getOperationType().equals("insert")){
                        arrayListTable.add(tableWrap.getData().get(0));
                    }
                    else if(tableWrap.getOperationType().equals("update")){
                        for (int i = 0; i < arrayListTable.size(); i++){
                            if(arrayListTable.get(i).getId().equals(tableWrap.getDocumentKey())){
                                arrayListTable.set(i, tableWrap.getData().get(0));
                                break;
                            }
                        }
                    }
                    else if (tableWrap.getOperationType().equals("delete")){
                        for (int i = 0; i < arrayListTable.size(); i++){
                            if(arrayListTable.get(i).getId().equals(tableWrap.getDocumentKey())){
                                arrayListTable.remove(i);
                                SharedPreferences.Editor editor = ConfigsStatic.sharedPreferences.edit();
                                editor.remove("idTable");
                                editor.commit();
                                break;
                            }
                        }
                    }
                    adapterConfigTable.notifyDataSetChanged();
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
        gridView = (GridView) findViewById(R.id.gridViewDialogConfigTable);
        adapterConfigTable = new AdapterSettingTable(this, R.layout.custom_table_setting, arrayListTable);
        gridView.setAdapter(adapterConfigTable);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundResource(R.drawable.custom_button_background_reddark);
                if(ConfigsStatic.idTable != "" && ConfigsStatic.idTable != arrayListTable.get(i).getId()){
                    for (int j = 0; j < arrayListTable.size(); j++){
                        if(arrayListTable.get(j).getId().equals(ConfigsStatic.idTable)){
                            view = gridView.getChildAt(j);
                            view.setBackgroundResource(R.drawable.custom_button_background_white_dark);
                            break;
                        }
                    }
                }
                ConfigsStatic.idTable = arrayListTable.get(i).getId();
                ConfigsStatic.nameTableConfig = arrayListTable.get(i).getName();
                changeTable = true;
                Toast.makeText(SettingTable.this, arrayListTable.get(i).getName(), 100).show();
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
                String table = ConfigsStatic.sharedPreferences.getString("idTable", "");
                if(table != "") {
                    Intent intent = new Intent(SettingTable.this, Setting.class);
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                else {
                    Toast.makeText(SettingTable.this, "You have not set the number of tables",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        disconnectWSTable();
    }
}
