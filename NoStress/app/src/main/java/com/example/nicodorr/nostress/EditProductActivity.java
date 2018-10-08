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
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicodorr on 17.11.2016.
 */
public class EditProductActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtPrice;
    WebView txtDesc;
    EditText txtCreatedAt;
    Button btnSave;
    Button btnDelete;

    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_product_details = "http://efhmi.org/api/get_product_details.php";

    // url to update product
    private static final String url_update_product = "http://efhmi.org/api/update_product.php";

    // url to delete product
    private static final String url_delete_product = "http://efhmi.org/api/delete_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_JOKE = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_JOKE_TITLE = "joke_title";
    private static final String TAG_DESC = "description";
    private static final String TAG_AUTHORNAME = "authorname";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isConnected(EditProductActivity.this)) buildDialog(EditProductActivity.this).show();
        else {
            setContentView(R.layout.edit_product);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // save button
            btnSave = (Button) findViewById(R.id.btnSave);
            btnDelete = (Button) findViewById(R.id.btnDelete);

            // getting product details from intent
            Intent i = getIntent();

            // getting product id (pid) from intent
            pid = i.getStringExtra(TAG_PID);

            // Getting complete product details in background thread
            new GetProductDetails().execute();

            // save button click event
            btnSave.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // starting background task to update product
                    new SaveProductDetails().execute();
                }
            });

            // Delete button click event
            btnDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // deleting product in background thread
                    new DeleteProduct().execute();
                }
            });
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Loading joke details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("pid", pid));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_details, "GET", params);

                        // check your log for json response
                        Log.d("Single Product Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray jokeObj = json
                                    .getJSONArray(TAG_JOKE); // JSON Array

                            // get first product object from JSON Array
                            JSONObject joke = jokeObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            txtName = (TextView) findViewById(R.id.inputName);
                            txtPrice = (TextView) findViewById(R.id.inputPrice);
                            txtDesc = (WebView) findViewById(R.id.inputDesc);

                            // display product data in EditText
                            txtName.setText(joke.getString(TAG_JOKE_TITLE));
                            txtPrice.setText("Posted by: " + joke.getString(TAG_AUTHORNAME));
                            String myDis = joke.getString(TAG_DESC);
                            String mes = joke.getString(TAG_JOKE_TITLE);
                            //txtDesc.setText(joke.getString(TAG_DESC));
                            String text = "<html><head>"
                                    +"<style type=\'text/css\'>body{color: #000; font-size: 18px;}"
                                    +"</style></head>"
                                    +"<body>"
                                    +"<p align='justify'>"
                                    +myDis
                                    +"</p>"
                                    +"</body></html>";
                            txtDesc.loadData(text,"text/html","UTF-8");
                            txtDesc.setBackgroundColor(0x00000000);

                             String message =  "You are reading: " +mes;
                             Toast.makeText(EditProductActivity.this, message, Toast.LENGTH_LONG).show();

                        }else{
                            // product with pid not found
                            String message =  "Oups!! Something went wrong";
                            Toast.makeText(EditProductActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }


    /**
     * Background Async Task to  Save product Details
     * */
    class SaveProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Saving joke ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            String name = args[0];
            String price = args[1];
            String description = args[2];

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_JOKE_TITLE, name));
            params.add(new BasicNameValuePair(TAG_AUTHORNAME, price));
            params.add(new BasicNameValuePair(TAG_JOKE, description));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProductActivity.this);
            pDialog.setMessage("Deleting joke...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("pid", pid));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_product, "POST", params);

                // check your log for json response
                Log.d("Delete Product", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
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
            // dismiss the dialog once product deleted
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

