package com.example.testlan1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText etName, etAddr, etRate;
    Button btnCreate, btnBack;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        etName = (EditText) findViewById(R.id.etName);
        etAddr = (EditText) findViewById(R.id.etAddr);
        etRate = (EditText) findViewById(R.id.etRate);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnBack = (Button) findViewById(R.id.btnBack);

        dbHelper = new DBHelper(SecondActivity.this);
        dbHelper.openDB();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValueString(etName).equals("") || getValueString(etAddr).equals("") || getValueString(etRate).equals(""))
                {
                    Toast.makeText(SecondActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    long resultAdd = dbHelper.Insert(getValueString(etName), getValueString(etAddr), Double.parseDouble(getValueString(etRate)));
                    if(resultAdd==-1){
                        Toast.makeText(SecondActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SecondActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
                        Dialog();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Thêm mới thành công ");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private String getValueString(EditText edt)
    {
        return edt.getText().toString().trim();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }
}
