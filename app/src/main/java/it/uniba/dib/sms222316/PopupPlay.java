package it.uniba.dib.sms222316;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import it.uniba.dib.sms222316.Gameplay.GameActivity;

public class PopupPlay extends Dialog {

    private final String[] titles = new String[]{getContext().getString(R.string.achievements), getContext().getString(R.string.missions)};
    int selected = 0;
    public PopupPlay(Context context) {
        super(context);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.play_popup);
        ImageButton Playbutton = findViewById(R.id.continu);
        ImageView icon1 = findViewById(R.id.icon1);
        ImageView icon2 = findViewById(R.id.icon2);
        ImageView icon3 = findViewById(R.id.icon3);
        ImageView icon4 = findViewById(R.id.icon4);
        ImageView icon5 = findViewById(R.id.icon5);
        RadioButton radio1 = findViewById(R.id.radio1);
        RadioButton radio2 = findViewById(R.id.radio2);

        icon1.setOnClickListener(v ->
        {
            selected=1;
            icon1.setBackgroundColor(Color.YELLOW);
            icon2.setBackgroundColor(Color.rgb(0, 102, 153));
            icon3.setBackgroundColor(Color.rgb(0, 102, 153));
            icon4.setBackgroundColor(Color.rgb(0, 102, 153));
            icon5.setBackgroundColor(Color.rgb(0, 102, 153));

        });
        icon2.setOnClickListener(v ->
        {
            selected=2;
            icon2.setBackgroundColor(Color.YELLOW);
            icon1.setBackgroundColor(Color.rgb(0, 102, 153));
            icon3.setBackgroundColor(Color.rgb(0, 102, 153));
            icon4.setBackgroundColor(Color.rgb(0, 102, 153));
            icon5.setBackgroundColor(Color.rgb(0, 102, 153));

        });
        icon3.setOnClickListener(v ->
        {
            selected=3;
            icon3.setBackgroundColor(Color.YELLOW);
            icon2.setBackgroundColor(Color.rgb(0, 102, 153));
            icon1.setBackgroundColor(Color.rgb(0, 102, 153));
            icon4.setBackgroundColor(Color.rgb(0, 102, 153));
            icon5.setBackgroundColor(Color.rgb(0, 102, 153));

        });
        icon4.setOnClickListener(v ->
        {
            selected=4;
            icon4.setBackgroundColor(Color.YELLOW);
            icon2.setBackgroundColor(Color.rgb(0, 102, 153));
            icon3.setBackgroundColor(Color.rgb(0, 102, 153));
            icon1.setBackgroundColor(Color.rgb(0, 102, 153));
            icon5.setBackgroundColor(Color.rgb(0, 102, 153));

        });
        icon5.setOnClickListener(v ->
        {
            selected=5;
            icon5.setBackgroundColor(Color.YELLOW);
            icon4.setBackgroundColor(Color.rgb(0, 102, 153));
            icon3.setBackgroundColor(Color.rgb(0, 102, 153));
            icon2.setBackgroundColor(Color.rgb(0, 102, 153));
            icon1.setBackgroundColor(Color.rgb(0, 102, 153));

        });

        Playbutton.setOnClickListener(v ->
        {
            if (radio1.isChecked())
            {
                Intent i = new Intent(context, GameActivity.class);

                i.putExtra("money", 1500);
                i.putExtra("puddle", selected);
                context.startActivity(i);
                this.hide();

            }
            else
            {
                Intent i = new Intent(context, GameActivity.class);

                i.putExtra("money", 5000);
                i.putExtra("puddle", selected);
                context.startActivity(i);
                this.hide();
            }

        });



    }
}