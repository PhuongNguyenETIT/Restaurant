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
import vn.iotech.restaurant.ObjectOriented.ObjectDataOfLogin;
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
                    Call<ObjectDataOfLogin> call = dataClient.getDataLogin(jsonObject);
                    call.enqueue(new Callback<ObjectDataOfLogin>() {
                        @Override
                        public void onResponse(Call<ObjectDataOfLogin> call, Response<ObjectDataOfLogin> response) {
                            if(response.isSuccessful()){
                                Log.i("TAG", response.body().getMessage());
                                if(response.body().getMessage().indexOf("User not found")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getMessage().indexOf("Wrong password")>0){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                else if(response.body().getData().get(0).getAuth()){
                                    Toast.makeText(Login.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Home.class);
                                    Log.i("TAG", response.body().getData().get(0).getToken());
                                    intent.putExtra("token", response.body().getData().get(0).getToken());
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ObjectDataOfLogin> call, Throwable t) {
                            Log.i("TAG", t.getMessage());
                        }
                    });
                }
            }
        });
    }
}
