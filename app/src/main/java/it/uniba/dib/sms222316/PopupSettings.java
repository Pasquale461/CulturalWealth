package it.uniba.dib.sms222316;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

public class PopupSettings extends Dialog {
    SwitchCompat eVolume,mVolume;
    boolean effect_boolean,music_boolean;
MediaPlayer main_ost;
    public PopupSettings(@NonNull Context context, final Home home) {
        super(context);
        setContentView(R.layout.options);
        main_ost = MediaPlayer.create(context, R.raw.electricpistol);

        eVolume = findViewById(R.id.vol_effetti);
        mVolume = findViewById(R.id.vol_musica);
        eVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundSwitchE();
            }
        });
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundSwitchM();
            }
        });
        load();
        updatev();
    }

    public void SoundSwitchM(){
        if(mVolume.isChecked()){
            SharedPreferences.Editor share = getContext().getSharedPreferences("editor", MODE_PRIVATE).edit();
            share.putBoolean("vol_musica",true);
            share.apply();
            mVolume.setChecked(true);
            main_ost.start();
        }else{
            SharedPreferences.Editor share = getContext().getSharedPreferences("editor", MODE_PRIVATE).edit();
            share.putBoolean("vol_musica",false);
            share.apply();
            mVolume.setChecked(false);
            main_ost.pause();
        }
    }

    public void SoundSwitchE(){
        if(eVolume.isChecked()){
            SharedPreferences.Editor share = getContext().getSharedPreferences("editor", MODE_PRIVATE).edit();
            share.putBoolean("vol_effetti",true);
            share.apply();
            eVolume.setChecked(true);
        }else{
            SharedPreferences.Editor editor = getContext().getSharedPreferences("editor", MODE_PRIVATE).edit();
            editor.putBoolean("vol_effetti",false);
            editor.apply();
            eVolume.setChecked(false);
        }
    }

    public void load(){
        SharedPreferences getSound = getContext().getSharedPreferences("editor", MODE_PRIVATE);
        effect_boolean = getSound.getBoolean("vol_effetti",false);
        music_boolean = getSound.getBoolean("vol_musica",true);

    }
    public void updatev(){
        eVolume.setChecked(effect_boolean);
        mVolume.setChecked(music_boolean);
    }



}
