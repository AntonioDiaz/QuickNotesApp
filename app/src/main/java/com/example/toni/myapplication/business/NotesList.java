package com.example.toni.myapplication.business;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;


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
        NotesList notesReaded = new NotesList();
        try {
            InputStream inputStream = context.openFileInput(NotesList.NOTES_LIST_FILE);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder everything = new StringBuilder();
            String line;
            while( (line = bufferedReader.readLine()) != null) {
                everything.append(line);
            }
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Note>>(){}.getType();
            notesReaded.notes = gson.fromJson(everything.toString(), collectionType);
            if (notesReaded.notes == null) {
                notesReaded = new NotesList();
            }
        } catch (Exception e) {
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
        notes.add(note);
        this.persitNotesList(context);
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

    /**
     * Save note in a file which name is the name of the note.
     * @param note
     * @param noteText
     * @param context
     * @throws IOException
     */
    public void saveNote(Note note, String noteText, Context context) throws IOException {
        note.setDateModification(new Date());
        FileOutputStream fileOutputStream = context.openFileOutput(note.getId().toString(), Context.MODE_PRIVATE);
        fileOutputStream.write(noteText.getBytes());
        this.persitNotesList(context);
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
            String line = bufferedReader.readLine();
            while (line != null) {
                noteContent.append(line);
                line = bufferedReader.readLine();
                if (line !=null) {
                    noteContent.append(LINE_SEPARATOR);
                }
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

    /**
     * Save the list of notes in json format in a file called "notes_list.txt"
     * @param context
     */
    public void persitNotesList(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(NOTES_LIST_FILE, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String listStr = gson.toJson(this.notes);
            fileOutputStream.write(listStr.getBytes());
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


    public static void main(String[] args) throws IOException {
        List<Note> notesList = new ArrayList<>();
        notesList.add(new Note("primera", 1));
        notesList.add(new Note("segunda", 2));
        Gson gson = new Gson();
        System.out.println("empieza");
        for (Note note : notesList) {
            System.out.println("note -->" + note);
            System.out.println(gson.toJson(note, Note.class));
        }

        String listStr = gson.toJson(notesList);
        Type collectionType = new TypeToken<List<Note>>(){}.getType();
        List<Note> lista = gson.fromJson(listStr, collectionType);
        System.out.println(lista);
        System.out.println(listStr);
        System.out.println("jsonsss");
    }


    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
