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

public class EditActivity extends AppCompatActivity {

    EditText etName, etAddr, etRate;
    Button btnEdit, btnBack;
    DBHelper dbHelper;
    Restaurant res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etName = (EditText) findViewById(R.id.etName);
        etAddr = (EditText) findViewById(R.id.etAddr);
        etRate = (EditText) findViewById(R.id.etRate);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnBack = (Button) findViewById(R.id.btnBack);

        dbHelper = new DBHelper(EditActivity.this);
        dbHelper.openDB();

        Intent intent = getIntent();
        res = (Restaurant) intent.getSerializableExtra("RES");

        etName.setText(res.getTen());
        etAddr.setText(res.getDiaChi());
        etRate.setText(res.getDanhGia()+ "");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getValueString(etName).equals("") || getValueString(etAddr).equals("") || getValueString(etRate).equals(""))
                {
                    Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    long resultEdit = dbHelper.update(res.getMa(), getValueString(etName), getValueString(etAddr), Double.parseDouble(getValueString(etRate)));
                    if(resultEdit==-1){
                        Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
                        Dialog();
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Cập nhập thành công ");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
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
