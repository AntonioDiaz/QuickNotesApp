package com.example.toni.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toni.myapplication.business.NotesList;

import java.io.IOException;


public class MainActivity extends ListActivity {
    public static final String TAG = "MainActivity";
    public static final String NOTE_NAME = "NOTE_NAME_INTENT";
    public static NotesList notesList;

    private String[] listValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesList = NotesList.recoverNotesListFromFile(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(android.R.id.list);
        TextView textView = (TextView) findViewById(R.id.textview_no_notes);
        listView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        listValues = MainActivity.notesList.arrayNotes();

        if (listValues.length>0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listValues);
            listView.setAdapter(arrayAdapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isValid = true;
        switch (item.getItemId()) {
            case R.id.action_new:
                createNewNote();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "hola SETTINGS", Toast.LENGTH_SHORT).show();
                break;
            default:
                isValid = super.onOptionsItemSelected(item);
        }
        return isValid;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(MainActivity.NOTE_NAME, listValues[position]);
        this.startActivity(intent);

    }

    private void createNewNote() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        this.startActivity(intent);
    }

}
