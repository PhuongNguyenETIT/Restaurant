package vn.iotech.restaurant;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import vn.iotech.restaurant.Adapters.AdapterCart;
import vn.iotech.restaurant.Models.ObjectForRecyclerViewInCart;
import vn.iotech.restaurant.Models.PreferencesCart;

public class Cart extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonInvoice, buttonService;
    private TextView textViewTableCart;
    Dialog dialogPopup;
    ArrayList<ObjectForRecyclerViewInCart> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mapped();

        buttonBackToolbar();

        initRecyclerViewCart();

        buttonInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cart.this, Invoice.class));
            }
        });

        popupService();

    }

    private void popupService(){
        dialogPopup = new Dialog(this);
        dialogPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPopup.setContentView(R.layout.custom_popup_service);
        dialogPopup.setCanceledOnTouchOutside(false);
        ImageButton imageButtonClosePopup = (ImageButton) dialogPopup.findViewById(R.id.closePopupMenu);
        final ImageView imageViewNotifica = (ImageView) dialogPopup.findViewById(R.id.imgNotifica);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_services);

        imageButtonClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPopup.dismiss();
            }
        });

        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPopup.show();
                imageViewNotifica.startAnimation(animation);
            }
        });
    }

    private void buttonBackToolbar() {
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
                Intent intent = new Intent(Cart.this, Home.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    public void initRecyclerViewCart(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CustomDivider customDivider = new CustomDivider(recyclerView.getContext(), 80, 26);
        recyclerView.addItemDecoration(customDivider);
        String json = ConfigsStatic.preferencesCart.getString("objectCart", "");
        if(json != "") {
            Type type = new TypeToken<ArrayList<PreferencesCart>>(){}.getType();
            ArrayList<PreferencesCart> preferencesCarts = new Gson().fromJson(json, type);
            for (int i = 0; i < preferencesCarts.size(); i++) {
                arrayList.add(new ObjectForRecyclerViewInCart(R.drawable.ic_delete_white_24dp,
                        preferencesCarts.get(i).getName(), preferencesCarts.get(i).getDuring(),
                        preferencesCarts.get(i).getPeople(), preferencesCarts.get(i).getPrice(),
                        preferencesCarts.get(i).getAmount(), preferencesCarts.get(i).getUnitPrice()));
            }
        }
        AdapterCart viewCart = new AdapterCart(arrayList, getApplicationContext());
        recyclerView.setAdapter(viewCart);
    }
    private void mapped(){
        toolbar = (Toolbar) findViewById(R.id.toolbarCart);
        buttonInvoice = (Button) findViewById(R.id.buttonInvoiceInCart);
        buttonService = (Button) findViewById(R.id.buttonSevice);
        textViewTableCart = (TextView) findViewById(R.id.textViewCartTable);
        textViewTableCart.setText("Table " + ConfigsStatic.nameTableConfig);
    }

    public void bookingInCart(View view){
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

        tittle.setText("Are you sure you want to book?");

        buttonPosi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(Cart.this, "Booked", Toast.LENGTH_SHORT).show();
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
