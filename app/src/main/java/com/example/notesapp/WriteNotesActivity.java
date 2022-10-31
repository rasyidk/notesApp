package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.database.DatabaseHelper;
import com.example.notesapp.session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class WriteNotesActivity extends AppCompatActivity {

    TextView btsave;
    EditText etjudul,etcatatan;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String email;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);
        getSupportActionBar().hide();
        btsave= findViewById(R.id.write_save);
        etjudul = findViewById(R.id.write_judul);
        etcatatan = findViewById(R.id.write_catatan);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);


        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul,catatan;
                judul = etjudul.getText().toString();
                catatan = etcatatan.getText().toString();

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);


                try{
                    if(judul.trim().length() > 0 && catatan.trim().length() >0){
                        db.execSQL("INSERT INTO TB_NOTES (username, judul, isi,tanggal) VALUES ('" +
                                email + "','" +
                                judul + "','" +
                                catatan + "','" +
                                formattedDate + "');");
                        Toast.makeText(getApplicationContext(), "SAVED!", Toast.LENGTH_LONG).show();

                        Intent home=new Intent(WriteNotesActivity.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Judul Atau Catatan Masih Kosong!",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}