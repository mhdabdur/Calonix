package com.ffm.calonix;

import android.content.Intent;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.ffm.calonix.DBconnections.sqLiteDatabase;

public class CurrentProfile extends AppCompatActivity {


    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_curent);


        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);



    }

    public void currentNext(View view) {
        String age = editText1.getText().toString();
        String weight = editText2.getText().toString();
        String height = editText3.getText().toString();

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Profile", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS currentprofile");
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS currentprofile (ID INTEGER PRIMARY KEY AUTOINCREMENT, weight int, age int, height int)");

            String sqlString = "INSERT INTO currentprofile (age,weight,height) VALUES(?,?,?)";
            SQLiteStatement statement = sqLiteDatabase.compileStatement(sqlString);
            statement.bindString(1, age);
            statement.bindString(2, weight);
            statement.bindString(3, height);
            statement.execute();


            //sqLiteDatabase.execSQL("DROP TABLE todolist");

            Intent intent = new Intent(CurrentProfile.this, TargetProfile.class);
            startActivity(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
