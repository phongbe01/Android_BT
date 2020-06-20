package com.example.testlan1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    EditText search;
    ResAdapter adapter;
    ArrayList<Restaurant> ress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        search = (EditText) findViewById(R.id.edSearch);
        listView = (ListView) findViewById(R.id.listRes);
        dbHelper = new DBHelper(MainActivity.this);
        dbHelper.openDB();
        //addItem();
        ress = dbHelper.getAll();
        adapter = new ResAdapter(ress, MainActivity.this);
        String[] arr = {"1", "2"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        //delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteItem(position);
                return false;
            }
        });

        //update
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant res = ress.get(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("RES", res);
                startActivity(intent);
            }
        });

        //search
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void addItem()
    {
        long x = 0;
        x += dbHelper.Insert("Quan ngon Ha Noi",  "Lac Long Quan 2", 8.6);
        x += dbHelper.Insert( "Non La",  "Lac Long Quan 3", 8.6);
        x += dbHelper.Insert( "Sen Tay Ho ",  "Lac Long Quan 4", 8.6);
        x += dbHelper.Insert("Ly Club",  "Le Phung Hieu", 7.6);
        x += dbHelper.Insert("Cham Cham",  "Phan Van Chuong", 7.6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void DeleteItem(final int position)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Bạn có muốn xóa? ");
        dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.delete(ress.get(position).getMa());
                ress.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
