package it.uniba.dib.sms222316;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class PopupDialog extends Dialog {

    private EditText mEditText;
    private Button mButton;
    private static final int TIME_INTERVALL = 2000;
    private long backPressed;

    public PopupDialog(@NonNull Context context, final Home activity) {
        super(context);
        setContentView(R.layout.dialog_layout);

        mEditText = findViewById(R.id.username_text);
        mButton = findViewById(R.id.submitUserButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();

                // Do something with the text
                dismiss();
            }
        });
    }
    public void onBackPressed() {
        if(backPressed + TIME_INTERVALL > System.currentTimeMillis()){

            return;
        } else {

        }
        backPressed = System.currentTimeMillis();
    }
}