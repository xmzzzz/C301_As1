package com.example.CountBook;


import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.ArrayList;
/**
 * Show the detail of each item, and make
 * count by +1 or -1 and reset it.
 *
 *
 * October 2, 2017
 *
 * Copyright (x) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the code behaviour of students.
 *
 * @author xinmeng1
 * @version  1.0
 * @since 1.0
 */
public class EditCounter extends AppCompatActivity {
    /**
     * Save and load from GSON.
     * @see #loadFromFile()
     * @see #saveInFile()
     */

    private static final String FILENAME = "file.sav";

    private ArrayList<EachActivity> activities;
    private ArrayAdapter<EachActivity> adapter;

    private int position;
    private TextView name;
    private TextView counter;
    private TextView comment;

    private String nameStr;
    private int counterInt;
    private String commentStr;

    @Override
    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile();
        setContentView(R.layout.activity_edit_counter);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("pos");

        // initialize textview
        name = (TextView)findViewById(R.id.nameText);
        counter = (TextView)findViewById(R.id.counterText);
        comment = (TextView)findViewById(R.id.commentText);





    }
    /**
     * Called when the activity is start.
     */
    protected void onStart(){
        super.onStart();


        // initialize string
        nameStr = (activities.get(position)).getName();
        counterInt = (activities.get(position)).getCurrentValue();
        String counterStr=counterInt+"";
        commentStr = (activities.get(position)).getComment();
        // set text to text view
        name.setTextSize(40);
        name.setText("Name: "+nameStr);
        counter.setTextSize(40);
        counter.setText("Count: "+counterStr);
        comment.setTextSize(40);
        comment.setText("Comment: "+commentStr);

        // initialize button
        Button plusButton = (Button) findViewById(R.id.plusButton);
        Button minusButton = (Button) findViewById(R.id.minusButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        Button furtherEdit = (Button) findViewById(R.id.furtherEdit);
        Button delete = (Button) findViewById(R.id.delete);
        Button back = (Button) findViewById(R.id.back);

        // set plus button
        plusButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                (activities.get(position)).plus();
                counterInt = (activities.get(position)).getCurrentValue();
                String counterStr=counterInt+"";
                counter.setText("Count: "+counterStr);
                saveInFile();
            }

        });
        // set minus button
        minusButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                (activities.get(position)).minus();
                counterInt = (activities.get(position)).getCurrentValue();
                String counterStr=counterInt+"";
                counter.setText("Count: "+counterStr);
                saveInFile();
            }

        });
        // set reset button
        resetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                (activities.get(position)).reset();
                counterInt = (activities.get(position)).getCurrentValue();
                String counterStr=counterInt+"";
                counter.setText("Count: "+counterStr);
                saveInFile();
            }

        });
        // set edit button
        furtherEdit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EditCounter.this, MoreEdit.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }

        });
        // set delete button
        delete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                activities.remove(position);
                saveInFile();
                Intent backToMain = new Intent(EditCounter.this, MainActivity.class);
                startActivity(backToMain);

            }

        });
        // set back button
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EditCounter.this, MainActivity.class);
                startActivity(intent);
            }

        });



        saveInFile();
    }


    /**
     * https://github.com/ta301fall2017/lonelyTwitter/tree/f17TueLab3
     * Loads data from file.
     * @throws RuntimeException if IOException e happens
     * @exception FileNotFoundException if the file is not created
     */


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Taken from https://stackoverflow.com/question/12384064/gson-convert-from-json-into java
            // 2017 01-26 17:53:59
            activities = gson.fromJson(in, new TypeToken<ArrayList<EachActivity>>(){}.getType());

            fis.close();

        } catch (FileNotFoundException e) {
            activities = new ArrayList<EachActivity>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**https://github.com/ta301fall2017/lonelyTwitter/tree/f17TueLab3
     * Saves data in file in JSON format.
     * @throws FileNotFoundException if folder not exists
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(activities, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
