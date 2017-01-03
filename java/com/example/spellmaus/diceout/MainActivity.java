package com.example.spellmaus.diceout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    Field to hold the roll result text
    TextView rollResult;

//    Field to hold the score
    int score;

    Random rand;

//    Fields to hold dice values
    int die1, die2, die3;

//    Holds random dice values
    ArrayList<Integer> dice;

    ArrayList<ImageView> diceImageView;

//    Field to hold score text
    TextView scoreText;

//Executes when activity is created within the app when it runs.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

//        Set initial score
        score = 0;
//        casting a view to a textview
        rollResult = (TextView) findViewById(R.id.rollResult);

//        A "toast", a feature to send brief message to the display
        Toast.makeText(getApplicationContext(), "Welcome to DiceOut!", Toast.LENGTH_SHORT).show();

        rand = new Random();

//        Create ArrayList container for dice values
        dice = new ArrayList<Integer>();
        ImageView die1Image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3Image);

        diceImageView = new ArrayList<ImageView>();
        diceImageView.add(die1Image);
        diceImageView.add(die2Image);
        diceImageView.add(die3Image);

        scoreText = (TextView) findViewById(R.id.scoreText);
    }

    public void rollDice(View v){
        rollResult.setText("Clicked!");
//        Roll dice
        die1 = rand.nextInt(6)+1;
        die2 = rand.nextInt(6)+1;
        die3 = rand.nextInt(6)+1;

//        Set dice values into ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int i = 0; i <3; i++){
            String imageName = "die_" + dice.get(i) + ".png";

            try{
                InputStream stream = getAssets().open(imageName); //get from assets folder
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageView.get(i).setImageDrawable(d);
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        String msg;

        //if all 3 are equal
        if(die1==die2 && die1 == die3){
            int scoreDelta = die1 * 100;
            msg = "You rolled a triple " + die1 + "! You scored " + scoreDelta + " points!";
            score+=scoreDelta;
        }
        else if(die1 == die2 || die1 == die3 || die2 == die3){
            // a double
            msg = "You rolled doubles for 50 points!";
            score+= 50;
        }
        else{
            msg = "You didn't score this round. Try again!";
        }

        rollResult.setText(msg);
        scoreText.setText("Score: " + score);

//        int num = rand.nextInt(6)+1;
//        String randomValue = "Number generated: " + num;
//        Toast.makeText(getApplicationContext(),randomValue,Toast.LENGTH_SHORT).show();
    }
//Related to the menu bar; not used in this tutorial but kept for program to work
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
}
