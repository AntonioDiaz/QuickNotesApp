package com.example.toni.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toni.myapplication.business.Note;
import com.example.toni.myapplication.dialogs.NewNoteNameDialogFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by toni on 13/12/2014.
 */
public class EditNoteActivity  extends Activity implements NewNoteNameDialogFragment.NameUpdatedInterface{

    public static final String TAG = "EditNoteActivity";
    public static final String NEW_NAME = "NEW_NAME";
    public static final String OLD_NAME = "OLD_NAME";

    private static Note noteUpdate;
    private String temporalName;
    private Activity myActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity = this;
        noteUpdate = null;
        setContentView(R.layout.edit_note);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String noteName = getIntent().getStringExtra(MainActivity.NOTE_NAME);
        /** checking if is a new note. */
        if (noteName!=null && noteName.length()>0) {
            noteUpdate = MainActivity.notesList.existNoteName(noteName);
            if (noteUpdate!=null) {
                try {
                    String noteFromFile = MainActivity.notesList.readNoteFromFile(noteUpdate.getName(), this);
                    EditText editText = (EditText) findViewById(R.id.edit_text);
                    editText.setText(noteFromFile);
                } catch (IOException e) {
                    Toast.makeText(this, getString(R.string.error_read_note), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onCreate" + e.getMessage());
                    e.printStackTrace();
                    }
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_delete).setEnabled(true);
        if (this.noteUpdate==null) {
            menu.findItem(R.id.action_delete).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean myReturn = true;
        switch (item.getItemId()) {
            case R.id.action_rename:
                renameNote();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
            case R.id.action_save:
                saveNote();
                break;
            default:
                myReturn = super.onOptionsItemSelected(item);
        }
        return myReturn;
    }

    private void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirm);
        builder.setTitle(R.string.delete_note);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { }
        });
        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.notesList.deleteNote(noteUpdate, myActivity);
                myActivity.finish();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void renameNote() {
        DialogFragment newNoteNameDialogFragment = new NewNoteNameDialogFragment();
        Bundle args = new Bundle();
        if (noteUpdate==null) {
            args.putString(EditNoteActivity.OLD_NAME, this.temporalName!=null?this.temporalName:getDefaultName());
        } else {
            args.putString(EditNoteActivity.OLD_NAME, noteUpdate.getName());
        }
        newNoteNameDialogFragment.setArguments(args);
        newNoteNameDialogFragment.setCancelable(true);
        newNoteNameDialogFragment.show(getFragmentManager(), "note_name_dialog");
    }

    /**
     * Persist a note. If is a new note, before to save add a new note.
     *
     */
    private void saveNote() {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String newText = editText.getText().toString();
        try {
            if (this.noteUpdate==null) {
                String newNoteName = this.temporalName!=null ? this.temporalName : getDefaultName();
                this.noteUpdate = MainActivity.notesList.addNote(newNoteName, newText, this);
            } else {
                MainActivity.notesList.saveNote(this.noteUpdate, newText, this);
            }
            Toast.makeText(this, getString(R.string.saved_note), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.error_save_note), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "saveNote" + e.getMessage());
            e.printStackTrace();
        }


    }

    private String getDefaultName() {
        String defaultName = getString(R.string.note_default_name);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return defaultName + "_" + format.format(new Date());
    }


    @Override
    public void onNameUpdated(String newName) {
        if (this.noteUpdate==null) {
            this.temporalName = newName;
        } else {
            noteUpdate.setName(newName);
            MainActivity.notesList.persitNotesList(this);
        }
    }
}
