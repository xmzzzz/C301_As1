package com.example.CountBook;


import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


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
 * Construct Main activity
 *
 * October 2, 2017
 *
 * Copyright (x) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the code behaviour of students.
 *
 * @author xinmeng1
 * @version  1.0
 * @since 1.0
 */

public class MainActivity extends AppCompatActivity {

    /**
     * The file is save and load from GSON.
     * @see #loadFromFile()
     * @see #saveInFile()
     */

    private static final String FILENAME = "file.sav";
    private EditText name;
    private EditText currentValue;
    private EditText comment;
    private ListView oldActivities;

    private ArrayList<EachActivity> activities;
    private ArrayAdapter<EachActivity> adapter;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.body1);
        currentValue = (EditText) findViewById(R.id.body2);
        comment = (EditText) findViewById(R.id.body3);

        oldActivities = (ListView) findViewById(R.id.activityList);
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String activityName = new String();
                activityName="";
                String activityCurrentValue = new String();
                activityCurrentValue="";
                String activityComment = new String();
                activityComment="";
                int newValue = -1;

                activityName = name.getText().toString();
                activityName.trim();
                activityCurrentValue = currentValue.getText().toString();
                activityComment = comment.getText().toString();
                try{
                    newValue = Integer.valueOf(activityCurrentValue).intValue();
                }catch (Exception e){
                    Intent intent = new Intent(MainActivity.this, Exceptions.class);
                    startActivity(intent);
                }
                if (activityName.isEmpty() || activityCurrentValue.isEmpty() || newValue<0 || activityName.length() > 30 ||
                        activityCurrentValue.length()>30 ||  activityComment.length()>30){
                    Intent intent = new Intent(MainActivity.this, Exceptions.class);
                    startActivity(intent);
                }else {EachActivity newActivity = new EachActivity(activityName, newValue, newValue, activityComment);
                    activities.add(newActivity);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });


    }


    /**
     * Called when activity starts. If user presses one of the activity, it will lead user to that detail.
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<EachActivity>(this,
                R.layout.list_item,activities);
        oldActivities.setAdapter(adapter);
        oldActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditCounter.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
        saveInFile();
    }


    /**
     * Loads tweets from file.
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

    /**
     * Saves tweets in file in JSON format.
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
