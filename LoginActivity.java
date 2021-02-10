package lab1.adsalesapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;

import cz.msebera.android.httpclient.Header;
import lab1.adsalesapp.Utils.CheckNetwork;
import lab1.adsalesapp.Utils.PrefManager;
import lab1.adsalesapp.Utils.Urls;

public class LoginActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    Button btnSubmit;
    TextInputLayout text_input_username,text_input_password;
    TextInputEditText empId,password;
    LinearLayout coordinatorLayout;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        checkLocationPermission();

        btnSubmit = findViewById(R.id.btnSubmit);

        text_input_username = findViewById(R.id.text_input_username);
        text_input_password = findViewById(R.id.text_input_password);
        empId = findViewById(R.id.empId);
        password = findViewById(R.id.password);

        empId.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);

        coordinatorLayout= findViewById(R.id.coordinatorLayout);


        empId.setText(PrefManager.getEmpCode(LoginActivity.this, "username"));
        password.setText(PrefManager.getPassword(LoginActivity.this, "password"));


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (empId.getText().toString().isEmpty()){
                    text_input_username.setError("Please Enter Employee Id");
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    text_input_password.setError("Please Enter Password");
                    return;
                }

                loginData(empId.getText().toString(),password.getText().toString());
            }
        });

    }

    private void loginData(String empIdString,String passwordString) {

        RequestParams params = new RequestParams();
        params.put("username", empIdString);
        params.put("password", passwordString);

        Log.v("params", params.toString());

        if (CheckNetwork.isInternetAvailable(LoginActivity.this)) {
            AsyncHttpClient client = new AsyncHttpClient();
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
                sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                client.setSSLSocketFactory(sf);
            } catch (Exception e) {
            }
            client.post(Urls.loginUrl,params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    // called before request is started
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("SignIn...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    // called when response HTTP status is "200 OK"
                    try {
                        JSONObject jsonObject = new JSONObject(new String(response));
                        JSONObject listObject = jsonObject.getJSONObject("list");
                        JSONArray jsonArray = listObject.getJSONArray("userdata");

                        if (jsonArray.length()>0){
                        JSONObject userObject = jsonArray.getJSONObject(0);

                        Log.v("status", userObject.toString());
                        if (listObject.getString("status").equalsIgnoreCase("1")) {

                            //  sendIntent(statusObj.getJSONObject("userData"));

                            PrefManager.setUserId(LoginActivity.this, "emp_id", userObject.getString("emp_id"));
                            PrefManager.setUserName(LoginActivity.this, "emp_name", userObject.getString("emp_name"));
                            PrefManager.setEmpCode(LoginActivity.this, "username", userObject.getString("username"));
                            PrefManager.setPassword(LoginActivity.this, "password", userObject.getString("password"));
                            PrefManager.setRoleId(LoginActivity.this, "role_id", userObject.getString("role_id"));
                            PrefManager.setUnitId(LoginActivity.this, "unit_id", userObject.getString("unit_id"));
                            PrefManager.setRolePercentage(LoginActivity.this, "role_percentage", userObject.getString("role_percentage"));

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            progressDialog.dismiss();
                        }
                            // progressDialog.dismiss();
                        } else {
                            // Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Invalid Username/Password", Snackbar.LENGTH_SHORT);
                            snackbar1.show();
                            //progressDialog.dismiss();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //progressDialog.dismiss();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    // progressDialog.dismiss();
                    // Toast.makeText(LoginActivity.this,"Try Again later",Toast.LENGTH_SHORT).show();
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Try Again later", Snackbar.LENGTH_SHORT);
                    snackbar1.show();
                    progressDialog.dismiss();
                }
            });

        } else{
            //Toast.makeText(LoginActivity.this, getString(R.string.noInternetText), Toast.LENGTH_SHORT).show();
            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, R.string.noInternetText, Snackbar.LENGTH_SHORT);
            snackbar1.show();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(LoginActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(view);
        }
    }
}