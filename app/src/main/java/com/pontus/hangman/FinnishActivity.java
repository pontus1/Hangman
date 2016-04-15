package com.pontus.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FinnishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finnish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // hämta result från ActivityMain
        String result = getIntent().getStringExtra("result");

        TextView resultText = (TextView) findViewById(R.id.textView_result);

        resultText.setText(result);
    }

    public void getBackToMain(View view){

            Intent intent = new Intent(FinnishActivity.this, MainActivity.class);
            startActivity(intent);
    }

}
