package com.example.abhi.hello;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean is_O_turn = false;
    private char gameBoard[][] = new char[3][3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupOnClickListeners();
        resetButtons();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Developed By: Abhishek Shukla", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newGame(View view) {
        is_O_turn = false;
        gameBoard = new char[3][3];
        resetButtons();
    }
    private void resetButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setText("");
                        B.setEnabled(true);
                    }
                }
            }
        }
        TextView t = (TextView) findViewById(R.id.titleText);
        t.setText("Tic-Tac-Toe");
        TextView nextTurnView = (TextView) findViewById(R.id.nextTurn);
        nextTurnView.setText("X's Turn");
    }

    private boolean checkWin() {
        char winner = '\0';
        if (checkWinner(gameBoard, 3, 'X')) {
            winner = 'X';
        } else if (checkWinner(gameBoard, 3, 'O')) {
            winner = 'O';
        }

        if (winner == '\0') {
            return false;
        } else {
            Toast.makeText(this, winner + " wins", Toast.LENGTH_LONG).show();
            return true;
        }

    }

    private boolean checkWinner(char[][] board, int size, char player) {
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true;
            }
        }

        for (int y = 0; y < size; y++) {
            int total = 0;
            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    total++;
                }
            }
            if (total >= size) {
                return true;
            }
        }

        int total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true;
        }

        total = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[x][y] == player) {
                    total++;
                }
            }
        }
        if (total >= size) {
            return true;
        }
        return false;
    }
    private void disableGameButtons() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof Button) {
                        Button B = (Button) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }
    private void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    View V = R.getChildAt(x);
                    V.setOnClickListener(new PlayButtonOnClick(x, y));
                }
            }
        }
    }
    private class PlayButtonOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayButtonOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof Button) {
                Button B = (Button) view;
                gameBoard[x][y] = is_O_turn ? 'O' : 'X';
                B.setText(is_O_turn ? "O" : "X");
                B.setTextColor(is_O_turn ?
                        getResources().getColor(R.color.colorPrimary) :
                        getResources().getColor(R.color.colorRed));
                B.setTextSize(20);
                B.setEnabled(false);
                is_O_turn = !is_O_turn;
                TextView nextTurnView = (TextView) findViewById(R.id.nextTurn);
                nextTurnView.setText(is_O_turn ? "O's Turn" : "X's Turn");
                if (checkWin()) {
                    disableGameButtons();
                    nextTurnView.setText("X's Turn");
                }
            }
        }
    }
}
