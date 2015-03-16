package com.example.toni.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toni.myapplication.business.NotesList;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        TextView textView = (TextView)view.findViewById(R.id.note_name);
        textView.setText(notesList.getNotes().get(position).getName());
        if (position==0) {
            View viewDivisor = view.findViewById(R.id.head_divider);
            viewDivisor.setVisibility(View.VISIBLE);
        }
        textView = (TextView)view.findViewById(R.id.creation_date);
        Date myDate = notesList.getNotes().get(position).getDateCreation();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (myDate!=null) {
            textView.setText(dateFormat.format(myDate));
        } else {
            textView.setText("");
        }
        textView = (TextView)view.findViewById(R.id.modification_date);
        myDate = notesList.getNotes().get(position).getDateModification();
        if (myDate!=null) {
            textView.setText(dateFormat.format(myDate));
        } else {
            textView.setText("");
        }


        return view;
    }

    public NotesList getNotesList() {
        return notesList;
    }

    public void setNotesList(NotesList notesList) {
        this.notesList = notesList;
    }



}
