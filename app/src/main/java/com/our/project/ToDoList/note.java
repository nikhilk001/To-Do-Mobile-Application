package com.our.project.ToDoList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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

public class note extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayadapter;
    private Bundle savedInstanceState;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addnote) {
            Intent intent = new Intent(getApplicationContext(), NoteEditor.class);
            startActivity(intent);

            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.our.project.ToDoList", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sp.getStringSet("notes", null);
        if(set==null){
            notes.add("Note 1");
        }else{
            notes = new ArrayList<>(set);
        }


        Intent i =getIntent();
        arrayadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(arrayadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),NoteEditor.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int itemtodel= position;

                new AlertDialog.Builder(note.this)
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(itemtodel);
                                arrayadapter.notifyDataSetChanged();
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("com.our.project.ToDoList", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(note.notes);
                                sp.edit().putStringSet("notes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;
            }
        });






















    }


}
