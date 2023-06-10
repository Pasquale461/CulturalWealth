package it.uniba.dib.sms222316;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import it.uniba.dib.sms222316.Gameplay.Game;
import it.uniba.dib.sms222316.Gameplay.Property;

public class PopupField extends Dialog {
    Caselle caselle = new Caselle();
    ImageView[] casella;

    public PopupField(@NonNull Context context,final GameActivity activity) {
        super(context);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.info_field);
        casella = caselle.setCasella(context);
        Button front = findViewById(R.id.frontbutton);
        Button back = findViewById(R.id.backbutton);
        EasyFlipView flip = findViewById(R.id.flip);


        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
            }
        });
    }

    public void InfoField(int field, List<Property> properties){

        TextView Name = findViewById(R.id.name);
        TextView Rent = findViewById(R.id.rent);
        TextView Sell_Price = findViewById(R.id.mortage);
        TextView Price = findViewById(R.id.price);
        TextView PaintCost = findViewById(R.id.paintingprice);
        TextView Rent1P = findViewById(R.id.rentonepaint);
        TextView Rent2P = findViewById(R.id.renttwopaint);
        TextView Rent3P = findViewById(R.id.rentthreepaint);
        TextView Rent4P = findViewById(R.id.rentfourpaint);
        TextView Description = findViewById(R.id.smalldescription);

            for (int i = 0; i < properties.size(); i++) {
                String name = properties.get(i).getNome();


                if (name.equals(casella[field].getContentDescription())) {
                    Name.setText(name);
                    int price = properties.get(i).getCosto();
                    Price.setText("Prezzo:"+price);
                    int paintCost = properties.get(i).getCostoQuadro();
                    PaintCost.setText("Costo Quadro:"+paintCost);
                    String description = properties.get(i).getDescrizione();
                    Description.setText(description);
                    int sell_price = properties.get(i).getCostoVendita();
                    Sell_Price.setText("Vendita:"+sell_price);
                    int[] rentarray = properties.get(i).getAffitto();
                    int[] rent = new int[rentarray.length];
                    for (int j = 0; j < rentarray.length; j++) {
                        rent[j] = rentarray[j];
                    }
                    Rent.setText("Rendita:"+rent[0]);
                    Rent1P.setText("rendita con 1 quadro:"+rent[1]);
                    Rent2P.setText("rendita con 2 quadro:"+rent[2]);
                    Rent3P.setText("rendita con 3 quadro:"+rent[3]);
                    Rent4P.setText("rendita con 4 quadro:"+rent[4]);
                }

            }



        ImageView Image = findViewById(R.id.image);
    }
}
