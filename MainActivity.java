package org.ayanle.qoraal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;

    ArrayList<String> All_IDS, Alltitles,
            AllContents, AllDates;

    CustomAdaptor customAdaptor;
    DatabaseHelper my_db;

    private TextView noData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);


        All_IDS = new ArrayList<>();
        Alltitles = new ArrayList<>();
        AllContents = new ArrayList<>();
        AllDates = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);


         customAdaptor = new CustomAdaptor(MainActivity.this,MainActivity.this,
                 All_IDS,
                 Alltitles,
                 AllContents,
                 AllDates);

         my_db = new DatabaseHelper(this);

        storedWordsInLibrary();


        recyclerView.setAdapter(customAdaptor);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        AddNoteActivity.class));
            }
        });
    }

    void storedWordsInLibrary(){

        Cursor cursor = my_db.readAllDate();

        if (cursor.getColumnCount() == 0){

            Toast.makeText(this,
                    "Your library is empty.", Toast.LENGTH_SHORT).show();
        }

        else {

            while (cursor.moveToNext()){

                All_IDS.add(cursor.getString(0));
                Alltitles.add(cursor.getString(1));
                AllContents.add(cursor.getString(2));
                AllDates.add(cursor.getString(3));

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.menu_add:
                startActivity(new Intent(MainActivity.this,
                        AddNoteActivity.class));
                break;

            case R.id.menu_deleteAll:

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Masax dhamaan waraaqaha?");
                alert.setMessage("Ma hubtaa in aad masaxda waraaqaha oo dhan?");
                alert.setPositiveButton("Haa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
                        helper.delteAll();
                        startActivity(new Intent(MainActivity.this,
                                MainActivity.class));

                        Toast.makeText(MainActivity.this,
                                "Waad masaxday waraaqihii oo dhan.", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });

                alert.setNegativeButton("Maya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create().show();


                break;

            default:
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1){
            recreate();
        }
    }
}
