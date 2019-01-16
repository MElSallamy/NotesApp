package com.example.sallamy.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class EditAvtivty extends AppCompatActivity implements TextWatcher {

    int noteid;
    String headofnotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_avtivty);
        //  a back arrow appear to the left of the application icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Define EditText
        EditText editText = (EditText) findViewById(R.id.editTextActivity2);

        // get extra from Main Activity
        Intent i = getIntent();
        noteid = i.getIntExtra("noteID", -1);
        if (noteid != -1) {
            // notes is Arraylist in MainActivity
            headofnotes = MainActivity.notes.get(noteid);
            // editText by default = the name of title in any item from the list
            editText.setText(headofnotes);
        }

        // save data in edit activity
        // what happen when we change the text ??
        editText.addTextChangedListener(this);


    }

    // For back icon ..
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    // 3 methods created from  editText.addTextChangedListener(this); line
    // before editing
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    // on text editing
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        // take new CharSequence s and add it into notes arr in the same noteid
        MainActivity.notes.set(noteid, String.valueOf(s));
        // update the item even use back icon of application or use back icon in system
        MainActivity.arrayAdapter.notifyDataSetChanged();

        // clear set to make sure is empty
        MainActivity.set.clear();
        // save data permanent
        if (MainActivity.set == null) {
            MainActivity.set = new HashSet<String>(); // define set as hashset of string
            //  because set is null you can not add notes in it directly we should to write the previous line ..
        } else {
            MainActivity.set.clear();
        }

        // save new text permanent by using sharedpreferences
        MainActivity.sharedPreferences = this.getSharedPreferences("com.example.sallamy.notes", Context.MODE_PRIVATE);

        MainActivity.set.addAll(MainActivity.notes);
        MainActivity.sharedPreferences.edit().remove("notes");
        MainActivity.sharedPreferences.edit().putStringSet("notes", MainActivity.set).apply();


    }

    // after text editing
    @Override
    public void afterTextChanged(Editable s) {
    }
}

