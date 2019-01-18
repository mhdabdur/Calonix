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

public class TargetProfile extends AppCompatActivity {


    EditText editText1;
    EditText editText2;



        @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_target);

        editText1 = findViewById(R.id.editTargetWeight);
        editText2 = findViewById(R.id.editTargetTime);

    }

    public void calculate(View view) {
        String weight = editText1.getText().toString();
        String time = editText2.getText().toString();

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Profile", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS targetprofile");
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS targetprofile (id INTEGER PRIMARY KEY AUTOINCREMENT,weight int, time int)");

            String sqlString = "INSERT INTO targetprofile (weight,time) VALUES(?,?)";
            SQLiteStatement statement = sqLiteDatabase.compileStatement(sqlString);

            statement.bindString(1, weight);
            statement.bindString(2, time);
            statement.execute();


            //sqLiteDatabase.execSQL("DROP TABLE todolist");

            Intent intent = new Intent(TargetProfile.this, MainActivity.class);
            startActivity(intent);
            finish();


        } catch (Exception e) {
            e.printStackTrace();
        }

        }

}
