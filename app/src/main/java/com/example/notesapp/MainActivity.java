package com.example.notesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.adapter.AlertDialogManager;
import com.example.notesapp.adapter.MainActivityAdapter;
import com.example.notesapp.database.DatabaseHelper;
import com.example.notesapp.model.MainActivityModel;
import com.example.notesapp.session.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SessionManager session;
    String id_notes = "",username,judul,isi,tanggal;
    String email;
    TextView tvNotFound;
    FloatingActionButton fab;

    AlertDialogManager alert = new AlertDialogManager();
    TextView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        tvNotFound = findViewById(R.id.noHistory);
        btnLogout = findViewById(R.id.signout);
        fab = findViewById(R.id.fab);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        email = user.get(SessionManager.KEY_EMAIL);



        refreshList();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(MainActivity.this, WriteNotesActivity.class);
                startActivity(home);
                finish();
            }
        });


        session.checkLogin();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });

        SearchAction();
    }

    private void SearchAction() {

        SearchView searchView = findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
                searchList(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                refreshList();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshList() {
        final ArrayList<MainActivityModel> hasil = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM TB_NOTES WHERE username='" + email + "'", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id_notes = cursor.getString(0);
            username = cursor.getString(1);
            judul = cursor.getString(2);
            isi = cursor.getString(3);
            tanggal = cursor.getString(4);


            hasil.add(new MainActivityModel(id_notes,username,judul,isi,tanggal));
        }

        ListView listBook = findViewById(R.id.list_booking);
        MainActivityAdapter arrayAdapter = new MainActivityAdapter(this, hasil);
        listBook.setAdapter(arrayAdapter);

        //delete data
        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = hasil.get(i).getMid_notes();
                final CharSequence[] dialogitem = {"Hapus Note","Edit Note"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0){
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:

                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            try {
                                                db.execSQL("DELETE FROM TB_NOTES where id_notes = " + selection + "");
                                                id_notes = "";
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            refreshList();

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();

                            Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_LONG).show();
                        }else if(item == 1){
                            Intent home=new Intent(MainActivity.this, EditNoteActivity.class);
                            home.putExtra("id_notes", selection);
                            home.putExtra("judul", hasil.get(i).getMjudul());
                            home.putExtra("isi", hasil.get(i).getMisi());
                            startActivity(home);

                        }





                    }
                });
                builder.create().show();
            }
        });



        if (id_notes.equals("")) {
            tvNotFound.setVisibility(View.VISIBLE);
            listBook.setVisibility(View.GONE);
        } else {
            tvNotFound.setVisibility(View.GONE);
            listBook.setVisibility(View.VISIBLE);
        }

    }

    public void searchList(String keyword) {

//        String update = "SELECT * FROM judul = '"+ judul +"', isi = '"+ note +"', tanggal = '"+ formattedDate +"' WHERE id_notes = " + id_notes;
        String update = "SELECT * FROM TB_NOTES WHERE id_notes = '"+ id_notes +"' AND isi LIKE" + '%'+ keyword+ '%';
//        db.execSQL(update);

        final ArrayList<MainActivityModel> hasil = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM TB_NOTES WHERE username = '"+ email +"' AND isi LIKE '%"+keyword+"%'", null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id_notes = cursor.getString(0);
            username = cursor.getString(1);
            judul = cursor.getString(2);
            isi = cursor.getString(3);
            tanggal = cursor.getString(4);


            hasil.add(new MainActivityModel(id_notes,username,judul,isi,tanggal));
        }

        ListView listBook = findViewById(R.id.list_booking);
        MainActivityAdapter arrayAdapter = new MainActivityAdapter(this, hasil);
        listBook.setAdapter(arrayAdapter);

        //delete data
        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String selection = hasil.get(i).getMid_notes();
                final CharSequence[] dialogitem = {"Hapus Note","Edit Note"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0){
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:

                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            try {
                                                db.execSQL("DELETE FROM TB_NOTES where id_notes = " + selection + "");
                                                id_notes = "";
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            refreshList();

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();

                            Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_LONG).show();
                        }else if(item == 1){
                            Intent home=new Intent(MainActivity.this, EditNoteActivity.class);
                            home.putExtra("id_notes", selection);
                            home.putExtra("judul", hasil.get(i).getMjudul());
                            home.putExtra("isi", hasil.get(i).getMisi());
                            startActivity(home);

                        }





                    }
                });
                builder.create().show();
            }
        });



        if (id_notes.equals("")) {
            tvNotFound.setVisibility(View.VISIBLE);
            listBook.setVisibility(View.GONE);
        } else {
            tvNotFound.setVisibility(View.GONE);
            listBook.setVisibility(View.VISIBLE);
        }

    }
}