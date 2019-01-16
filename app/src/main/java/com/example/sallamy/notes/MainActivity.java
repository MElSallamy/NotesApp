package com.example.sallamy.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    // static Arraylist
    static ArrayList<String> notes = new ArrayList<>();
    // Adapter array for items
    static ArrayAdapter arrayAdapter;
    // SharedPreferences to sore data permanent
    static SharedPreferences sharedPreferences;
    //Set<String> for using it for save data just like arr for sharedPreferences ..
    // make it static to use it in edit activity again  ..
    static Set<String> set;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Define list
        listView = (ListView) findViewById(R.id.listView);

        // save new text permanent by using sharedpreferences
        sharedPreferences = this.getSharedPreferences("com.example.sallamy.notes", Context.MODE_PRIVATE);

        set = sharedPreferences.getStringSet("notes", null);
        // clear notes arr to add new data insteed of old one
        notes.clear();

        if (set != null) {
            notes.addAll(set);
        } else {
            // Add items in list
            notes.add("Muhammad El - sallamy ");
            notes.add("hi again !");
            set = new HashSet<String>(); // define set as hashset of string
            set.addAll(notes); //  because set is null you can not add notes in it directly we should to write the previous line ..
            sharedPreferences.edit().putStringSet("notes", set).apply();

        }


        // use Arr Adapter for list
        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, notes);
        listView.setAdapter(arrayAdapter);

        // What happen when we tapped on any item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });


        // WHat happen when long click on items
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("are you need to delete this note ?")
                        .setMessage("are you sure you need to delete this note !! ")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        notes.remove(position);

                                        // save data permanent
                                        if (set == null) {
                                            set = new HashSet<String>(); // define set as hashset of string
                                            //  because set is null you can not add notes in it directly we should to write the previous line ..
                                        } else {
                                            set.clear();
                                        }

                                        set.addAll(notes);

                                        // save new text permanent by using sharedpreferences
                                        sharedPreferences = MainActivity.super.getSharedPreferences("com.example.sallamy.notes", Context.MODE_PRIVATE);
                                        sharedPreferences.edit().remove("notes").apply();
                                        sharedPreferences.edit().putStringSet("notes", set).apply();
                                        arrayAdapter.notifyDataSetChanged();


                                    }
                                }
                        )
                        .setNegativeButton("no", null)
                        .show();
                return true;
            }
        });

    }

    // onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add) {

            notes.add("");

            // clear set to make sure is empty
            set.clear();
            // save data permanent
            if (set == null) {
                set = new HashSet<String>(); // define set as hashset of string
                //  because set is null you can not add notes in it directly we should to write the previous line ..
            } else {
                set.clear();
            }

            set.addAll(notes);

            // save new text permanent by using sharedpreferences
            sharedPreferences = this.getSharedPreferences("com.example.sallamy.notes", Context.MODE_PRIVATE);

            sharedPreferences.edit().remove("notes");
            sharedPreferences.edit().putStringSet("notes", set).apply();
            arrayAdapter.notifyDataSetChanged();

            // intent to go to edit Activity
            Intent i = new Intent(getApplicationContext(), EditAvtivty.class);
            // item tapped position
            i.putExtra("noteID", notes.size() - 1);
            // go to edit Activity
            startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
