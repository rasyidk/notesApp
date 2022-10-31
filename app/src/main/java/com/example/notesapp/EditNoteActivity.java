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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {

    TextView btnsave;
    EditText editJudul,editNote;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        getSupportActionBar().hide();
        btnsave = findViewById(R.id.edit_save);
        editJudul = findViewById(R.id.edit_judul);
        editNote = findViewById(R.id.edit_catatan);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        String id_notes = extras.getString("id_notes");
        String wtjudul = extras.getString("judul");
        String wtisi = extras.getString("isi");

        editJudul.setText(wtjudul);
        editNote.setText(wtisi);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul,note;
                judul = editJudul.getText().toString();
                note = editNote.getText().toString();

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                if (judul.trim().length() >0 && note.trim().length()>0){

                    try {
                        String x="x;";
//                        db.execSQL("INSERT INTO TB_NOTES (username, judul, isi,tanggal) VALUES ('" +
//                                email + "','" +
//                                judul + "','" +
//                                catatan + "','" +
//                                formattedDate + "');");

                        String update = "UPDATE TB_NOTES SET judul = '"+ judul +"', isi = '"+ note +"', tanggal = '"+ formattedDate +"' WHERE id_notes = " + id_notes;
                        db.execSQL(update);

                        Toast.makeText(getApplicationContext(), "SAVED!", Toast.LENGTH_LONG).show();

                        Intent home=new Intent(EditNoteActivity.this, MainActivity.class);
                        startActivity(home);
                        finish();

//                        db.execSQL("UPDATE TB_NOTES SET judul = "+ judul +", isi="+ note +",tanggal = "+ x +" WHERE id_notes = "+ id_notes +" ");
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"Tidak Boleh Kosong!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}