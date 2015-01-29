package com.example.toni.myapplication.business;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toni on 18/01/2015.
 */
public class NotesList implements Serializable {
    public static final String NOTES_LIST_FILE = "notes_list.txt";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String TAG = "NotesList";
    private List<Note> notes;


    public NotesList() {
        notes = new ArrayList<>();
    }

    public static NotesList recoverNotesListFromFile (Context context){
        NotesList notesReaded;
        try {
            FileInputStream fileInputStream = context.openFileInput(NotesList.NOTES_LIST_FILE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            notesReaded = (NotesList)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            notesReaded = new NotesList();
            Log.d(TAG, "recoverNotesListFromFile " + e.getMessage());
            e.printStackTrace();
        }
        return notesReaded;
    }

    public Note findNoteById(Integer idNote) {
        Note noteFound = null;
        for (Note note : notes) {
            if (note.getId() == idNote) {
                noteFound = note;
            }
        }
        return noteFound;
    }

    public Note existNoteName(String noteName) {
        Note noteFound = null;
        for (Note note : notes) {
            if (noteName.equals(note.getName())) {
                noteFound = note;
            }
        }
        return noteFound;
    }

    public Note addNote(String noteName, String noteText, Context context) throws IOException {
        Note note = new Note(noteName, this.nextNoteId());
        this.saveNote(note, noteText, context);
        this.persitNotesList(context);
        notes.add(note);
        return note;
    }

    private Integer nextNoteId() {
        Integer maxId = 1;
        for (Note note : notes) {
            if (note.getId()>maxId) {
                maxId = note.getId();
            }
        }
        return maxId + 1;
    }

    public void saveNote(Note note, String noteText, Context context) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(note.getId().toString(), Context.MODE_PRIVATE);
        fileOutputStream.write(noteText.getBytes());
        fileOutputStream.close();
    }

    public String readNoteFromFile(String noteName, Context context) throws IOException {
        StringBuilder noteContent = new StringBuilder();
        Note note = null;
        for (Note noteAux : notes) {
            if (noteName.equals(noteAux.getName())) {
                note = noteAux;
            }
        }
        if (note!=null) {
            InputStream inputStream = context.openFileInput(note.getId().toString());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                noteContent.append(line + LINE_SEPARATOR);
            }
            inputStream.close();
        }
        return noteContent.toString();
    }


    public boolean deleteNote(Note noteToDelete, Context context) {
        notes.remove(noteToDelete);
        context.deleteFile(noteToDelete.getId().toString());
        persitNotesList(context);
        return true;
    }

    public String[]arrayNotes() {
        String[] notesNames = new String[notes.size()];
        int i=0;
        for (Note note : notes) {
            notesNames[i++] = note.getName();
        }
        return notesNames;
    }

    public void persitNotesList(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(NOTES_LIST_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            fileOutputStream.close();
        } catch (IOException e) {
            Log.d(TAG, "saveNote" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "NotesList{" +
                "notes=" + notes +
                '}';
    }
}
