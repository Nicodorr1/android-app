package com.example.nicodorr.nostress;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.ByteArrayInputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewFlipper v_flip;
    ImageView l1, a1, c1, vo1, p1, pic, cro, jak, theodl;
    TextView l2, a2, c2, vo2, p2;
    TextView usrname;

    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initiate
        l1 = (ImageView) findViewById(R.id.lis1);
        a1 = (ImageView) findViewById(R.id.add1);
        c1 = (ImageView) findViewById(R.id.com1);
        vo1 = (ImageView) findViewById(R.id.vot1);
        p1 = (ImageView) findViewById(R.id.pro1);

        cro = (ImageView)findViewById(R.id.ctgyar);
        jak = (ImageView)findViewById(R.id.jhgfat);
        theodl = (ImageView)findViewById(R.id.towom);

        l2 = (TextView) findViewById(R.id.lis2);
        a2 = (TextView) findViewById(R.id.add2);
        c2 = (TextView) findViewById(R.id.com2);
        vo2 = (TextView) findViewById(R.id.vot2);
        p2 = (TextView) findViewById(R.id.pro2);


        // get data from sqlite

        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);

        sqLiteHelper.queryData("create table if not exists food (id integer primary key autoincrement, name varchar, price varchar, image blob)");


        Cursor cursor = MainActivity.sqLiteHelper.getData("select * from food");

        usrname = (TextView) findViewById(R.id.unho);
        pic = (ImageView)findViewById(R.id.imd2);

       // int icount = cursor.getInt(0);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            if (name.isEmpty()){
                usrname.setText("");
                pic.setBackgroundResource(R.drawable.usrprof);
            }
            else{
                usrname.setText(name.toString());

                ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                pic.setImageBitmap(bitmap);
            }

        }

        //handle clicks
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotolist = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(gotolist);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tolist = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(tolist);
            }
        });
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoad = new Intent(getApplicationContext(), NewProductActivity.class);
                startActivity(gotoad);
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toadd = new Intent(getApplicationContext(), NewProductActivity.class);
                startActivity(toadd);
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tocomp = new Intent(getApplicationContext(), Competition.class);
                startActivity(tocomp);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gtocomp = new Intent(getApplicationContext(), Competition.class);
                startActivity(gtocomp);
            }
        });
        vo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gtocomp = new Intent(getApplicationContext(), Vote.class);
                startActivity(gtocomp);
            }
        });
        vo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gtocomp = new Intent(getApplicationContext(), Vote.class);
                startActivity(gtocomp);
            }
        });
        cro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makeLa = "Crossing the graveyard";
                String cont = "A woman were crossing a graveyard during the night, she was so afraid, suddenly she sees a man, she relieved and join him quickly. Man seeing her face and said: Are you afraid? She answer: Yes! Then the man smiled and told her: It is normal, I was also very afraid of graveyard when I was alive. Exercise: please calculate the running speed of that woman , 10pts.";
                int likes1 = 2423;
                Intent i = new Intent(MainActivity.this, Activity3.class);
                i.putExtra("parameter name", makeLa);
                i.putExtra("parameter name1", cont);
                i.putExtra("params2",likes1);
                startActivity(i);
            }
        });

        jak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makeLa = "Jake and his grand-father";
                String cont = "Jake spent 3 weeks without going to school. One day he sit down on the balcony with his grand-father, they were joking together and they saw a staff from the school coming. His grand-father told him: 'Jake, hide yourself quickly because they will find out that you had nothing, so you were able to go to school'. Jake answered to him: 'You are the one who should hide himself because I told them that you died, that's why I was not going to school'.";
                int likes1 = 2064;
                Intent i = new Intent(MainActivity.this, Activity3.class);
                i.putExtra("parameter name", makeLa);
                i.putExtra("parameter name1", cont);
                i.putExtra("params2",likes1);
                startActivity(i);
            }
        });

        theodl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makeLa = "The old woman";
                String cont = "After the huge evolution of the world, an old woman ask her daughter to buy a washing machine, the daughter bought her the washing machine and also a blender. After one week, the daughter asked: -Do they work mom? -Old woman: hmmm! I'm telling you, the big one works with no problem, but the small one rips all of my panties!";
                int likes1 = 1954;
                Intent i = new Intent(MainActivity.this, Activity3.class);
                i.putExtra("parameter name", makeLa);
                i.putExtra("parameter name1", cont);
                i.putExtra("params2",likes1);
                startActivity(i);
            }
        });

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inted = new Intent(MainActivity.this, Profile.class);
                startActivity(inted);
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inteds = new Intent(MainActivity.this, Profile.class);
                startActivity(inteds);
            }
        });


        int images[] = {R.drawable.tob, R.drawable.topb, R.drawable.bgt};
        v_flip = (ViewFlipper) findViewById(R.id.vfl);

        for (int i = 0; i < images.length; i++){
            flipperimages(images[i]);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void flipperimages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        v_flip.addView(imageView);
        v_flip.setFlipInterval(5000);
        v_flip.setAutoStart(true);

        //add animation
        v_flip.setInAnimation(this, android.R.anim.slide_in_left);
        v_flip.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
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
            Intent ss = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(ss);
        }
        if (id == R.id.privacy) {
            Intent sp = new Intent(MainActivity.this, Privacy.class);
            startActivity(sp);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profie){
            Intent pr = new Intent(MainActivity.this, ChangeProfile.class);
            startActivity(pr);
        }

//        else if (id == R.id.homet) {
//           //nothing here
//        }

        else if (id == R.id.nav_all_jokes) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this, AllProductsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_top_jokes) {
            Intent i = new Intent(MainActivity.this, MainScreenActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_create_joke) {
            Intent np = new Intent(MainActivity.this, NewProductActivity.class);
            startActivity(np);
        }
        else if (id == R.id.im1){
            Intent im3 = new Intent(MainActivity.this, WeekImage.class);
            startActivity(im3);
        }

        else if (id == R.id.comp) {
            Intent resl = new Intent(MainActivity.this, Competition.class);
            startActivity(resl);
        }
        else if (id == R.id.vote) {
            Intent vot = new Intent(MainActivity.this, Vote.class);
            startActivity(vot);
        }
        else if (id == R.id.set){
            Intent seti = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(seti);
        }
        else if (id == R.id.nav_prv) {
            Intent prv = new Intent(MainActivity.this, Privacy.class);
            startActivity(prv);
        }
        else if (id == R.id.help) {
            Intent hl = new Intent(MainActivity.this, Helpus.class);
            startActivity(hl);
        }
        else if (id == R.id.nav_share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Stress Killer");
                String sAux = "\nStresskiller, a good application for you\n\n";
                sAux = sAux + "https://play.google.com/store/apps/ \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose an App"));
            } catch(Exception e) {
                //e.toString();
            }
        }

        else if (id == R.id.contact){
            Intent ct = new Intent(MainActivity.this, ContactUs.class);
            startActivity(ct);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
