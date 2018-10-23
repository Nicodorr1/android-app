package com.example.nicodorr.nostress;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
*/

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "SettingsActivity";
    private static final String TAG = "TheName";


    private List<Car> myCars = new ArrayList<Car>();
      //  WebView mWebView;
      //  Button btnViewProducts;
      //  Button btnNewProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        String sSetting = prefs.getString("example_text","Stresskiller");
//
        TextView dName = (TextView)findViewById(R.id.Dname);
//        dName.setText(sSetting.toString());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("example_text", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
            dName.setText(name);
        }


        if (android.os.Build.VERSION.SDK_INT > 9) {
           StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        populateCarList();
        populateListView();
        registerClickCallBack();

       /* // Buttons
        btnViewProducts = (Button) findViewById(R.id.btnViewProducts);
        btnNewProduct = (Button) findViewById(R.id.add_new);

        // view products click event
        btnViewProducts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnNewProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                startActivity(i);

            }
        });

    */

    }


    private void populateCarList(){
        myCars.add(new Car("Crossing the graveyard", 2423, R.drawable.sm5, "A woman were crossing a graveyard during the night, she was so afraid, suddenly she sees a man, she relieved and join him quickly. Man seeing her face and said: Are you afraid? She answer: Yes! Then the man smiled and told her: It is normal, I was also very afraid of graveyard when I was alive. Exercise: please calculate the running speed of that woman , 10pts."));
        myCars.add(new Car("Jake and his grand-father", 2064, R.drawable.sm5,"Jake spent 3 weeks without going to school. One day he sit down on the balcony with his grand-father, they were joking together and they saw a staff from the school coming. His grand-father told him: \'Jake, hide yourself quickly because they will find out that you had nothing, so you were able to go to school\'. Jake answered to him: \'You are the one who should hide himself because I told them that you died, that\'s why I was not going to school\'."));
        myCars.add(new Car("At the cinema", 2015, R.drawable.sm5, "Peter went to cinema with his girlfriend, while watching the movie, she told him: honey, let\'s bet that the man with the black t-shirt will hide himself in that green house, then the man with the white t-shirt will kill him, though. -Peter: ok, for $20, I bet that it will not be like that. In a while, the action took place as she told. -The girl: yeah I won! Peter gave her $20, then she gave it to him back and said: I\'ve already seen the movie, I will not take your money. -Peter: I have already seen it too, but I did not think that the idiot would make the same mistake one more time."));
        myCars.add(new Car("The old woman", 1954, R.drawable.sm5, "After the huge evolution of the world, an old woman ask her daughter to buy a washing machine, the daughter bought her the washing machine and also a blender. After one week, the daughter asked: -Do they work mom? -Old woman: hmmm! I\'m telling you, the big one works with no problem, but the small one rips all of my panties!"));
        myCars.add(new Car("Technique in the army", 1016, R.drawable.sm5, "Jake and Joel started to work in the army. Two beds in each room. Joel never sleep because of his roommate that snores so loud. He then asked Jake to take his place at least one night he will be able to sleep. In the morning, Jake wok up as normal. -Joel: It seems that you slept, how could you? -Jake: it is simple, before going to bed, I kissed the snoreman on his cheek and I said: \'good night honey\', so he spent the whole night without sleeping, controlled me, he thought that I\'m a gay, so that he could protect himself if I tended to do something."));
        myCars.add(new Car("Jojo, the student", 1015, R.drawable.sm5, "After preschool, Jojo went to primary school, the teacher told his mom that there is no hope, no way to continue with the school. His mother did not discouraged, she changed him the school. 20 years later, that teacher falls seriously ill heart, all doctors said that no surgery can be done, she will die anyway. At last, a doctor made the surgery successful. When they wake her up after surgery, she open her eyes, a little bit ashamed. The doctor did not understand. She raised her hand and died, because Jojo was sweeping the surgery room and he unplugged the oxygen apparatus just to charge his phone."));
        myCars.add(new Car("The mad man", 995, R.drawable.sm5, "A mad man was sleeping on a bed. While sleeping, he rolled on the bed and fell down, he then woke up and went to bed again, he slept right after. After around 30mn, he fell down again, he woke up and he was very happy and was laughing so lound and saying Yes Yes Yes. When they asked him why he is laughing, he answered: \'I am happy because if I did not wake up the first time, this time I would fall down on me\'."));
        myCars.add(new Car("Toto and mosquitoes", 987, R.drawable.sm5, "He was killing mosquitoes that are going through his money and his beer. His wife then came to join him and ask him: What are you doing? -Toto: I\'m killing mosquitoes, I have already killed 15 females and 4 males. -His wife: how can you distinguish them? -Toto: it is so easy honey, males rest on the beer, females rest on the money."));
    }

    private void populateListView() {

        ArrayAdapter<Car> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.carsListView);

        LayoutInflater inflateHeader = getLayoutInflater();
        View headerView = inflateHeader.inflate(R.layout.headerlist, list, false);
        list.addHeaderView(headerView);


        LayoutInflater inflater = getLayoutInflater();
        View footerView = inflater.inflate(R.layout.footer, list, false);
        list.addFooterView(footerView);

        Button loadmore = (Button) findViewById(R.id.loadmore);

        // view load all product click event
        loadmore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(i);

            }
        });

        list.setAdapter(adapter);

    }


    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.carsListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position,long id){
                Car clickCar = myCars.get(position);
                String makeLa = clickCar.getMake();
                String cont = clickCar.getCondition();
                int likes1 = clickCar.getYear();
               // String message = clickCar.getMake() + "!! Please navigate the full list (All Jokes) to read content";
               // Toast.makeText(MainScreenActivity.this, message, Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainScreenActivity.this, Activity3.class);
                i.putExtra("parameter name", makeLa);
                i.putExtra("parameter name1", cont);
                i.putExtra("params2",likes1);
                startActivity(i);
            }
        });
    }


    private class MyListAdapter extends ArrayAdapter<Car> {
        public MyListAdapter(){
            super(MainScreenActivity.this, R.layout.item_car, myCars);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            // make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_car, parent, false);
            }
            // find the car to work with
            Car currentCar = myCars.get(position);

            // fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentCar.getIconID());

            // make
            TextView makeText = (TextView)itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentCar.getMake());


            return itemView;
        }


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
            Intent ss = new Intent(MainScreenActivity.this, SettingsActivity.class);
            startActivity(ss);
        }
        if (id == R.id.privacy) {
            Intent sp = new Intent(MainScreenActivity.this, Privacy.class);
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
            Intent pr = new Intent(MainScreenActivity.this, ChangeProfile.class);
            startActivity(pr);
        }

        else if (id == R.id.hhhm) {
            Intent ins = new Intent(MainScreenActivity.this, MainActivity.class);
            startActivity(ins);
        }

        else if (id == R.id.nav_all_jokes) {
            // Handle the camera action
            Intent i = new Intent(MainScreenActivity.this, AllProductsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_top_jokes) {
           // setContentView(R.layout.all_products);
        }
        else if (id == R.id.nav_create_joke) {
            Intent np = new Intent(MainScreenActivity.this, NewProductActivity.class);
            startActivity(np);
        }
        else if (id == R.id.im1){
            Intent im3 = new Intent(MainScreenActivity.this, WeekImage.class);
            startActivity(im3);
        }

        else if (id == R.id.comp) {
            Intent resl = new Intent(MainScreenActivity.this, Competition.class);
            startActivity(resl);
        }
        else if (id == R.id.vote) {
            Intent vot = new Intent(MainScreenActivity.this, Vote.class);
            startActivity(vot);
        }
        else if (id == R.id.set){
            Intent seti = new Intent(MainScreenActivity.this, SettingsActivity.class);
            startActivity(seti);
        }
        else if (id == R.id.nav_prv) {
            Intent prv = new Intent(MainScreenActivity.this, Privacy.class);
            startActivity(prv);
        }
        else if (id == R.id.help) {
            Intent hl = new Intent(MainScreenActivity.this, Helpus.class);
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
            Intent ct = new Intent(MainScreenActivity.this, ContactUs.class);
            startActivity(ct);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
