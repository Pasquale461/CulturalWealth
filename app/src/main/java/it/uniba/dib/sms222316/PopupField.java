package it.uniba.dib.sms222316;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class PopupField extends Dialog {
    Caselle caselle = new Caselle();
    ImageView[] casella;
    public PopupField(@NonNull Context context,final Game activity) {
        super(context);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.info_field);
        casella = caselle.setCasella(context);
        Button button = findViewById(R.id.frontbutton);
        Button button1 = findViewById(R.id.backbutton);
        EasyFlipView flip = findViewById(R.id.flip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.flipTheView();
            }
        });
    }

    public void InfoField(int field){
        TextView Name = findViewById(R.id.name);
        ImageView Image = findViewById(R.id.image);

        Name.setText(casella[field].getContentDescription());
    }
}
