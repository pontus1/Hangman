package com.pontus.hangman;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class HangActivity extends AppCompatActivity {


    private String secretWord = null;
    private int imageNo = 1;
    private InputStream imageStream = null;
    private ImageView gallow = null;
    char[] secretArray = null;
    String[] underLines = null;
    EditText guessedLetter = null;
    TextView textView_secretWord;
    String hiddenWord = "";
    TextView textView_guessed_letters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // hämta secret word från ActivityMain
        secretWord = getIntent().getStringExtra("secret_word");

        // hämta textView_secretWord
        textView_secretWord = (TextView) findViewById(R.id.textView_secretWord);

        // hämta textView_guessed_letters
        textView_guessed_letters = (TextView) findViewById(R.id.textView_guessed_letters);

        // skapa en array av secretWord
        secretArray = secretWord.toCharArray();




        // fyll hiddenWord med "_ " för varje bokstav
        for (int i = 0; i < secretArray.length; i++) {
            hiddenWord += "_ ";
        }

        // fyll underLines med "_" för varje bokstav
        underLines = hiddenWord.split(" ");

        // sätt textViewen till fnuttar
        textView_secretWord.setText(hiddenWord);


        try {
            imageStream = getAssets().open("Hangman-0.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(imageStream, null);
        gallow = (ImageView) findViewById(R.id.imageView_gallow);
        gallow.setImageDrawable(d);

        // hämta texten från editText
        guessedLetter = (EditText) findViewById(R.id.editText_guess);



        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       */
    }

    // om man trycker på submitknappen
    public void guess(View view) {

        // ++ touppercase!
        char letter = guessedLetter.getText().toString().charAt(0);

        if(Character.toString(letter).matches("[A-Öa-ö]")) {

            // fel svar
            if(secretWord.indexOf(letter) < 0){
                try {
                    if (imageNo >= 6) //imageNo = 0;
                    {
                        goToResult("You died");
                    }

                    InputStream imageStream = getAssets().open("Hangman-" + imageNo++ + ".png");
                    Drawable d = Drawable.createFromStream(imageStream, null);
                    gallow.setImageDrawable(d);
                    textView_guessed_letters.setText(textView_guessed_letters.getText() + " " + letter);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast toast = Toast.makeText(HangActivity.this, "\"" + guessedLetter.getText().toString() + "\" is wrong!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 100, 200);
                toast.show();
            } else {

                // en for-loop som stegar igenom 'secretArray' och byter ut slotten "_" mot letter på rätt plats
                for (int i = 0; i < secretArray.length; i++) {
                    if (secretArray[i] == letter) {
                        underLines[i] = String.valueOf(secretWord.charAt(i));
                    }
                }

                // nollställer hiddenWord
                hiddenWord = "";

                // for-loop som fyller 'word' med de rätta bokstäverna och "_" på de ännu ej gissade
                for (int i = 0; i < secretArray.length; i++) {
                    hiddenWord += underLines[i] + " ";
                }
                textView_secretWord.setText(hiddenWord.toString());

                // rätt svar
                Toast toast = Toast.makeText(HangActivity.this, "\"" + guessedLetter.getText().toString() + "\" is right!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.LEFT, 100, 200);
                toast.show();

                if(!textView_secretWord.getText().toString().contains("_")){
                    goToResult("You survived!");
                }
            }

        } else {
            Toast toast = Toast.makeText(HangActivity.this, "\"" + guessedLetter.getText().toString() + "\" is not a letter!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 100, 200);
            toast.show();
        }

        guessedLetter.setText("");
    }

    public void goToResult(String result){

            Intent intent = new Intent(HangActivity.this, FinnishActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
    }

}
