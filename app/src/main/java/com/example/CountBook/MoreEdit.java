package com.example.CountBook;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;

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
 * This class is the MoreEdit, it can edit name, count, comment
 *
 *
 *
 *  Date: Sept 30, 2017
 *
 *  Copyright (x) Team X, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the code behaviour of students.
 * @author Guanfang Dong
 * @version  1.0
 * @since 1.0
 */

public class MoreEdit extends AppCompatActivity {
    /**
     * The file is save and load from GSON.
     * @see #loadFromFile()
     * @see #saveInFile()
     */
    private static final String FILENAME = "file.sav";

    private ArrayList<EachActivity> activities;
    private ArrayAdapter<EachActivity> adapter;

    private EditText newName;
    private EditText newComment;
    private EditText newCount;

    private int position;
    @Override
    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_edit);
        loadFromFile();
        Intent intent = getIntent();
        position = intent.getExtras().getInt("pos");

        newName = (EditText) findViewById(R.id.newName);
        newComment = (EditText) findViewById(R.id.newComment);
        newCount = (EditText) findViewById(R.id.newCount);

        Button saveButton = (Button) findViewById(R.id.save);
        Button backButton = (Button) findViewById(R.id.back);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String nameStr = new String();
                nameStr="";
                String CommentStr = new String();
                CommentStr="";
                String CountStr = new String();
                CountStr="";
                int newValue = -1;

                nameStr = newName.getText().toString();
                nameStr.trim();
                CountStr = newCount.getText().toString();
                CommentStr = newComment.getText().toString();
                if (CountStr.equals("")==false ){
                    try {
                        newValue = Integer.valueOf(CountStr).intValue();
                    } catch (Exception e) {
                        Intent intent = new Intent(MoreEdit.this, Exception2.class);
                        intent.putExtra("pos", position);
                        startActivity(intent);
                    }
                }


                if (CommentStr.equals("")==false  & CommentStr.length()<30) {
                    (activities.get(position)).setComment(CommentStr);
                    (activities.get(position)).setDate();
                    saveInFile();
                }
                if (nameStr.equals("")==false & nameStr.length()<30) {
                    (activities.get(position)).setName(nameStr);
                    (activities.get(position)).setDate();
                    saveInFile();
                }
                if (CountStr.equals("")==false  & CountStr.length()<30 & newValue!=-1) {
                    (activities.get(position)).setCount(newValue);
                    (activities.get(position)).setInitValue(newValue);
                    (activities.get(position)).setDate();
                    saveInFile();
                }



            }

        });



        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent backToMain = new Intent(MoreEdit.this, MainActivity.class);
                startActivity(backToMain);
            }

        });




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
