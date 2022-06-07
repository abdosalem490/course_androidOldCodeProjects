package com.abdosalm.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // 0 : yellow
    // 1 : red
    // 2 : empty
    int activePlayer = 0;

    boolean gameActive = true;

    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void DropIn(View view){
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameActive){

            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }else{
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            for (int[] winningPosition : winningPositions){
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                    // someone has won
                    String winner = "Red";
                    if (activePlayer == 1){
                        winner = "Yellow";
                    }

                    gameActive = false;

                    Button playAgainButton = findViewById(R.id.playAgainButton);
                    TextView winnerTextView = findViewById(R.id.winnerTextView);

                    winnerTextView.setText(String.format("%s has won", winner));
                    winnerTextView.setVisibility(View.VISIBLE);
                    playAgainButton.setVisibility(View.VISIBLE);

                }
            }
        }
    }
    public void playAgain(View view){
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        androidx.gridlayout.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0 ; i < gridLayout.getChildCount() ; i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }


        activePlayer = 0;

        gameActive = true;

        Arrays.fill(gameState, 2);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
    }
}