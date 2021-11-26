package com.our.project.ToDoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if(noteId != -1){
            editText.setText(note.notes.get(noteId));
        }else{
            note.notes.add("");
            noteId=note.notes.size() -1;
            note.arrayadapter.notifyDataSetChanged();

        }





        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.notes.set(noteId, String.valueOf(charSequence));
                note.arrayadapter.notifyDataSetChanged();
                SharedPreferences sp = getApplicationContext().getSharedPreferences("com.our.project.ToDoList", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(note.notes);
                sp.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
