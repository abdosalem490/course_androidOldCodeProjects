package com.example.gameconnect3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow
    // 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;
    // 2 means un played
    int[] gameState= {2,2,2,2,2,2,2,2,2};

    int[][] winingPositions = {{0,1,2} , {3,4,5} , {6,7,8} , {0,3,6} , {1,4,7}, {2,5,8} ,{0,4,8},{2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void dropIn(View view)
    {
        ImageView counter =  (ImageView)view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 & gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000).rotation(360).setDuration(300);
        }
        for (int[]winingPosition :winingPositions)
        {
            if (gameState[winingPosition[0]] == gameState[winingPosition[1]]
                    && gameState[winingPosition[1]] == gameState[winingPosition[2]]
                    && gameState[winingPosition[0]] != 2) {
                System.out.println(gameState[winingPosition[0]]);
                TextView winner = findViewById(R.id.winnerMessage);
                Button btn = findViewById(R.id.playAgainButton);
                gameIsActive = false;
                String winnerName = " Red";
                if (gameState[winingPosition[0]] == 0)
                {
                    winnerName = "Yellow";
                }
                winner.setText(winnerName + " has won");
                winner.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                break;
            }else {
                boolean gameIsOver = true;
                for (int counterState : gameState)
                {
                    if (counterState == 2)
                    {
                        gameIsOver = false;
                    }
                }
                if (gameIsOver)
                {
                    TextView winner = findViewById(R.id.winnerMessage);
                    Button btn = findViewById(R.id.playAgainButton);
                    winner.setText("IT'S A DRAW");
                    winner.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    public void playAgain(View view)
    {
        gameIsActive = true;
        TextView winner = findViewById(R.id.winnerMessage);
        Button btn = findViewById(R.id.playAgainButton);
        winner.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i = 0 ; i < gameState.length ; i++)
        {
            gameState[i] = 2;
        }
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0 ; i < gridLayout.getChildCount() ; i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

}