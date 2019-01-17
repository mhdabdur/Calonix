package com.ffm.calonix;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    static SQLiteDatabase sqLiteDatabase;
    double weightcv, agev, heightv, weightTv, timev,bmi,tde, hasil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.botton_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        button = findViewById(R.id.editProfile);



//        sqLiteDatabase = this.openOrCreateDatabase("Profile", MODE_PRIVATE, null);
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM currentprofile",null);
//        int currentWeight = cursor.getColumnIndex("weight");
//        TextView textView = (TextView) findViewById(R.id.calorie);
//        String tmpStr10 = String.valueOf(currentWeight);
//        textView.setText(tmpStr10);

        try {
            DBManager dbManager = new DBManager(this);
            dbManager.open();
            Cursor cursor = dbManager.fetch(1);
            Cursor cursor1 = dbManager.fetch(2);
            cursor.moveToFirst();
            cursor1.moveToFirst();


            weightcv = cursor.getInt(1);
            agev = cursor.getInt(2);
            heightv = cursor.getInt(3);

            weightTv = cursor1.getInt(1);
            timev = cursor1.getInt(2);



            update(weightcv,heightv,agev,weightTv,timev);



        }
            catch (SQLiteException e) {
                if (e.getMessage().contains("no such table")) {
                    Intent intent = new Intent(MainActivity.this, CurrentProfile.class);
                    startActivity(intent);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentProfile.class);
                startActivity(intent);
            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_calculate:
                break;
                case R.id.nav_todo:
                    Intent intent = new Intent(MainActivity.this, TodoMain.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        }
    };

    private void update( double weightcv , double heightv,double agev,double weightTv, double timev){
        double delta, multipleTime;
        DecimalFormat df = new DecimalFormat("#.##");

        delta = weightcv - weightTv;
        tde = 66+(11*weightcv)+(5*heightv)+(-6.8*agev);

        multipleTime= delta/(0.5*timev);
        hasil= tde - (100*multipleTime);

        generateMeal(hasil);

        String tmpStr10 = String.valueOf(df.format(hasil));
        TextView calorie = (TextView) findViewById(R.id.calorie);
        calorie.setText(tmpStr10);

    }

    private void generateMeal(double target){
        ImageView imgPagi,imgSiang,imgMalam,imgSnack,imgMinum;
        TextView imgPagiTitle,imgSiangTitle,imgMalamTitle,imgMinumTitle,imgSnackTitle;
        TextView imgPagiCal,imgSiangCal,imgMalamCal,imgMinumJml,imgSnackCal;

        DecimalFormat df = new DecimalFormat("#.##");


        imgPagi = (ImageView) findViewById(R.id.imgPagi);
        imgSiang = (ImageView) findViewById(R.id.imgSiang);
        imgMalam = (ImageView) findViewById(R.id.imgMalam);
        imgSnack = (ImageView) findViewById(R.id.imgSnack);
        imgMinum = (ImageView) findViewById(R.id.imgMinum);

        imgPagiTitle = (TextView) findViewById(R.id.imgPagiTitle);
        imgSiangTitle= (TextView) findViewById(R.id.imgSiangTitle);
        imgMalamTitle= (TextView) findViewById(R.id.imgMalamTitle);
        imgMinumTitle= (TextView) findViewById(R.id.imgMinumTitle);
        imgSnackTitle= (TextView) findViewById(R.id.imgSnackTitle);

        imgPagiCal= (TextView) findViewById(R.id.imgPagiCalorie);
        imgSiangCal= (TextView) findViewById(R.id.imgSiangCalorie);
        imgMalamCal= (TextView) findViewById(R.id.imgMalamCalorie);
        imgMinumJml= (TextView) findViewById(R.id.imgMinumJumlah);
        imgSnackCal= (TextView) findViewById(R.id.imgSnackCalorie);





        if(target > 2350){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.soto);
            imgSiang.setBackgroundResource(R.drawable.sate_kambing);
            imgMalam.setBackgroundResource(R.drawable.ayam_krispi);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.soto));
            imgSiangTitle.setText(getString(R.string.sate));
            imgMalamTitle.setText(getString(R.string.ayam_krispi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.sate_kambing_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));


        }else if(target > 2200){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.pecel_lele);
            imgSiang.setBackgroundResource(R.drawable.ayam_krispi);
            imgMalam.setBackgroundResource(R.drawable.sate_kambing);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.pecel_lele));
            imgSiangTitle.setText(getString(R.string.ayam_krispi));
            imgMalamTitle.setText(getString(R.string.sate));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgMalamCal.setText(getString(R.string.sate_kambing_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));

        }else if (target > 2100){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.soto);
            imgSiang.setBackgroundResource(R.drawable.tuna);
            imgMalam.setBackgroundResource(R.drawable.sate_kambing);
            imgMinum.setBackgroundResource(R.drawable.airputih);
            imgSnack.setBackgroundResource(R.drawable.fitbar);

            //title
            imgPagiTitle.setText(getString(R.string.soto));
            imgSiangTitle.setText(getString(R.string.tuna));
            imgMalamTitle.setText(getString(R.string.sate));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.fitbar));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.tuna_kkcl));
            imgMalamCal.setText(getString(R.string.sate_kambing_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));
            imgSnackCal.setText(getString(R.string.fitbar_kkcl));


        }else if (target > 2000){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.soto);
            imgSiang.setBackgroundResource(R.drawable.pecel_lele);
            imgMalam.setBackgroundResource(R.drawable.ayam_krispi);
            imgSnack.setBackgroundResource(R.drawable.fitbar);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.soto));
            imgSiangTitle.setText(getString(R.string.pecel_lele));
            imgMalamTitle.setText(getString(R.string.ayam_krispi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.fitbar));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.pecel_lele_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSnackCal.setText(getString(R.string.fitbar_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));


        }else if (target > 1900){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.nasi_goreng);
            imgSiang.setBackgroundResource(R.drawable.sate_kambing);
            imgMalam.setBackgroundResource(R.drawable.mie_ayam);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.nasgor));
            imgSiangTitle.setText(getString(R.string.sate));
            imgMalamTitle.setText(getString(R.string.mie_ayam));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.nasi_goreng_kkcl));
            imgSiangCal.setText(getString(R.string.sate_kambing_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));


        }else if (target > 1800){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.ayam_krispi);
            imgSiang.setBackgroundResource(R.drawable.pecel_lele);
            imgMalam.setBackgroundResource(R.drawable.pempek);
            imgSnack.setBackgroundResource(R.drawable.tictac);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.ayam_krispi));
            imgSiangTitle.setText(getString(R.string.pecel_lele));
            imgMalamTitle.setText(getString(R.string.pempek));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.tictac));

            //deskripsi
            imgPagiCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSiangCal.setText(getString(R.string.pecel_lele_kkcl));
            imgMalamCal.setText(getString(R.string.pempek_kkcl));
            imgSnackCal.setText(getString(R.string.tictac_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));


        }else if (target > 1700){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.soto);
            imgSiang.setBackgroundResource(R.drawable.tuna);
            imgMalam.setBackgroundResource(R.drawable.ayam_krispi);
            imgSnack.setBackgroundResource(R.drawable.tictac);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.soto));
            imgSiangTitle.setText(getString(R.string.tuna));
            imgMalamTitle.setText(getString(R.string.ayam_krispi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.tictac));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.tuna_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSnackCal.setText(getString(R.string.tictac_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali10));


        }else if (target > 1600){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.pecel_lele);
            imgSiang.setBackgroundResource(R.drawable.nasi_goreng);
            imgMalam.setBackgroundResource(R.drawable.bakso);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.pecel_lele));
            imgSiangTitle.setText(getString(R.string.nasgor));
            imgMalamTitle.setText(getString(R.string.bakso));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.pecel_lele_kkcl));
            imgSiangCal.setText(getString(R.string.nasi_goreng_kkcl));
            imgMalamCal.setText(getString(R.string.bakso_kkcl));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1500){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tuna);
            imgSiang.setBackgroundResource(R.drawable.pecel_lele);
            imgMalam.setBackgroundResource(R.drawable.pempek);
            imgSnack.setBackgroundResource(R.drawable.tortila);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tuna));
            imgSiangTitle.setText(getString(R.string.pecel_lele));
            imgMalamTitle.setText(getString(R.string.pempek));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.tortila));

            //deskripsi
            imgPagiCal.setText(getString(R.string.tuna_kkcl));
            imgSiangCal.setText(getString(R.string.pecel_lele_kkcl));
            imgMalamCal.setText(getString(R.string.pempek_kkcl));
            imgSnackCal.setText(getString(R.string.tortila_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1400){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tuna);
            imgSiang.setBackgroundResource(R.drawable.ayambakar);
            imgMalam.setBackgroundResource(R.drawable.bakso);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tuna));
            imgSiangTitle.setText(getString(R.string.ayam_bakar));
            imgMalamTitle.setText(getString(R.string.bakso));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.tuna_kkcl));
            imgSiangCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgMalamCal.setText(getString(R.string.bakso_kkcl));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1300){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.nasi_goreng);
            imgSiang.setBackgroundResource(R.drawable.tuna);
            imgMalam.setBackgroundResource(R.drawable.pempek);
            imgSnack.setBackgroundResource(R.drawable.tictac);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.nasgor));
            imgSiangTitle.setText(getString(R.string.tuna));
            imgMalamTitle.setText(getString(R.string.pempek));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.tictac));

            //deskripsi
            imgPagiCal.setText(getString(R.string.nasi_goreng_kkcl));
            imgSiangCal.setText(getString(R.string.tuna_kkcl));
            imgMalamCal.setText(getString(R.string.pempek_kkcl));
            imgSnackCal.setText(getString(R.string.tictac_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1200){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.nasi_goreng);
            imgSiang.setBackgroundResource(R.drawable.mie_ayam);
            imgMalam.setBackgroundResource(R.drawable.pempek);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.nasgor));
            imgSiangTitle.setText(getString(R.string.mie_ayam));
            imgMalamTitle.setText(getString(R.string.ayam_krispi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.mie_ayam_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1100){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tuna);
            imgSiang.setBackgroundResource(R.drawable.pempek);
            imgMalam.setBackgroundResource(R.drawable.cumi);
            imgSnack.setBackgroundResource(R.drawable.tortila);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tuna));
            imgSiangTitle.setText(getString(R.string.pempek));
            imgMalamTitle.setText(getString(R.string.cumi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.tortila));

            //deskripsi
            imgPagiCal.setText(getString(R.string.soto_kkcl));
            imgSiangCal.setText(getString(R.string.sate_kambing_kkcl));
            imgMalamCal.setText(getString(R.string.ayam_krispi_kkcl));
            imgSnackCal.setText(getString(R.string.tortila_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali9));


        }else if (target > 1000){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tuna);
            imgSiang.setBackgroundResource(R.drawable.mie_ayam);
            imgMalam.setBackgroundResource(R.drawable.cumi);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tuna));
            imgSiangTitle.setText(getString(R.string.mie_ayam));
            imgMalamTitle.setText(getString(R.string.cumi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.tuna_kkcl));
            imgSiangCal.setText(getString(R.string.mie_ayam_kkcl));
            imgMalamCal.setText(getString(R.string.cumi_kkcl));
            imgSnackCal.setText(getString(R.string.cheetos));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else if (target > 900){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.salmon);
            imgSiang.setBackgroundResource(R.drawable.mie_ayam);
            imgMalam.setBackgroundResource(R.drawable.bakso);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.salmon));
            imgSiangTitle.setText(getString(R.string.mie_ayam));
            imgMalamTitle.setText(getString(R.string.bakso));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.cheetos));

            //deskripsi
            imgPagiCal.setText(getString(R.string.salmon_kkcl));
            imgSiangCal.setText(getString(R.string.mie_ayam_kkcl));
            imgMalamCal.setText(getString(R.string.bakso_kkcl));
            imgSnackCal.setText(getString(R.string.chitose_kkcl));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else if (target > 800){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.salmon);
            imgSiang.setBackgroundResource(R.drawable.cumi);
            imgMalam.setBackgroundResource(R.drawable.pempek);
            imgSnack.setBackgroundResource(R.drawable.xicon);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.salmon));
            imgSiangTitle.setText(getString(R.string.cumi));
            imgMalamTitle.setText(getString(R.string.pempek));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.nosnack));

            //deskripsi
            imgPagiCal.setText(getString(R.string.salmon_kkcl));
            imgSiangCal.setText(getString(R.string.cumi_kkcl));
            imgMalamCal.setText(getString(R.string.pempek_kkcl));
            imgSnackCal.setText(getString(R.string.nosnack));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else if (target > 700){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.cumi);
            imgSiang.setBackgroundResource(R.drawable.bakso);
            imgMalam.setBackgroundResource(R.drawable.salmon);
            imgSnack.setBackgroundResource(R.drawable.xicon);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.cumi));
            imgSiangTitle.setText(getString(R.string.bakso));
            imgMalamTitle.setText(getString(R.string.salmon));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.nosnack));

            //deskripsi
            imgPagiCal.setText(getString(R.string.cumi_kkcl));
            imgSiangCal.setText(getString(R.string.bakso_kkcl));
            imgMalamCal.setText(getString(R.string.salmon_kkcl));
            imgSnackCal.setText(getString(R.string.nosnack));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else if (target > 600){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tempe);
            imgSiang.setBackgroundResource(R.drawable.cumi);
            imgMalam.setBackgroundResource(R.drawable.salmon);
            imgSnack.setBackgroundResource(R.drawable.xicon);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tempe));
            imgSiangTitle.setText(getString(R.string.cumi));
            imgMalamTitle.setText(getString(R.string.salmon));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.nosnack));

            //deskripsi
            imgPagiCal.setText(getString(R.string.tempe_kkcl));
            imgSiangCal.setText(getString(R.string.cumi_kkcl));
            imgMalamCal.setText(getString(R.string.salmon_kkcl));
            imgSnackCal.setText(getString(R.string.nosnack));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else if (target > 500){

            //gambar
            imgPagi.setBackgroundResource(R.drawable.salad);
            imgSiang.setBackgroundResource(R.drawable.salmon);
            imgMalam.setBackgroundResource(R.drawable.cumi);
            imgSnack.setBackgroundResource(R.drawable.chitose);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.salad));
            imgSiangTitle.setText(getString(R.string.salmon));
            imgMalamTitle.setText(getString(R.string.cumi));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.nosnack));

            //deskripsi
            imgPagiCal.setText(getString(R.string.salad_kkcl));
            imgSiangCal.setText(getString(R.string.salmon_kkcl));
            imgMalamCal.setText(getString(R.string.cumi_kkcl));
            imgSnackCal.setText(getString(R.string.nosnack));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }else {

            //gambar
            imgPagi.setBackgroundResource(R.drawable.tahu);
            imgSiang.setBackgroundResource(R.drawable.salad);
            imgMalam.setBackgroundResource(R.drawable.salmon);
            imgSnack.setBackgroundResource(R.drawable.xicon);
            imgMinum.setBackgroundResource(R.drawable.airputih);

            //title
            imgPagiTitle.setText(getString(R.string.tahu));
            imgSiangTitle.setText(getString(R.string.salad));
            imgMalamTitle.setText(getString(R.string.salmon));
            imgMinumTitle.setText(getString(R.string.air));
            imgSnackTitle.setText(getString(R.string.nosnack));

            //deskripsi
            imgPagiCal.setText(getString(R.string.tahu_kkcl));
            imgSiangCal.setText(getString(R.string.salad_kkcl));
            imgMalamCal.setText(getString(R.string.salmon_kkcl));
            imgSnackCal.setText(getString(R.string.nosnack));
            imgMinumJml.setText(getString(R.string.air_kali8));


        }
    }
}
