package it.uniba.dib.sms222316;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class TurnDialog extends DialogFragment {
    private Games game;
    private TextView playerNameTextView;
    private TextView playerScoreTextView;

    public TurnDialog(Games game) {
        this.game = game;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_game, container, false);

        playerNameTextView = view.findViewById(R.id.playerNameTextView);
        playerScoreTextView = view.findViewById(R.id.playerNameTextView);

        updateUI();

        return view;
    }

    private void updateUI() {
        Player currentPlayer = game.getCurrentPlayer();
        playerNameTextView.setText(currentPlayer.getName());
        playerScoreTextView.setText(String.valueOf(currentPlayer.getScore()));
    }
}

