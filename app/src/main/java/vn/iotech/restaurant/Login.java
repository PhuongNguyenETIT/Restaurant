package vn.iotech.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotech.restaurant.Models.LoginWrap;
import vn.iotech.restaurant.Models.ProfileWrap;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;
import android.provider.Settings.Secure;

public class Login extends AppCompatActivity {

    private Boolean settingCallback = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConfigsStatic.sharedPreferences = getSharedPreferences("comfigs", MODE_PRIVATE);
        ConfigsStatic.restaurantID = ConfigsStatic.sharedPreferences.getString("restaurantId", "");
        ConfigsStatic.statusAuthenticate = ConfigsStatic.sharedPreferences.getBoolean("authenticate", false);
        ConfigsStatic.idTable = ConfigsStatic.sharedPreferences.getString("idTable", "");
        ConfigsStatic.token = ConfigsStatic.sharedPreferences.getString("token", "");
        ConfigsStatic.nameTableConfig = ConfigsStatic.sharedPreferences.getString("nameTable", "");

        Intent intent = getIntent();
        settingCallback = intent.getBooleanExtra("settingBack", false);

        if (ConfigsStatic.restaurantID != "" && ConfigsStatic.idTable != ""){
            if(!ConfigsStatic.statusAuthenticate){
                Button buttonSkip = (Button) findViewById(R.id.buttonSkip);
                buttonSkip.setVisibility(View.VISIBLE);
                buttonSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Login.this, Home.class));
                    }
                });
            }
            else if(!settingCallback) {
                if(ConfigsStatic.idTable != "") {
                    startActivity(new Intent(Login.this, Home.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                else {
                    startActivity(new Intent(Login.this, SettingTable.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            }
        }

        Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
        EditText editTextUser = (EditText)findViewById(R.id.editUsernameLogin);
        EditText editTextPass = (EditText) findViewById(R.id.editPasswordLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user =  editTextUser.getText().toString();
                String pass = editTextPass.getText().toString().trim();
                if(user.length() > 0 && pass.length() > 0){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("userName", user);
                    jsonObject.addProperty("password", pass);

                    RetrofitDataClient dataClient = APIRetrofitUtils.getData();
                    Call<LoginWrap> call = dataClient.getDataLogin(jsonObject);
                    call.enqueue(new Callback<LoginWrap>() {
                        @Override
                        public void onResponse(Call<LoginWrap> call, Response<LoginWrap> response) {
                            if(response.isSuccessful()){
                                if(response.body().getMessage().indexOf("User not found")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getMessage().indexOf("Wrong password")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getData().getAuth()){
                                    ConfigsStatic.token = response.body().getData().getToken();
                                    getProfile(ConfigsStatic.token);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginWrap> call, Throwable t) {
                            Log.i("TAG", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void getProfile(String token){
        RetrofitDataClient client = APIRetrofitUtils.getData();
        Call<ProfileWrap> call = client.getProfile(token);
        call.enqueue(new Callback<ProfileWrap>() {
            @Override
            public void onResponse(Call<ProfileWrap> call, Response<ProfileWrap> response) {
                if(response.isSuccessful()){
                    if(response.body().getProfile().getRole().equals("System")){
                        View view = findViewById(R.id.layoutLogin);
                        Snackbar.make(view, "User does not have permission to login.", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        ConfigsStatic.restaurantID = response.body().getProfile().getResult().getRestaurantId();
                        SharedPreferences.Editor editor = ConfigsStatic.sharedPreferences.edit();
                        editor.putString("token", token);
                        ConfigsStatic.statusAuthenticate = true;
                        editor.putBoolean("authenticate", true);
                        editor.commit();
                        if (!ConfigsStatic.restaurantID.equals(ConfigsStatic.sharedPreferences.
                                getString("restaurantId", ""))) {
                            editor.clear();
                            editor.putString("restaurantId", ConfigsStatic.restaurantID);
                            editor.commit();
                            startActivity(new Intent(Login.this, SettingTable.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        else {
                            if (!settingCallback) {
                                if (ConfigsStatic.idTable != "") {
                                    startActivity(new Intent(Login.this, Home.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }
                                else {
                                    startActivity(new Intent(Login.this, SettingTable.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                }

                            }
                            else {
                                startActivity(new Intent(Login.this, Setting.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileWrap> call, Throwable t) {
                Log.i("TAG", "ErrorProfile");
            }
        });
    }
}
