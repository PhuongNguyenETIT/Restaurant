package vn.iotech.restaurant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        buttonBackToolbar();

    }

    private void buttonBackToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarSetting);
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
                Intent intent = new Intent(Setting.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void showHideConfigIP(View view){
        startActivity(new Intent(Setting.this, ConfigIPServer.class));
    }

    public void showHideChangePassword(View view){
        startActivity(new Intent(Setting.this, ChangePassword.class));
    }

    public void dialogSelectTable(View view){
        startActivity(new Intent(Setting.this, SettingTable.class));
    }

    public void logOutSetting(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.custom_dialog_alert, null);
        builder.setView(view);
        builder.setCancelable(false);
        Button buttonPosi = (Button) view.findViewById(R.id.buttonPosiLogout);
        Button buttonNega = (Button) view.findViewById(R.id.buttonNegaLogout);
        TextView tittle = (TextView) view.findViewById(R.id.textViewTittleAlert);
        final AlertDialog dialog = builder.create();
        dialog.show();

        tittle.setText("Are you sure you want to log out?");

        buttonPosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Setting.this, "LogOut", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = ConfigsStatic.sharedPreferences.edit();
                editor.putBoolean("authenticate", false);
                editor.commit();
                ConfigsStatic.statusAuthenticate = false;
                startActivity(new Intent(Setting.this, Home.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        buttonNega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
