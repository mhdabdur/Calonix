package com.ffm.calonix;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoMain extends AppCompatActivity {

    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_main);

        button = findViewById(R.id.addActivity);
        listView = findViewById(R.id.listViewTodo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DBconnections.class);
                intent.putExtra("info","new");
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.task_title);
        String s = String.valueOf(taskTextView.getText());

        String sqlString = "DELETE FROM todolist WHERE name = (?)";
        DBconnections.sqLiteDatabase = this.openOrCreateDatabase("Todolist", MODE_PRIVATE, null);
        SQLiteStatement statement = DBconnections.sqLiteDatabase.compileStatement(sqlString);
        statement.bindString(1, s);
        statement.execute();

        onStart();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<String> taskList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.task_item, R.id.task_title, taskList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DBconnections.class);
                intent.putExtra("info", "old");
                intent.putExtra("name", taskList.get(i));
                startActivity(intent);
            }
        });

        try {
            DBconnections.sqLiteDatabase = this.openOrCreateDatabase("Todolist", MODE_PRIVATE, null);
            DBconnections.sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todolist (name VARCHAR)");
            Cursor cursor = DBconnections.sqLiteDatabase.rawQuery("SELECT * FROM todolist", null);

            int nameIx = cursor.getColumnIndex("name");
            cursor.moveToFirst();

            while(cursor != null) {
                taskList.add(cursor.getString(nameIx));
                cursor.moveToNext();
                arrayAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_calculate:
                    Intent intent = new Intent(TodoMain.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.nav_todo:
                    break;
            }

            return true;
        }
    };

}
