package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private EditText editTextText2;
    private TextView textView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        editTextText2 = findViewById(R.id.editTextText2);
        textView = findViewById(R.id.textView);

        // Create an instance of DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform write operation
                String inputText = editTextText2.getText().toString();
                if (!inputText.isEmpty()) {
                    insertData(inputText);
                    editTextText2.setText("");
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = readData();
                textView.setText(data);
            }
        });
    }

    private void insertData(String text) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data", text);
        db.insert("myTable", null, values);
        db.close();
    }

    private String readData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("myTable", null, null, null, null, null, null);
        StringBuilder dataBuilder = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndex("data"));
                dataBuilder.append(data).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataBuilder.toString();
    }
}