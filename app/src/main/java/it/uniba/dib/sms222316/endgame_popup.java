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

import java.util.concurrent.atomic.AtomicInteger;

public class endgame_popup extends Dialog {

    private final String[] titles = new String[]{getContext().getString(R.string.achievements), getContext().getString(R.string.missions)};
    int selected = 0;
    public endgame_popup(Context context) {
        super(context);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.play_popup);


    }


}