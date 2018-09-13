package giel.hva.nl.higherlowerapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton arrowUp;
    private FloatingActionButton arrowDown;
    private ImageView imageView;
    private TextView highScoreText;
    private TextView scoreText;
    private int oldThrow;
    private int score;
    private int highScore;
    private ListView throwList;
    private List<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrowUp = findViewById(R.id.arrowUp);
        arrowDown = findViewById(R.id.arrowDown);
        imageView = findViewById(R.id.imageView);
        highScoreText = findViewById(R.id.highScoreText);
        scoreText = findViewById(R.id.scoreText);
        throwList = findViewById(R.id.throwList);
        items = new ArrayList<>();
        oldThrow = randomThrow();

        score = 0;
        highScore = 0;

        setScoreText();
        setImage();


        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(false);
            }
        });

        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(true);
            }
        });
    }


    private int randomThrow(){
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    private void rollDice(boolean isHigher){
        updateList();
        int currentThrow = randomThrow();
        if(currentThrow >= oldThrow && isHigher){
            score++;
        }else if( currentThrow <= oldThrow && !isHigher){
            score++;
        }else{
            score = 0;
        }
        if(score > highScore){
            highScore = score;
        }

        oldThrow = currentThrow;
        setImage();
        setScoreText();
    }

    private void setImage(){
        switch (oldThrow){
            case 1: imageView.setImageResource(R.drawable.d1);
                break;
            case 2: imageView.setImageResource(R.drawable.d2);
                break;
            case 3: imageView.setImageResource(R.drawable.d3);
                break;
            case 4: imageView.setImageResource(R.drawable.d4);
                break;
            case 5: imageView.setImageResource(R.drawable.d5);
                break;
            default: imageView.setImageResource(R.drawable.d6);
                break;
        }

    }

    private void updateList(){
        items.add("Throw is "+ oldThrow);

        // If the list adapter is null, a new one will be instantiated and set on our list view.
        if (adapter == null) {
            // We can use ‘this’ for the context argument because an Activity is a subclass of the Context class.
            // The ‘android.R.layout.simple_list_item_1’ argument refers to the simple_list_item_1 layout of the Android layout directory.
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
            throwList.setAdapter(adapter);
        } else {
            // When the adapter is not null, it has to know what to do when our dataset changes, when a change happens we need to call this method on the adapter so that it will update internally.
            adapter.notifyDataSetChanged();
        }
    }

    private void setScoreText(){
        highScoreText.setText("HighScore: "+ highScore);
        scoreText.setText("Score: "+ score);
    }
}
