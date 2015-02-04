package com.example.toni.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toni.myapplication.business.NotesList;

/**
 * Created by antonio on 02/02/2015.
 */
public class ComplexListAdapter extends BaseAdapter {

    private final Context context;
    private NotesList notesList;

    public ComplexListAdapter(Context context, NotesList notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @Override
    public int getCount() {
        return this.notesList.getNotes().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.my_item, null);
        } else {
            view = convertView;
        }
        TextView title = (TextView)view.findViewById(R.id.note_name);
        title.setText(notesList.getNotes().get(position).getName());
        return view;
    }

    public NotesList getNotesList() {
        return notesList;
    }

    public void setNotesList(NotesList notesList) {
        this.notesList = notesList;
    }



}
