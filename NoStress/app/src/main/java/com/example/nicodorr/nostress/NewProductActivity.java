package com.example.nicodorr.nostress;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicodorr on 17.11.2016.
 */
public class NewProductActivity extends AppCompatActivity {

    Button btnCreateProduct;
    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputEmail;
    EditText inputTitle;
    EditText inputGenre;
    EditText inputContent;

    // url to create new product
    private static String url_create_product = "http://efhmi.org/api/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(NewProductActivity.this)) buildDialog(NewProductActivity.this).show();
        else {
            setContentView(R.layout.add_product);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Edit Text
            inputName = (EditText) findViewById(R.id.inputName);
            inputEmail = (EditText) findViewById(R.id.inputEmail);
            inputTitle = (EditText) findViewById(R.id.inputTitle);
            inputGenre = (EditText) findViewById(R.id.inputGenre);
            inputContent = (EditText) findViewById(R.id.inputContent);

            // Create button
            btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

            CheckBox mcheck = (CheckBox) findViewById(R.id.checkBox);
            mcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked){
                        btnCreateProduct.setEnabled(true);
                        btnCreateProduct.setVisibility(View.VISIBLE);
                    }
                    else {
                        btnCreateProduct.setEnabled(false);
                        btnCreateProduct.setVisibility(View.GONE);
                    }
                }
            });

            // button click event
            btnCreateProduct.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // creating new product in background thread
                    String authorname = inputName.getText().toString();
                    String authormail = inputEmail.getText().toString();
                    String joke_title = inputTitle.getText().toString();
                    String joke_categorie = inputGenre.getText().toString();
                    String description = inputContent.getText().toString();
                    new CreateNewProduct().execute(authorname, authormail, joke_title, joke_categorie, description);
                }
            });
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewProductActivity.this);
            pDialog.setMessage("Creating StressKiller..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String authorname = args[0];
            String authormail = args[1];
            String joke_title = args[2];
            String joke_categorie = args[3];
            String description = args[4];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("authorname", authorname));
            params.add(new BasicNameValuePair("authormail", authormail));
            params.add(new BasicNameValuePair("joke_title", joke_title));
            params.add(new BasicNameValuePair("joke_categorie", joke_categorie));
            params.add(new BasicNameValuePair("description", description));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(NewProductActivity.this, AllProductsActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                    Intent in = new Intent(NewProductActivity.this, AllProductsActivity.class);
                    startActivity(in);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
    //pou internet connection an
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
    //li fini la
}
