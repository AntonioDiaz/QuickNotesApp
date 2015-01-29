package com.example.toni.myapplication.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toni.myapplication.EditNoteActivity;
import com.example.toni.myapplication.MainActivity;
import com.example.toni.myapplication.R;

/**
 * Created by toni on 13/01/2015.
 */
public class NewNoteNameDialogFragment extends DialogFragment {

    private AlertDialog alertDialog;

    public interface NameUpdatedInterface {
        void onNameUpdated(String inputText);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String oldName = getArguments().getString(EditNoteActivity.OLD_NAME);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_note_name, null);
        builder.setView(view);
        builder.setTitle(R.string.new_note_name);
        TextView textView = (TextView)view.findViewById(R.id.edit_note_name);
        textView.setText(oldName);

        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.accept, null);

        //EditNoteActivity activity = (EditNoteActivity) getActivity();
        //activity.updateNoteName();
        alertDialog = builder.create();
        return alertDialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        Button cancelButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
        cancelButton.setOnClickListener(onclickCancel);
        Button acceptButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        acceptButton.setOnClickListener(onclickAccept);
    }

    View.OnClickListener onclickCancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            alertDialog.dismiss();
        }
    };

    View.OnClickListener onclickAccept = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO check correct pattern.
            EditText editText = (EditText)alertDialog.findViewById(R.id.edit_note_name);
            String newName = editText.getText().toString();
            if (MainActivity.notesList.existNoteName(newName)!=null) {
                Toast.makeText(getActivity(), getString(R.string.name_exists), Toast.LENGTH_SHORT).show();
            } else {
                NameUpdatedInterface nameUpdated = (NameUpdatedInterface)getActivity();
                nameUpdated.onNameUpdated(newName);
                alertDialog.dismiss();
            }
        }
    };
}
