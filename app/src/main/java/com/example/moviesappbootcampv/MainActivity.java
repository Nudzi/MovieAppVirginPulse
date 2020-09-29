package com.example.moviesappbootcampv;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClicked{

    public AsyncTask<String, Integer, ArrayList<Movie>> asyncTask;
    ArrayList<String> names = new ArrayList<String>();
    ImageView btnShow;
    @SuppressLint("StaticFieldLeak")
    static AutoCompleteTextView etSearch;
    ListFrag listFrag;
    static FragmentManager fragmentManager;

    public static ArrayList<String> stringNames = new ArrayList<String>();
    public static ArrayList<Boolean> stringValues = new ArrayList<Boolean>();
    public static final String MY_PREFS_FILENAME = "com.example.customization.Names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = (ImageView) findViewById(R.id.btnShow);
        etSearch = (AutoCompleteTextView) findViewById(R.id.etSearch);

        fragmentManager = this.getSupportFragmentManager();
        listFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);

        fragmentManager.beginTransaction()
                .hide(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.listFrag)))
                .commit();

        try {
            MovieSearchDB db = new MovieSearchDB(MainActivity.this);
            db.open();
            names = db.getData();
            db.close();
        } catch (android.database.SQLException ex) {
            ex.getMessage();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.custom_design_autocomplete, names);

        etSearch.setThreshold(1);
        etSearch.setAdapter(arrayAdapter);

//        SharedPreferences pef = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE);
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                doButton();
            }
        });
    }
    public void doButton() {
        String param = etSearch.getText().toString().trim();
        asyncTask = new data(MainActivity.this).execute(param);
        listFrag.setMyList((data) asyncTask, param);

        listFrag.notifyDataChanged();
        fragmentManager.beginTransaction()
                .show(Objects.requireNonNull(fragmentManager.findFragmentById(R.id.listFrag)))
                .commit();
    }
    public void clickBtnShow(View v) {
        doButton();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            MovieSearchDB db = new MovieSearchDB(MainActivity.this);
            db.open();
            for (int i = 0; i < stringValues.size(); i++) {
                if (!stringValues.get(i)) {
                    db.createEntry(stringNames.get(i));
                }
            }
            db.close();
        } catch (android.database.SQLException ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_FILENAME, MODE_PRIVATE).edit();
        editor.putString(etSearch.getText().toString().trim(), etSearch.getText().toString().trim());
        editor.apply();
    }
}
