package com.example.nicodorr.nostress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private static final String URL_Test = "http://192.168.1.73:8080/StressKillerWeb/api/get_token.php";

    TextView namm, pric;
    ImageView pic;

    EditText firstname, lastname;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView u_na = (TextView) findViewById(R.id.u_name);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sSetting = prefs.getString("example_text","Stresskiller User");

        u_na.setText(sSetting);


        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);

        sqLiteHelper.queryData("create table if not exists food (id integer primary key autoincrement, name varchar, price varchar, image blob)");


        // get data from sqlite
        Cursor cursor = Profile.sqLiteHelper.getData("select * from food");

        namm = (TextView) findViewById(R.id.nam);
        pric = (TextView)findViewById(R.id.pri);
        pic = (ImageView)findViewById(R.id.imvd);

        firstname = (EditText)findViewById(R.id.edfn);
        lastname = (EditText)findViewById(R.id.edln);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

//            namm.setText(name.toString());
//            pric.setText(price.toString());
//
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//            pic.setImageBitmap(bitmap);


            if (name.isEmpty()){
                namm.setText("");
                pric.setText("");
                pic.setBackgroundResource(R.drawable.usrprof);
            }
            else{
                namm.setText(name.toString());
                pric.setText(price.toString());

                firstname.setHint("Change: " + name.toString());
                lastname.setHint("Change: " + price.toString());

                ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                pic.setImageBitmap(bitmap);
            }
        }

        //button to change firstname & lastname
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(Profile.this);
            }
        });

        Button tryit = (Button) findViewById(R.id.btni);
        tryit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
//                    Profile.sqLiteHelper.updateData(
//                            firstname.getText().toString().trim(),
//                            lastname.getText().toString().trim()
//                    );
//                    Toast.makeText(getApplicationContext(), "It should be updated", Toast.LENGTH_LONG).show();
//                }
//                catch (Exception error){
//                    Log.e("Update error: ",  error.getMessage() );
//                }
            }
        });

    }

    private void showDialogUpdate(Activity activity){
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_db_activity);
        dialog.setTitle("Update");

        ImageView imgU = (ImageView) dialog.findViewById(R.id.imgUpd);


        //setting width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);
        dialog.getWindow().setLayout(width, height);
        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);

        sqLiteHelper.queryData("create table if not exists food (id integer primary key autoincrement, name varchar, price varchar, image blob)");


        // get data from sqlite
        Cursor cursor = Profile.sqLiteHelper.getData("select * from food");

        namm = (TextView) findViewById(R.id.nam);
        pric = (TextView)findViewById(R.id.pri);
        pic = (ImageView)findViewById(R.id.imvd);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgU.setImageBitmap(bitmap);
        }
        dialog.show();
    }

    public void chancepic(View view) {
        Intent intsd = new Intent(getApplicationContext(), ChangeProfile.class);
        startActivity(intsd);
    }


}
