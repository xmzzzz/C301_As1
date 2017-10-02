package com.example.CountBook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class is Exception2, it will show a notice and a back button
 *
 * October 2, 2017
 *
 * Copyright (x) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the code behaviour of students.
 *
 *
 * @author xinmeng1
 * @collebrate Guanfang Dong
 * @version  1.0
 * @since 1.0
 */
public class Exception2 extends AppCompatActivity {
    private TextView warning;
    private int position;

    @Override
    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceptions);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("pos");

        warning = (TextView)findViewById(R.id.activity);
        Button Back = (Button) findViewById(R.id.back);
        Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent backToMain = new Intent(Exception2.this, MoreEdit.class);
                backToMain.putExtra("pos", position);
                startActivity(backToMain);
            }

        });
    }
}
