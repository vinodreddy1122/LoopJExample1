
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import cz.msebera.android.httpclient.Header;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aonetips.Utils.PrefManager;
import com.app.aonetips.Utils.SharedPrefManager;
import com.app.aonetips.Utils.Urls;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView signUp,forgotPassword;
    CardView crd_login;
    TextInputLayout emailInput,passwordInput;
    TextInputEditText email,password;
    ProgressDialog progressDialog;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.signUp);
        crd_login = findViewById(R.id.crd_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);

            }
        });


        /*FirebaseApp.initializeApp(LoginActivity.this);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token, token);

                        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(msg);

                    }
                });*/


        crd_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().isEmpty()){
                    emailInput.setError("Please Enter Email or Mobile Number");
                    return;
                }

                if (password.getText().toString().isEmpty()){
                    passwordInput.setError("Please Enter Password");
                    return;
                }

                loginData();

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    private void loginData(){

        RequestParams params = new RequestParams();
        params.put("action","userlogin");
        params.put("user_email", email.getText().toString());
        params.put("user_pass",password.getText().toString());
        params.put("fcm_id", SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken());

        // Log.v("params",params.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.userLoginUrl,params, new AsyncHttpResponseHandler() {

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
                    JSONObject statusObj = new JSONObject(new String(response));
                    Log.v("status",statusObj.toString());
                    if (statusObj.getString("status").equalsIgnoreCase("Login Success")) {

                        sendIntent(statusObj.getJSONObject("userData"));
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, statusObj.getString("status"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Try Again later",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendIntent(JSONObject jsonObject) throws JSONException {

        PrefManager.setEmail(LoginActivity.this,"user_email",jsonObject.getString("user_email"));
        PrefManager.setUserId(LoginActivity.this,"user_id",jsonObject.getString("user_id"));
        PrefManager.setMobile(LoginActivity.this,"user_mobileno",jsonObject.getString("user_mobileno"));
        PrefManager.setUserName(LoginActivity.this,"user_name",jsonObject.getString("user_name"));
        //Toast.makeText(LoginActivity.this,jsonObject.getString("user_id"),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
