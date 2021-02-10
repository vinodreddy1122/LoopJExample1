package lab1.adsalesapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import lab1.adsalesapp.Utils.PrefManager;
import lab1.adsalesapp.Utils.Urls;

import static com.codetroopers.betterpickers.timezonepicker.TimeZoneInfo.time;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    TextView marketVisit,viewClientList,clientListName,addClientList,booking,collection,assignment,assignmentAdd,assignmentView,assignmentUpdate,viewBookingList,viewCollectionList,bookingUpdate,collectionUpdate,totalBookingText,todayBookingText,totalCollectionText,todayCollectionText,totalAssignmentText,totalTargetCountText,totalTargetAchievedText,totalMarketVisitCount,todayMarketVisitText,targetsText,marketVisitUpdate,viewMarketVisit;
    ImageView logout;
    ProgressDialog progressDialog;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    String address,city,date;
    String msg;
    Intent intent;
    CardView booingsCountsCard,collectionCountsCard,assignmentCountsCardView,totalBookingCountsCardView,marketVisitCardView;
    String lat,lang;
    LinearLayout bookingViewMoreText;
    String bookingUpdateStatus,assignmentUpdateStatus,collectionUpdateStatus,marketVisitUpdateString;
    public static DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        marketVisit = findViewById(R.id.marketVisit);
        viewClientList = findViewById(R.id.viewClientList);
        clientListName = findViewById(R.id.clientListName);
        addClientList = findViewById(R.id.addClientList);
        booking = findViewById(R.id.booking);
        collection = findViewById(R.id.collection);
        assignment = findViewById(R.id.assignment);
        assignmentAdd = findViewById(R.id.assignmentAdd);
        assignmentView = findViewById(R.id.assignmentView);

        //assignmentUpdate = findViewById(R.id.assignmentUpdate);
        viewBookingList = findViewById(R.id.viewBookingList);
        viewCollectionList = findViewById(R.id.viewCollectionList);
       // bookingUpdate = findViewById(R.id.bookingUpdate);
        //collectionUpdate = findViewById(R.id.collectionUpdate);
        logout = findViewById(R.id.logout);

        booingsCountsCard = findViewById(R.id.booingsCountsCard);
        collectionCountsCard = findViewById(R.id.collectionCountsCard);
        assignmentCountsCardView = findViewById(R.id.assignmentCountsCardView);
        totalBookingCountsCardView = findViewById(R.id.totalBookingCountsCardView);
        marketVisitCardView = findViewById(R.id.marketVisitCardView);

        targetsText = findViewById(R.id.targetsText);

        totalBookingText = findViewById(R.id.totalBookingText);
        todayBookingText = findViewById(R.id.todayBookingText);
        totalCollectionText = findViewById(R.id.totalCollectionText);
        todayCollectionText = findViewById(R.id.todayCollectionText);
        todayCollectionText = findViewById(R.id.todayCollectionText);
        totalAssignmentText = findViewById(R.id.totalAssignmentText);
        totalTargetCountText = findViewById(R.id.totalTargetCountText);
        totalTargetAchievedText = findViewById(R.id.totalTargetAchievedText);
        totalMarketVisitCount = findViewById(R.id.totalMarketVisitCount);
        todayMarketVisitText = findViewById(R.id.todayMarketVisitText);
        bookingViewMoreText = findViewById(R.id.bookingViewMoreText);

       // marketVisitUpdate = findViewById(R.id.marketVisitUpdate);
        viewMarketVisit = findViewById(R.id.viewMarketVisit);




       /* if(Build.VERSION.SDK_INT >=23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS}, 1);
        }*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);




        intent = new Intent(HomeActivity.this, LocationNotifyService.class);
        startService(intent);


        viewMarketVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MarketVisitListActivity.class);
                intent.putExtra("marketVisitStringValue",marketVisitUpdateString);
                startActivity(intent);
            }
        });



        assignmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,AddAssignmentActivity.class);
                startActivity(intent);
            }
        });

       /* assignmentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,AssignmentActivity.class);
                startActivity(intent);
            }
        });*/

        assignmentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ViewAssignmentActivity.class);
                intent.putExtra("assignmentUpdateStatus",assignmentUpdateStatus);
                startActivity(intent);

            }
        });

        marketVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,MarketVisitActivity.class);
                startActivity(intent);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,BookingFormActivity.class);
                startActivity(intent);
            }
        });

        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,CollectionActivity.class);
                startActivity(intent);
            }
        });

        addClientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,AddClientListActivity.class);
                startActivity(intent);
            }
        });

        viewClientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ClientListActivity.class);
                startActivity(intent);
            }
        });

        viewBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ViewBookingListActivity.class);
                intent.putExtra("bookingUpdateSttaus",bookingUpdateStatus);
                startActivity(intent);
            }
        });
        viewCollectionList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ViewCollectionListActivity.class);
                intent.putExtra("collectionUpdate",collectionUpdateStatus);
                startActivity(intent);
            }
        });

      /*  bookingUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,BookingUpdateActivity.class);
                startActivity(intent);
            }
        });*/

       /* collectionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CollectionUpdateActivity.class);
                startActivity(intent);
            }
        });*/


        booingsCountsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ViewBookingListActivity.class);
                intent.putExtra("bookingUpdateSttaus",bookingUpdateStatus);
                startActivity(intent);

            }
        });

        collectionCountsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ViewCollectionListActivity.class);
                intent.putExtra("collectionUpdate",collectionUpdateStatus);
                startActivity(intent);



            }
        });

        assignmentCountsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ViewAssignmentActivity.class);
                intent.putExtra("assignmentUpdateStatus",assignmentUpdateStatus);
                startActivity(intent);

            }
        });

        marketVisitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,MarketVisitListActivity.class);
                intent.putExtra("marketVisitStringValue",marketVisitUpdateString);
                startActivity(intent);

            }
        });

        totalBookingCountsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TargetsWebViewActivity.class);
                intent.putExtra("webviewType","TargetsWebview");
                startActivity(intent);
            }
        });

        targetsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,TargetsWebViewActivity.class);
                intent.putExtra("webviewType","TargetsWebview");
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Are you sure want to logout?");
                builder.setIcon(R.drawable.logout_icon_png);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        PrefManager.setUserId(HomeActivity.this,"emp_id","");

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);

      /*  View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.userName);
        userName.setText(PrefManager.getUserName(HomeActivity.this, "emp_name"));*/



        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(5);  /* min dist for location change, here it is 10 meter */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
        dashBoard();
        getDataboardCountsData();

     //   Toast.makeText(HomeActivity.this,lat,Toast.LENGTH_LONG).show();




    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


      /*  DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            intent = new Intent(HomeActivity.this,
                    LocationNotifyService.class);
            stopService(intent);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(HomeActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);*/

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.changePassword) {
         /*   Intent intent = new Intent(HomeActivity.this,AdminWebviewActivity.class);
            startActivity(intent);*/
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Are you sure want to logout?");
            builder.setIcon(R.drawable.logout_icon_png);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    PrefManager.setUserId(HomeActivity.this,"emp_id","");

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void IsFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        finish();
                        intent = new Intent(HomeActivity.this,
                                LocationNotifyService.class);
                        stopService(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void dashBoard(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.permissionsUrl+PrefManager.getEmpCode(HomeActivity.this, "username"),new AsyncHttpResponseHandler() {


            public void onStart() {

                progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String s = new String(responseBody);

                    try {

                        JSONArray jsonArray = new JSONArray(s);
                        if (jsonArray.length()>0) {
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                if (jsonObject.getString("add_per").equals("1") && jsonObject.getString("view_per").equals("1") && jsonObject.getString("update_per").equals("1")) {
                                    marketVisit.setVisibility(View.VISIBLE);
                                   // marketVisit.setText(jsonObject.getString("moduelName"));
                                    viewMarketVisit.setVisibility(View.VISIBLE);
                                    //marketVisitUpdate.setVisibility(View.VISIBLE);
                                    marketVisitUpdateString = jsonObject.getString("update_per");
                                    marketVisitCardView.setVisibility(View.VISIBLE);
                                }
                               /*
                                if (jsonObject.getString("module_id").equals("1")) {
                                    marketVisit.setVisibility(View.VISIBLE);
                                    marketVisit.setText(jsonObject.getString("moduelName"));
                                    marketVisitCardView.setVisibility(View.VISIBLE);
                                }*/
                                if (jsonObject.getString("add_per").equals("1") && jsonObject.getString("view_per").equals("1") && jsonObject.getString("update_per").equals("1")) {
                                    booking.setVisibility(View.VISIBLE);
                                  //  bookingUpdate.setVisibility(View.VISIBLE);
                                    bookingUpdateStatus = jsonObject.getString("update_per");
                                    viewBookingList.setVisibility(View.VISIBLE);
                                    booingsCountsCard.setVisibility(View.VISIBLE);
                                }
                                if (jsonObject.getString("add_per").equals("1") && jsonObject.getString("view_per").equals("1") && jsonObject.getString("update_per").equals("1")) {
                                    collection.setVisibility(View.VISIBLE);
                                    //collectionUpdate.setVisibility(View.VISIBLE);
                                    collectionUpdateStatus = jsonObject.getString("update_per");
                                    viewCollectionList.setVisibility(View.VISIBLE);
                                    collectionCountsCard.setVisibility(View.VISIBLE);
                                }


                                if (jsonObject.getString("add_per").equals("1") && jsonObject.getString("view_per").equals("1") && jsonObject.getString("update_per").equals("1")) {
                                    assignmentAdd.setVisibility(View.VISIBLE);
                                  //  assignmentUpdate.setVisibility(View.VISIBLE);
                                    assignmentUpdateStatus = jsonObject.getString("update_per");
                                    assignmentView.setVisibility(View.VISIBLE);
                                    //collectionUpdate.setVisibility(View.VISIBLE);
                                   // viewCollectionList.setVisibility(View.VISIBLE);
                                   // collectionCountsCard.setVisibility(View.VISIBLE);
                                }
                                if (jsonObject.getString("add_per").equals("1") && jsonObject.getString("view_per").equals("1") && jsonObject.getString("update_per").equals("1")) {
                                    addClientList.setVisibility(View.VISIBLE);
                                    viewClientList.setVisibility(View.VISIBLE);

                                }
                                if (jsonObject.getString("module_id").equals("5")) {
                                    assignment.setVisibility(View.VISIBLE);
                                    assignment.setText(jsonObject.getString("moduelName"));
                                    assignmentCountsCardView.setVisibility(View.VISIBLE);
                                }
                                if (jsonObject.getString("module_id").equals("6")) {
                                    clientListName.setVisibility(View.VISIBLE);
                                    clientListName.setText(jsonObject.getString("moduelName"));
                                }
                                if (jsonObject.getString("module_id").equals("7")) {
                                    targetsText.setVisibility(View.VISIBLE);
                                    targetsText.setText(jsonObject.getString("moduelName"));
                                    totalBookingCountsCardView.setVisibility(View.VISIBLE);
                                }


                                progressDialog.dismiss();

                            }
                        }else{
                            Toast.makeText(HomeActivity.this, " No permissions", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }

            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                progressDialog.dismiss();
            }
        });

    }


    private void getDataboardCountsData(){

        RequestParams params = new RequestParams();
        params.put("emp_code", PrefManager.getUserName(HomeActivity.this, "username"));
        params.put("emp_role", PrefManager.getRoleId(HomeActivity.this, "role_id"));

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Urls.getDashboardDataUrl,params,new AsyncHttpResponseHandler() {


            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String s = new String(responseBody);

                try {

                    JSONArray jsonArray = new JSONArray(s);
                     JSONObject jsonObject = jsonArray.getJSONObject(0);

                        totalBookingText.setText(jsonObject.getString("totalBooking"));
                        todayBookingText.setText(jsonObject.getString("todayBookings"));

                        if (!jsonObject.getString("todayCollection").equals("null"))
                          todayCollectionText.setText(jsonObject.getString("todayCollection"));
                        else todayCollectionText.setText("0");

                    if (!jsonObject.getString("thisMonthCollection").equals("null"))
                        totalCollectionText.setText(jsonObject.getString("thisMonthCollection"));
                    else totalCollectionText.setText("0");

                      //  totalCollectionText.setText(jsonObject.getString("thisMonthCollection"));


                       totalAssignmentText.setText(jsonObject.getString("todayAssignments"));

                    if (!jsonObject.getString("thisMonthTarget").equals("null"))
                       totalTargetCountText.setText(jsonObject.getString("thisMonthTarget"));
                    else totalTargetCountText.setText("0");

                    if (!jsonObject.getString("thisMonthAchieved").equals("null"))
                        totalTargetAchievedText.setText(jsonObject.getString("thisMonthAchieved"));
                    else totalTargetAchievedText.setText("0");

                    if (!jsonObject.getString("thisMonthVisit").equals("null"))
                        totalMarketVisitCount.setText(jsonObject.getString("thisMonthVisit"));
                    else totalMarketVisitCount.setText("0");

                    if (!jsonObject.getString("todayVisit").equals("null"))
                        todayMarketVisitText.setText(jsonObject.getString("todayVisit"));
                    else todayMarketVisitText.setText("0");



                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {

            }
        });

    }


    private void empTrakingData(String latString, String landString, String addressString, String dateString){

        RequestParams params = new RequestParams();
        params.put("emp_id", PrefManager.getUserName(HomeActivity.this, "username"));
        params.put("lat",latString);
        params.put("long",landString);
        params.put("address",addressString);
        params.put("datetime",dateString);

        Log.v("params",params.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Urls.empTrackingDataUrl,params,new AsyncHttpResponseHandler() {


            public void onStart() {

                progressDialog = new ProgressDialog(HomeActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                try {

                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {


                      //  Toast.makeText(HomeActivity.this, " Location Updated", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurrentLocation != null) {
            // Print current location if not null
            //Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            lat = Double.toString(mCurrentLocation.getLatitude());
            lang = Double.toString(mCurrentLocation.getLongitude());

            msg = "Current Location: " +
                    Double.toString(mCurrentLocation.getLatitude()) + "," +
                    Double.toString(mCurrentLocation.getLongitude());
            // Toast.makeText(this, lat+lang, Toast.LENGTH_SHORT).show();

            //    latLog.setText("Current Location: " +lat+", "+lang);




            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude(), 1);// Here 1 represent max location result to returned, by documents it recommended 1 to 5

                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                // addressText.setText("Current Location: " +address+", "+city);
             //   Toast.makeText(this, "Current Location: " +address+", "+city, Toast.LENGTH_SHORT).show();

               // Toast.makeText(HomeActivity.this, lat+lang, Toast.LENGTH_SHORT).show();

                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(time * 1000);
                date = DateFormat.format("yyyy-MM-dd", cal).toString();

               /* Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();*/



                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final String currentDateTime = dateFormat.format(new Date());

               /* empTrakingData(lat,lang,address,currentDateTime);

                final Handler handler = new Handler();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        empTrakingData(lat,lang,address,currentDateTime);
                        handler.postDelayed(this, 600000);
                    }
                }, 600000);*/


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        intent = new Intent(HomeActivity.this, LocationNotifyService.class);
        startService(intent);
        super.onResume();
    }
}
