package vn.iotech.restaurant;

import android.content.Intent;
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
import vn.iotech.restaurant.Models.LoginArray;
import vn.iotech.restaurant.Models.ProfileArray;
import vn.iotech.restaurant.Retrofit2.APIRetrofitUtils;
import vn.iotech.restaurant.Retrofit2.RetrofitDataClient;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                    Call<LoginArray> call = dataClient.getDataLogin(jsonObject);
                    call.enqueue(new Callback<LoginArray>() {
                        @Override
                        public void onResponse(Call<LoginArray> call, Response<LoginArray> response) {
                            if(response.isSuccessful()){
                                if(response.body().getMessage().indexOf("User not found")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getMessage().indexOf("Wrong password")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getData().getAuth()){
                                    getProfile(response.body().getData().getToken());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginArray> call, Throwable t) {
                            Log.i("TAG", t.getMessage());
                        }
                    });
                }
            }
        });
    }
    private void getProfile(String token){
        RetrofitDataClient client = APIRetrofitUtils.getData();
        Call<ProfileArray> call = client.getProfile(token);
        call.enqueue(new Callback<ProfileArray>() {
            @Override
            public void onResponse(Call<ProfileArray> call, Response<ProfileArray> response) {
                if(response.isSuccessful()){
                    if(response.body().getObjectDataOfProfile().getRole().equals("System")){
                        Toast.makeText(Login.this, "User does not have permission to login.", Toast.LENGTH_SHORT);
                    }
                    else {
                        startActivity(new Intent(Login.this, Home.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileArray> call, Throwable t) {
                Log.i("TAG", "ErrorProfile");
            }
        });
    }
}
