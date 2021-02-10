package lab1.adsalesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.ParseException;
import lab1.adsalesapp.Adapters.CientListAdapter;
import lab1.adsalesapp.Adapters.EditionGroupByListAdapter;
import lab1.adsalesapp.Adapters.EditionGroupListAdapter;
import lab1.adsalesapp.Adapters.NothingSelectedSpinnerAdapter;
import lab1.adsalesapp.Helpers.ClientList;
import lab1.adsalesapp.Helpers.EditionGroupByList;
import lab1.adsalesapp.Helpers.EditionGroupList;
import lab1.adsalesapp.Utils.MsgUtils;
import lab1.adsalesapp.Utils.PrefManager;
import lab1.adsalesapp.Utils.Urls;

public class BookingFormActivity extends AppCompatActivity implements View.OnFocusChangeListener,CientListAdapter.AdapterCallback,EditionGroupListAdapter.AdapterCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Spinner paymentModeSpinner,divisonListSpinner,salesOfficeSpinner,BookingOfficeSpinner,ItemCategorySpinner,AdvertiserCodeSpinner,BusinessContentComponentSpinner,PagePostionSpinner,segmentsListSpinner,FieldCollectorSpinner,SalesDocTypeSpinner,SalesOrgSpinner,DistributionChannelSpinner,RangeSpinner,ItemTypeSpinner,getTermSpinner,unitMeasureSpinner,FieldcollectorRoleSpinner;
    TextView clientSpinner,editionGrouping;
    String cat_id,ClientId,discountId,discountIDValue,editionId,editionIdValue,ColorId,editionGroupId;
    TextInputLayout text_input_new_name,text_input_roNumber,text_input_adCaption,text_input_discount,text_input_size,text_input_description,text_input_Category_Discount,text_input_Other_Discount,text_input_price,text_input_height,text_input_address,text_input_town,text_input_district,text_input_pincode,text_input_mobileNumber,text_input_email,text_input_pricecollect_amont,text_input_old_name,text_input_address_old,text_input_town_old,text_input_district_old,text_input_pincode_old,text_input_mobileNumber_old,text_input_aadharNumber_new,text_input_panNumber_new,text_input_email_old,text_input_actual_price;
    TextInputEditText newNameEdt,sizeEdt,descriptionEdt,CategoryDiscountEdt,OtherDiscountEdt,priceEdt,editionDateEdt,roDateEdt,addressEdt,townEdt,districtEdt,pincodeEdt,mobileNumberEdt,positionEdt,roNumberEdt,adCaptionEdt,discountEdt,instrumentNoEdt,chequeDateEdt,sizeHeightEdt,emailEdt,collectAmountEdt,oldNameEdt,addressOldEdt,townEdtOld,districtEdtOld,pincodeEdtOld,mobileNumberEdtOld,emailEdtOld,actualPriceEdt,aadharNumberEdt,panNumberEdt,aadharNumberNewEdt,panNumberNewEdt,moreDateEdt,spaceEdt,ratePerSqcmEdt;
    String sendDate,paymentString,paymentStringValue,editionsId,sendChequeDate,divisionString,divisionValueString,salesOfficeString,salesOfficeStringValue,bookingOfficeString,bookingOfficeStringValue,itemCategoryString,itemCategoryStringValue,advertiserCodeString,advertiserCodeStringValue,businessComString,businessComStringValue,pagePositionString,pagePositionStringValue,segmentsString,segmentsStringValue,fieldCollectorString,fieldCollectorStringValue,FieldCollectorRoleStringValue,salesDocTypeString,salesDocTypeStringValue,salesOrgString,salesOrgStringValue,DistributionChannelString,DistributionChannelStringValue,rangeString,rangeStringValue,itmTypeString,itemTypeStringValue,termString,termStringValue,unitMeasureString,unitMeasureStringValue,FieldCollectorString,FieldCollectorStringValue,schemaId,schemaIdValue,categoryId,categoryIdValue;
    SimpleDateFormat formatter;
    boolean firstTimeSelected = true;
    Button btnSubmit,submitOtp;
    int check = 0;
    Toolbar toolbar;
    ImageView imageUpload,imagePreview;
    ProgressDialog progressDialog;
    CardView newClientLayout,oldClientLayout,mandetoryCardview;
    TextView newClientText,editionsText,showClientDetails,editionsSpinner,updateClientFields;
    ImageView closeNewClient;
    Spinner colorsSpinner,SchemaSpinner,categoryListSpinner;
    private Button btnClear, btnSave;
    private File file;
    private LinearLayout canvasLL;
    private View view;
    private signature mSignature;
    private Bitmap bitmap;
    ImageView sourceImage,signatureUpload;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICTURE_TAKEN_FROM_GALLERY = 999;
    String img=null;
    String signatureImageStg=null;
    Uri selectedImage;
    AlertDialog alert;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/Signature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";
    String pids="";
    String colorTypeString;
    Boolean firstSelected = false;
    LinearLayout clientSpinnerLayout,editionGroupingLayout,editionsSpinnerLayout;
    EditText editOtp;
    String otpSuccess="",confirm_otp="";

    String lat, lang,address;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    String msg;
    String otpStringValue = "";
    Dialog countryListDialog,editionGroupDialog,editionsByIdGroupDialog;
    List<ClientList> clientLists;
    List<EditionGroupList> editionGroupLists;
    List<EditionGroupByList> editionGroupByLists;
    String clientNameId;
    private RecyclerView.Adapter recyclerviewViewadapter,recyclerviewViewadapter2,recyclerviewViewadapter3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_form);


        if(Build.VERSION.SDK_INT >=23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(BookingFormActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }

        checkLocationPermission();
        
        clientSpinnerLayout = findViewById(R.id.clientSpinnerLayout);
        editionGroupingLayout = findViewById(R.id.editionGroupingLayout);
        editionsSpinnerLayout = findViewById(R.id.editionsSpinnerLayout);


        clientSpinner = findViewById(R.id.clientSpinner);
        editionGrouping = findViewById(R.id.editionGrouping);

     //   mandetoryFieldsText = findViewById(R.id.mandetoryFieldsText);
       // closeMandetoryIcon = findViewById(R.id.closeMandetoryIcon);
        mandetoryCardview = findViewById(R.id.mandetoryCardview);
      //  discountSpinner = findViewById(R.id.discountSpinner);
        editionsSpinner = findViewById(R.id.editionsSpinner);
        paymentModeSpinner = findViewById(R.id.paymentModeSpinner);
        categoryListSpinner = findViewById(R.id.categoryListSpinner);
        SchemaSpinner = findViewById(R.id.SchemaSpinner);

        divisonListSpinner = findViewById(R.id.divisonListSpinner);
        salesOfficeSpinner = findViewById(R.id.salesOfficeSpinner);
        BookingOfficeSpinner = findViewById(R.id.BookingOfficeSpinner);
        ItemCategorySpinner = findViewById(R.id.ItemCategorySpinner);
        AdvertiserCodeSpinner = findViewById(R.id.AdvertiserCodeSpinner);
        BusinessContentComponentSpinner = findViewById(R.id.BusinessContentComponentSpinner);
        PagePostionSpinner = findViewById(R.id.PagePostionSpinner);
        colorsSpinner = findViewById(R.id.colorsSpinner);
        segmentsListSpinner = findViewById(R.id.segmentsListSpinner);
        FieldCollectorSpinner = findViewById(R.id.FieldCollectorSpinner);
        SalesDocTypeSpinner = findViewById(R.id.SalesDocTypeSpinner);
        SalesOrgSpinner = findViewById(R.id.SalesOrgSpinner);
        DistributionChannelSpinner = findViewById(R.id.DistributionChannelSpinner);
        RangeSpinner = findViewById(R.id.RangeSpinner);
        ItemTypeSpinner = findViewById(R.id.ItemTypeSpinner);
        getTermSpinner = findViewById(R.id.getTermSpinner);
        unitMeasureSpinner = findViewById(R.id.unitMeasureSpinner);
        FieldcollectorRoleSpinner = findViewById(R.id.FieldcollectorRoleSpinner);

        updateClientFields = findViewById(R.id.updateClientFields);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);



        text_input_new_name = findViewById(R.id.text_input_new_name);
        text_input_roNumber = findViewById(R.id.text_input_roNumber);
        text_input_adCaption = findViewById(R.id.text_input_adCaption);
        text_input_discount = findViewById(R.id.text_input_discount);
        //text_input_media = findViewById(R.id.text_input_media);
        text_input_size = findViewById(R.id.text_input_size);
        text_input_description = findViewById(R.id.text_input_description);
        text_input_Category_Discount = findViewById(R.id.text_input_Category_Discount);
        text_input_Other_Discount = findViewById(R.id.text_input_Other_Discount);
        text_input_price = findViewById(R.id.text_input_price);
        text_input_height = findViewById(R.id.text_input_height);
        text_input_address = findViewById(R.id.text_input_address);
        text_input_town = findViewById(R.id.text_input_town);
        text_input_district = findViewById(R.id.text_input_district);
        text_input_pincode = findViewById(R.id.text_input_pincode);
        text_input_mobileNumber = findViewById(R.id.text_input_mobileNumber);
        text_input_email = findViewById(R.id.text_input_email);
        text_input_pricecollect_amont = findViewById(R.id.text_input_pricecollect_amont);

        text_input_old_name = findViewById(R.id.text_input_old_name);
        text_input_address_old = findViewById(R.id.text_input_address_old);
        text_input_town_old = findViewById(R.id.text_input_town_old);
        text_input_district_old = findViewById(R.id.text_input_district_old);
        text_input_pincode_old = findViewById(R.id.text_input_pincode_old);
        text_input_mobileNumber_old = findViewById(R.id.text_input_mobileNumber_old);
        text_input_email_old = findViewById(R.id.text_input_email_old);
        text_input_actual_price = findViewById(R.id.text_input_actual_price);

        oldNameEdt = findViewById(R.id.oldNameEdt);
        addressOldEdt = findViewById(R.id.addressOldEdt);
        townEdtOld = findViewById(R.id.townEdtOld);
        districtEdtOld = findViewById(R.id.districtEdtOld);
        pincodeEdtOld = findViewById(R.id.pincodeEdtOld);
        mobileNumberEdtOld = findViewById(R.id.mobileNumberEdtOld);
        aadharNumberEdt = findViewById(R.id.aadharNumberEdt);
        panNumberEdt = findViewById(R.id.panNumberEdt);
        emailEdtOld = findViewById(R.id.emailEdtOld);
        actualPriceEdt = findViewById(R.id.actualPriceEdt);

        imageUpload = findViewById(R.id.imageUpload);
        imagePreview = findViewById(R.id.imagePreview);


        editOtp = findViewById(R.id.editOtp);
        submitOtp = findViewById(R.id.submitOtp);


        newNameEdt = findViewById(R.id.newNameEdt);
        addressEdt = findViewById(R.id.addressEdt);
        townEdt = findViewById(R.id.townEdt);
        districtEdt = findViewById(R.id.districtEdt);
        mobileNumberEdt = findViewById(R.id.mobileNumberEdt);
        aadharNumberNewEdt = findViewById(R.id.aadharNumberNewEdt);
        panNumberNewEdt = findViewById(R.id.panNumberNewEdt);
        pincodeEdt = findViewById(R.id.pincodeEdt);
        positionEdt = findViewById(R.id.positionEdt);
        roNumberEdt = findViewById(R.id.roNumberEdt);
        adCaptionEdt = findViewById(R.id.adCaptionEdt);
        discountEdt = findViewById(R.id.discountEdt);
        //colorEdt = findViewById(R.id.colorEdt);
        instrumentNoEdt = findViewById(R.id.instrumentNoEdt);
        chequeDateEdt = findViewById(R.id.chequeDateEdt);
        sizeHeightEdt = findViewById(R.id.sizeHeightEdt);
        emailEdt = findViewById(R.id.emailEdt);
        collectAmountEdt = findViewById(R.id.collectAmountEdt);

        moreDateEdt = findViewById(R.id.moreDateEdt);
        spaceEdt = findViewById(R.id.spaceEdt);
        ratePerSqcmEdt = findViewById(R.id.ratePerSqcmEdt);


      //  mediaEdt = findViewById(R.id.mediaEdt);
        sizeEdt = findViewById(R.id.sizeEdt);
        descriptionEdt = findViewById(R.id.descriptionEdt);
        CategoryDiscountEdt = findViewById(R.id.CategoryDiscountEdt);
        OtherDiscountEdt = findViewById(R.id.OtherDiscountEdt);
        priceEdt = findViewById(R.id.priceEdt);

        editionDateEdt = findViewById(R.id.editionDateEdt);
        roDateEdt = findViewById(R.id.roDateEdt);
        signatureUpload = findViewById(R.id.signatureUpload);

        btnSubmit = findViewById(R.id.btnSubmit);

    //    mediaEdt.setOnFocusChangeListener(this);
        sizeEdt.setOnFocusChangeListener(this);
        descriptionEdt.setOnFocusChangeListener(this);
        CategoryDiscountEdt.setOnFocusChangeListener(this);
        OtherDiscountEdt.setOnFocusChangeListener(this);
        priceEdt.setOnFocusChangeListener(this);
        actualPriceEdt.setOnFocusChangeListener(this);
        editionDateEdt.setOnFocusChangeListener(this);
        roDateEdt.setOnFocusChangeListener(this);
        chequeDateEdt.setOnFocusChangeListener(this);
        sizeHeightEdt.setOnFocusChangeListener(this);
        collectAmountEdt.setOnFocusChangeListener(this);
        positionEdt.setOnFocusChangeListener(this);
        roNumberEdt.setOnFocusChangeListener(this);
        editOtp.setOnFocusChangeListener(this);
        instrumentNoEdt.setOnFocusChangeListener(this);
        adCaptionEdt.setOnFocusChangeListener(this);
        discountEdt.setOnFocusChangeListener(this);
        moreDateEdt.setOnFocusChangeListener(this);
        spaceEdt.setOnFocusChangeListener(this);
        ratePerSqcmEdt.setOnFocusChangeListener(this);

        editOtp.setText("0000");



        newClientLayout = findViewById(R.id.newClientLayout);
        newClientText = findViewById(R.id.newClientText);
        showClientDetails = findViewById(R.id.showClientDetails);
        closeNewClient = findViewById(R.id.closeNewClient);
        editionsText = findViewById(R.id.editionsText);


        oldClientLayout = findViewById(R.id.oldClientLayout);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        editionDateEdt.setText(formattedDate);
        editionDateEdt.requestFocus();

        chequeDateEdt.setText(formattedDate);
        chequeDateEdt.requestFocus();

        roDateEdt.setText(formattedDate);
        chequeDateEdt.requestFocus();

        sourceImage = (ImageView)findViewById(R.id.sourceImage);


      /*  mandetoryFieldsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMandetoryIcon.setVisibility(View.VISIBLE);
                mandetoryCardview.setVisibility(View.VISIBLE);
            }
        });

        closeMandetoryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandetoryCardview.setVisibility(View.GONE);
                closeMandetoryIcon.setVisibility(View.GONE);
            }
        });
*/

        signatureUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(BookingFormActivity.this);
                LayoutInflater inflater = BookingFormActivity.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.signature_popup, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                canvasLL = (LinearLayout) dialogView.findViewById(R.id.canvasLL);
                mSignature = new signature(getApplicationContext(), null);
                mSignature.setBackgroundColor(Color.WHITE);
                // Dynamically generating Layout through java code
                canvasLL.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                btnClear = (Button) dialogView.findViewById(R.id.btnclear);
                btnSave = (Button)dialogView. findViewById(R.id.btnsave);


                ImageView closeDialog = (ImageView)dialogView.findViewById(R.id.closeDialog);




                view = canvasLL;

                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSignature.clear();
                    }
                });

                final View finalView = view;
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finalView.setDrawingCacheEnabled(true);
                        mSignature.save(finalView,StoredPath);
                       // Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                        Bitmap bitmap = BitmapFactory.decodeFile(StoredPath);
                      //  signatureImageStg = bitmap;
                        signatureImageStg = getStringImageNew(bitmap);
                        sourceImage.setImageBitmap(bitmap);
                        alert.dismiss();
                    }
                });
                alert = dialog.create();
                alert.show();
                //Button button=(Button)dialogView.findViewById(R.id.closebutton);


                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();
                    }
                });

                /*button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();
                    }
                });
                alert = dialog.create();
                alert.show();*/
            }
        });


        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(5);  /* min dist for location change, here it is 10 meter */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();


        submitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // oldClientLayout
              //  newClientLayout
                // mobileNumberEdtOld

                if (oldClientLayout.getVisibility() == View.VISIBLE) {
                    if (mobileNumberEdtOld.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Mobile No should not be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }else otpStringValue = mobileNumberEdtOld.getText().toString();
                }

                if (newClientLayout.getVisibility() == View.VISIBLE) {
                    if (mobileNumberEdt.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Mobile No should not be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }else otpStringValue = mobileNumberEdt.getText().toString();
                }

                submitOtpData(otpStringValue);

            }
        });


        /*editOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
               *//* if(cs.toString().equals(otpSuccess)){
                    //submitOtpData(cs.toString());
                }else{
                    Toast.makeText(BookingFormActivity.this,"Please Enter valid Otp",Toast.LENGTH_LONG).show();
                }*//*

               if (!cs.toString().equals(otpSuccess)){
                   //Toast.makeText(BookingFormActivity.this,"Otp not maching please check or continue",Toast.LENGTH_LONG).show();
                  // submitOtpData2(cs.toString());
               }
                //adapter.getFilter().filter();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
            }
        });
*/

        editionDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
                cdp.show(BookingFormActivity.this.getSupportFragmentManager(), "Material Calendar Example");
                cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            Date date = formatter.parse(dateInString);
                            formatter = new SimpleDateFormat("yyyy-MM-dd");

                            editionDateEdt.setText(formatter.format(date));
                            //    Toast.makeText(PagesListActivity.this,formatter.format(date),Toast.LENGTH_LONG).show();

                            sendDate = formatter.format(date);

                            firstTimeSelected = false;
                            check = 0;

                        } catch (ParseException | java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        roDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
                cdp.show(BookingFormActivity.this.getSupportFragmentManager(), "Material Calendar Example");
                cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            Date date = formatter.parse(dateInString);
                            formatter = new SimpleDateFormat("yyyy-MM-dd");

                            roDateEdt.setText(formatter.format(date));
                            //    Toast.makeText(PagesListActivity.this,formatter.format(date),Toast.LENGTH_LONG).show();

                            sendDate = formatter.format(date);

                            firstTimeSelected = false;
                            check = 0;

                        } catch (ParseException | java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        chequeDateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
                cdp.show(BookingFormActivity.this.getSupportFragmentManager(), "Material Calendar Example");
                cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            Date date = formatter.parse(dateInString);
                            formatter = new SimpleDateFormat("yyyy-MM-dd");

                            chequeDateEdt.setText(formatter.format(date));
                            //    Toast.makeText(PagesListActivity.this,formatter.format(date),Toast.LENGTH_LONG).show();

                            sendChequeDate = formatter.format(date);

                            firstTimeSelected = false;
                            check = 0;

                        } catch (ParseException | java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


        clientSpinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientListPopUp();
            }
        });

        editionGroupingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editionGroupingPopUp();
            }
        });

        editionsSpinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editionsByIdPopUp();
            }
        });


        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

                photoOptions();
            }
        });

        newClientText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newClientLayout.setVisibility(View.VISIBLE);
                closeNewClient.setVisibility(View.VISIBLE);
                oldClientLayout.setVisibility(View.GONE);
                updateClientFields.setVisibility(View.GONE);
                clientSpinner.setText("Select Client Name");
                showClientDetails.setVisibility(View.GONE);

            }
        });

        updateClientFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //updateClientDetails();
                if (oldNameEdt.getText().toString().isEmpty()){
                    text_input_old_name.setError("Please Enter Name");
                        return;
                }

                if (addressOldEdt.getText().toString().isEmpty()){
                    text_input_address_old.setError("Please Enter Address");
                    return;
                }
                if (townEdtOld.getText().toString().isEmpty()){
                    text_input_town_old.setError("Please Enter Town");
                    return;
                }

                if (districtEdtOld.getText().toString().isEmpty()){
                    text_input_district_old.setError("Please Enter District");
                    return;
                }
                if (pincodeEdtOld.getText().toString().isEmpty()){
                    text_input_pincode_old.setError("Please Enter Pincode");
                    return;
                }
                if (mobileNumberEdtOld.getText().toString().isEmpty()){
                    text_input_mobileNumber_old.setError("Please Enter Mobile Number");
                    return;
                }
                if (emailEdtOld.getText().toString().isEmpty()){
                    text_input_email_old.setError("Please Enter Email");
                    return;
                }

                updateClientDetails(newNameEdt.getText().toString(), addressEdt.getText().toString(),townEdt.getText().toString(),districtEdt.getText().toString(),
                        pincodeEdt.getText().toString(),mobileNumberEdt.getText().toString(),emailEdt.getText().toString());
            }
        });

        closeNewClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldClientLayout.setVisibility(View.GONE);
                closeNewClient.setVisibility(View.GONE);
                showClientDetails.setVisibility(View.GONE);
                newClientLayout.setVisibility(View.GONE);
               /* newNameEdt.setText("");
                addressEdt.setText("");
                townEdt.setText("");
                districtEdt.setText("");
                pincodeEdt.setText("");
                mobileNumberEdt.setText("");
                emailEdt.setText("");*/

            }
        });

        showClientDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldClientLayout.setVisibility(View.VISIBLE);
                newClientLayout.setVisibility(View.GONE);
                closeNewClient.setVisibility(View.VISIBLE);
                updateClientFields.setVisibility(View.VISIBLE);

            }
        });

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("BLACK & WHITE");
        arrayList.add("COLOR");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorsSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected6, getApplicationContext()));
        //  colorsSpinner.setAdapter(arrayAdapter);
        /*colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorTypeString = parent.getItemAtPosition(position).toString();
              //  Toast.makeText(parent.getContext(), "Selected: " + colorTypeString, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });*/
        colorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    String s = arrayList.get(position);
                    ColorId = s;

                } else {

                    String s = arrayList.get(position - 1);
                    ColorId = s;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (roNumberEdt.getText().toString().isEmpty()){
                   // text_input_roNumber.setError("Please Enter RO Number");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter RO Number", MsgUtils.ToastLength.SHORT);
                    return;
                }
                /*if (mediaEdt.getText().toString().isEmpty()){
                    text_input_media.setError("Please Enter Media");
                    return;
                }*/
                if (sizeEdt.getText().toString().isEmpty()){
                    //text_input_size.setError("Please Enter Width");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Width", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (sizeHeightEdt.getText().toString().isEmpty()){
                 //   text_input_height.setError("Please Enter Height");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Height", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (descriptionEdt.getText().toString().isEmpty()){
                   // text_input_description.setError("Please Enter Description");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Description", MsgUtils.ToastLength.SHORT);
                    return;
                }

                if (priceEdt.getText().toString().isEmpty()){
                  //  text_input_price.setError("Please Enter Total Price");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Total Price", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (actualPriceEdt.getText().toString().isEmpty()){
                    //text_input_price.setError("Please Enter Actual Price");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Actual Price", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (collectAmountEdt.getText().toString().isEmpty()){
                 //   text_input_pricecollect_amont.setError("Please Enter Collecting Price");
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter Collecting Price", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (editOtp.getText().toString().isEmpty()){
                   //Toast.makeText(BookingFormActivity.this,"Please Enter otp",Toast.LENGTH_LONG).show();
                    MsgUtils.showToast(BookingFormActivity.this, "Please Enter otp", MsgUtils.ToastLength.SHORT);
                    return;
                }
                if (!paymentStringValue.equals("1")) {
                    if (instrumentNoEdt.getText().toString().isEmpty()) {
                       // Toast.makeText(BookingFormActivity.this, "Please Enter Instrument Number", Toast.LENGTH_LONG).show();
                        MsgUtils.showToast(BookingFormActivity.this, "Please Enter Instrument Number", MsgUtils.ToastLength.SHORT);
                        return;
                    }
                    if (adCaptionEdt.getText().toString().isEmpty()){
                      //  text_input_adCaption.setError("Please Enter AD Caption");
                        MsgUtils.showToast(BookingFormActivity.this, "Please Enter AD Caption", MsgUtils.ToastLength.SHORT);
                        return;
                    }

                    if (discountEdt.getText().toString().isEmpty()){
                        //text_input_discount.setError("Please Enter Discount");
                        MsgUtils.showToast(BookingFormActivity.this, "Please Enter Discount", MsgUtils.ToastLength.SHORT);
                        return;
                    }
                    if (CategoryDiscountEdt.getText().toString().isEmpty()){
                        //text_input_Category_Discount.setError("Please Enter Category Discount");
                        MsgUtils.showToast(BookingFormActivity.this, "Please Enter Category Discount", MsgUtils.ToastLength.SHORT);
                        return;
                    }
                    if (OtherDiscountEdt.getText().toString().isEmpty()){
                     //   text_input_Other_Discount.setError("Please Enter Other Discount");
                        MsgUtils.showToast(BookingFormActivity.this, "Please Enter Other Discount", MsgUtils.ToastLength.SHORT);
                        return;
                    }
                }


                /*if (!editOtp.getText().toString().equals(otpSuccess)){
                   Toast.makeText(BookingFormActivity.this,"Please Enter valid Otp",Toast.LENGTH_LONG).show();
                    return;
                }*/

                /*if(!editOtp.getText().toString().isEmpty()){
                    confirm_otp= editOtp.getText().toString();
                }*/



                bookingSubmitData(newNameEdt.getText().toString(),adCaptionEdt.getText().toString(),discountEdt.getText().toString(),roNumberEdt.getText().toString(),sizeEdt.getText().toString(),sizeHeightEdt.getText().toString(),descriptionEdt.getText().toString(),CategoryDiscountEdt.getText().toString(),OtherDiscountEdt.getText().toString(),priceEdt.getText().toString(),actualPriceEdt.getText().toString(),editionDateEdt.getText().toString(),roDateEdt.getText().toString(),addressEdt.getText().toString()
                ,townEdt.getText().toString(),districtEdt.getText().toString(),pincodeEdt.getText().toString(),mobileNumberEdt.getText().toString(),aadharNumberNewEdt.getText().toString(),panNumberNewEdt.getText().toString(),positionEdt.getText().toString(),instrumentNoEdt.getText().toString(),emailEdt.getText().toString(),collectAmountEdt.getText().toString(),editOtp.getText().toString(),moreDateEdt.getText().toString(),spaceEdt.getText().toString(),ratePerSqcmEdt.getText().toString());

            }
        });




        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }


      //  clientListData();
        //discountListData();
        //editionsListData();
       // bookingList();
        categoryData();
        schemaData();
        paymentModeData();
        getDivisionListData();
        geSalesOfficeListData();
        geBookinOfficeListData();
        geItemCategoryListData();
        geAdvertiserCodeListData();
        geBusinessContentComponentListData();
        gePagePositionData();
        geSegmentsData();
        geFieldCollectorData();
        geSalesDocTypeData();
        getSalesOrgData();
        getDistributionChannelData();
        getRangeData();
        getItemTypeData();
        getTermData();
        getUnitMeasureData();
        getFieldcollectorRoleData();
    }


    private void updateClientDetails(String name,String address,String town,String district,String pincode,String number,String email){

        RequestParams params = new RequestParams();
        params.put("id", ClientId);
        params.put("name",name);
        params.put("address", address);
        params.put("town", town);
        params.put("district", district);
        params.put("pincode", pincode);
        params.put("phone", number);
        params.put("email", email);

        Log.v("paramsotp",params.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Urls.updateClientUrl,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started

                progressDialog = new ProgressDialog(BookingFormActivity.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.v("jsonstatus",new String(response));
                try {
                    JSONObject jsonObject = new JSONObject(new String(response));

                    Log.v("status", jsonObject.toString());
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                        Toast.makeText(getApplicationContext(),"Update successfully.",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(BookingFormActivity.this, "Try Again later", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(BookingFormActivity.this,"Try Again later",Toast.LENGTH_SHORT).show();

            }
        });


    }


    //Client list popup start------->

    private void clientListPopUp() {
        ImageView closeDialog;
        final ProgressBar progressBar;


        countryListDialog = new Dialog(BookingFormActivity.this, android.R.style.DeviceDefault_Light_ButtonBar);
        countryListDialog.getWindow().getAttributes().windowAnimations = R.style.detailDialogAnimation;
        countryListDialog.setContentView(R.layout.client_list_popup);

        EditText search = countryListDialog.findViewById(R.id.search);
        closeDialog = countryListDialog.findViewById(R.id.closeDialog);
        progressBar = countryListDialog.findViewById(R.id.progressBar);


        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countryListDialog.dismiss();
            }
        });

        final RecyclerView CountryRecyclerView = countryListDialog.findViewById(R.id.clientListRecyclerView);

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    final String query = charSequence.toString().toLowerCase().trim();
                    final ArrayList<ClientList> filteredList = new ArrayList<>();

                    for (int j = 0; j < clientLists.size(); j++) {

                        final String text = clientLists.get(j).getName().toLowerCase();
                        if (text.contains(query)) {
                            filteredList.add(clientLists.get(j));
                        }
                    }
                    recyclerviewViewadapter = new CientListAdapter(filteredList, BookingFormActivity.this, countryListDialog);
                    CountryRecyclerView.setAdapter(recyclerviewViewadapter);
                    recyclerviewViewadapter.notifyDataSetChanged();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });





        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingFormActivity.this, RecyclerView.VERTICAL, false);
        CountryRecyclerView.setLayoutManager(layoutManager);
        CountryRecyclerView.setHasFixedSize(true);
        CountryRecyclerView.setNestedScrollingEnabled(false);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(Urls.clientListUrl, new AsyncHttpResponseHandler() {

            public void onStart() {

                progressBar.setVisibility(View.VISIBLE);
                CountryRecyclerView.setVisibility(View.GONE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
// called when response HTTP status is "200 OK"
//status
                String s = new String(response);
                ArrayList<String> listCategory = new ArrayList<String>();

                clientLists = new ArrayList<ClientList>();

                try {

                    JSONArray jsonArray = new JSONArray(s);


                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jObj =jsonArray.getJSONObject(i);

                        //clientNameId = jObj.getString("CountryName");

                        ClientList countryModel = new ClientList();
                        countryModel.setId(jObj.getString("id"));
                        countryModel.setName(jObj.getString("name"));
                        countryModel.setAddress(jObj.getString("address"));
                        countryModel.setTown(jObj.getString("town"));
                        countryModel.setDistrict(jObj.getString("district"));
                        countryModel.setPincode(jObj.getString("pincode"));
                        countryModel.setPhone(jObj.getString("phone"));
                        countryModel.setEmail(jObj.getString("email"));
                        countryModel.setPan_number(jObj.getString("pan_number"));
                        countryModel.setAadhaar_number(jObj.getString("aadhaar_number"));

                        clientLists.add(countryModel);

                    }

                    recyclerviewViewadapter = new CientListAdapter(clientLists, BookingFormActivity.this,countryListDialog);
                    CountryRecyclerView.setAdapter(recyclerviewViewadapter);
                    progressBar.setVisibility(View.GONE);
                    CountryRecyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    CountryRecyclerView.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
// called when response HTTP status is "4XX" (eg. 401, 403, 404)

                progressBar.setVisibility(View.GONE);
                CountryRecyclerView.setVisibility(View.GONE);
            }

        });



        countryListDialog.show();
    }



    //Client list pop end------->



    //editionGroup popup list start------->

    private void editionGroupingPopUp() {
        ImageView closeDialog;
        final ProgressBar progressBar;


        editionGroupDialog = new Dialog(BookingFormActivity.this, android.R.style.DeviceDefault_Light_ButtonBar);
        editionGroupDialog.getWindow().getAttributes().windowAnimations = R.style.detailDialogAnimation;
        editionGroupDialog.setContentView(R.layout.edition_group_list_popup);

        EditText search = editionGroupDialog.findViewById(R.id.search);
        progressBar = editionGroupDialog.findViewById(R.id.progressBar);
        closeDialog = editionGroupDialog.findViewById(R.id.closeDialog);



        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editionGroupDialog.dismiss();
            }
        });



        final RecyclerView editionGroupListRecyclerView = editionGroupDialog.findViewById(R.id.editionGroupListRecyclerView);

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    final String query = charSequence.toString().toLowerCase().trim();
                    final ArrayList<EditionGroupList> filteredList = new ArrayList<>();

                    for (int j = 0; j < editionGroupLists.size(); j++) {

                        final String text = editionGroupLists.get(j).getShort_text().toLowerCase();
                        if (text.contains(query)) {
                            filteredList.add(editionGroupLists.get(j));
                        }
                    }
                    recyclerviewViewadapter2 = new EditionGroupListAdapter(filteredList, BookingFormActivity.this, editionGroupDialog);
                    editionGroupListRecyclerView.setAdapter(recyclerviewViewadapter2);
                    recyclerviewViewadapter2.notifyDataSetChanged();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });





        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingFormActivity.this, RecyclerView.VERTICAL, false);
        editionGroupListRecyclerView.setLayoutManager(layoutManager);
        editionGroupListRecyclerView.setHasFixedSize(true);
        editionGroupListRecyclerView.setNestedScrollingEnabled(false);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(Urls.editionGroupListUrl, new AsyncHttpResponseHandler() {

            public void onStart() {

                progressBar.setVisibility(View.VISIBLE);
                editionGroupListRecyclerView.setVisibility(View.GONE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
// called when response HTTP status is "200 OK"
//status
                String s = new String(response);
                ArrayList<String> listCategory = new ArrayList<String>();

                editionGroupLists = new ArrayList<EditionGroupList>();

                try {

                    JSONArray jsonArray = new JSONArray(s);


                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jObj =jsonArray.getJSONObject(i);

                        //clientNameId = jObj.getString("CountryName");

                        EditionGroupList editionGroupList = new EditionGroupList();
                        editionGroupList.setId(jObj.getString("id"));
                        editionGroupList.setGroup_name(jObj.getString("group_name"));
                        editionGroupList.setShort_text(jObj.getString("short_text"));
                        editionGroupList.setLong_text(jObj.getString("long_text"));


                        editionGroupLists.add(editionGroupList);

                    }

                    recyclerviewViewadapter2 = new EditionGroupListAdapter(editionGroupLists, BookingFormActivity.this,editionGroupDialog);
                    editionGroupListRecyclerView.setAdapter(recyclerviewViewadapter2);
                    progressBar.setVisibility(View.GONE);
                    editionGroupListRecyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    editionGroupListRecyclerView.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
// called when response HTTP status is "4XX" (eg. 401, 403, 404)

                progressBar.setVisibility(View.GONE);
                editionGroupListRecyclerView.setVisibility(View.GONE);
            }

        });



        editionGroupDialog.show();
    }


    //editionGroup popup list start------->

   /* private void clientListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.clientListUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                listCategory.add("Select Client");
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            cat_id = jsonObject1.getString("name");
                            listCategory.add(cat_id);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);



                          //  clientSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected1, getApplicationContext()));
                            clientSpinner.setAdapter(arrayAdapter);
                            //clientSpinner.setTitle("Select Client");
                            showClientDetails.setVisibility(View.GONE);
                            clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    *//*if (position == 0) {

                                        String s = listCat_id.get(position);
                                        ClientId = s;
                                        showClientDetails.setVisibility(View.VISIBLE);

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        ClientId = s;
                                        showClientDetails.setVisibility(View.VISIBLE);
                                    }*//*

                                    if (firstSelected) {
                                        // item = country_shortcode.get(position);
                                        if (position == 0) {
                                            ClientId = listCat_id.get(position);
                                            showClientDetails.setVisibility(View.VISIBLE);

                                            newNameEdt.setText("");
                                           // Toast.makeText(BookingFormActivity.this,ClientId,Toast.LENGTH_SHORT).show();
                                        } else {
                                            ClientId = listCat_id.get(position - 1);
                                            showClientDetails.setVisibility(View.VISIBLE);
                                          //  Toast.makeText(BookingFormActivity.this,ClientId,Toast.LENGTH_SHORT).show();
                                        }
                                        //Toast.makeText(SettingsActivity.this,item,Toast.LENGTH_SHORT).show();

                                    }else firstSelected = true;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }*/




    //discountList spinner list start------->

    /*private void discountListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getDiscountListUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            discountId = jsonObject1.getString("percentage");
                            listCategory.add(discountId);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            discountSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected2, getApplicationContext()));

                            discountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        discountIDValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        discountIDValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }*/

    //discountList spinner list ens------->


    /*private void editionsListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getEditionsUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            editionId = jsonObject1.getString("name");
                            listCategory.add(editionId);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            editionsSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected5, getApplicationContext()));

                            editionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        editionIdValue = s;
                                        editionsText.setText(editionIdValue);
                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        editionIdValue = s;
                                        editionsText.setText(title);
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }
*/


    /*private void bookingList(){

        RequestParams params = new RequestParams();
        //  params.put("dateofpage", sendDate);

        Log.v("params",params.toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getEditionsUrl,new AsyncHttpResponseHandler() {


            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);


                    EditionsSpinner collectionListHelper;
                    collectionListHelper = new EditionsSpinner();
                    collectionListHelper.setName("--Select Edition--");
                    List<EditionsSpinner> collectionListHelpers = new ArrayList<EditionsSpinner>();
                    collectionListHelpers.add(collectionListHelper);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        collectionListHelper = new EditionsSpinner();
                        collectionListHelper.setId(jsonObject1.getString("id"));
                        collectionListHelper.setName(jsonObject1.getString("name"));

                        collectionListHelper.setSelected(false);
                        collectionListHelpers.add(collectionListHelper);


                    }

                *//*  EditionSpinnerAdapter recyclerviewViewadapter2 = new EditionSpinnerAdapter(collectionListHelpers,BookingFormActivity.this);
                   editionsSpinner.setAdapter(recyclerviewViewadapter2);*//*

                    MyAdapter myAdapter = new MyAdapter(BookingFormActivity.this, 0,collectionListHelpers);
                    editionsSpinner.setAdapter(myAdapter);


                } catch (JSONException e) {

                    Toast.makeText(BookingFormActivity.this,"Loading failed...",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {

                Toast.makeText(BookingFormActivity.this,"Loading failed...",Toast.LENGTH_LONG).show();
            }
        });

    }
*/


    //editionsList popup spinner list starts------->

    private void editionsByIdPopUp() {
        ImageView closeDialog;
        final ProgressBar progressBar;
        RelativeLayout footer;


        editionsByIdGroupDialog = new Dialog(BookingFormActivity.this, android.R.style.DeviceDefault_Light_ButtonBar);
        editionsByIdGroupDialog.getWindow().getAttributes().windowAnimations = R.style.detailDialogAnimation;
        editionsByIdGroupDialog.setContentView(R.layout.edition_group_list__by_popup);

        EditText search = editionsByIdGroupDialog.findViewById(R.id.search);
        closeDialog = editionsByIdGroupDialog.findViewById(R.id.closeDialog);
        progressBar = editionsByIdGroupDialog.findViewById(R.id.progressBar);
        footer = editionsByIdGroupDialog.findViewById(R.id.footer);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if (editionGroupByLists != null && editionGroupByLists.size()>0)
                    editionsByIdGroupDialog.dismiss();
               // else Toast.makeText(BookingFormActivity.this,"Please Select Editions",Toast.LENGTH_LONG).show();
            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editionsByIdGroupDialog.dismiss();
            }
        });

        final RecyclerView editionGroupListByRecyclerView = editionsByIdGroupDialog.findViewById(R.id.editionGroupListByRecyclerView);
      //  if (editionGroupByLists != null && editionGroupByLists.size()>0) {
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    final String query = charSequence.toString().toLowerCase().trim();
                    final ArrayList<EditionGroupByList> filteredList = new ArrayList<>();

                    for (int j = 0; j < editionGroupByLists.size(); j++) {

                        final String text = editionGroupByLists.get(j).getShort_text().toLowerCase();
                        if (text.contains(query)) {
                            filteredList.add(editionGroupByLists.get(j));
                        }
                    }
                    recyclerviewViewadapter3 = new EditionGroupByListAdapter(filteredList, BookingFormActivity.this, editionsByIdGroupDialog);
                    editionGroupListByRecyclerView.setAdapter(recyclerviewViewadapter3);
                    recyclerviewViewadapter3.notifyDataSetChanged();

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });





        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingFormActivity.this, RecyclerView.VERTICAL, false);
        editionGroupListByRecyclerView.setLayoutManager(layoutManager);
        editionGroupListByRecyclerView.setHasFixedSize(true);
        editionGroupListByRecyclerView.setNestedScrollingEnabled(false);


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(Urls.editionGroupListByidUrl+editionGroupId, new AsyncHttpResponseHandler() {

            public void onStart() {

                progressBar.setVisibility(View.VISIBLE);
                editionGroupListByRecyclerView.setVisibility(View.GONE);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
// called when response HTTP status is "200 OK"
//status
                String s = new String(response);
                ArrayList<String> listCategory = new ArrayList<String>();

                editionGroupByLists = new ArrayList<EditionGroupByList>();

                try {

                    JSONArray jsonArray = new JSONArray(s);


                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jObj =jsonArray.getJSONObject(i);

                        //clientNameId = jObj.getString("CountryName");

                        EditionGroupByList editionGroupList = new EditionGroupByList();
                        editionGroupList.setId(jObj.getString("id"));
                        editionGroupList.setBooking_unit(jObj.getString("booking_unit"));
                        editionGroupList.setShort_text(jObj.getString("short_text"));
                        editionGroupList.setLong_text(jObj.getString("long_text"));


                        editionGroupByLists.add(editionGroupList);

                    }

                    recyclerviewViewadapter3 = new EditionGroupByListAdapter(editionGroupByLists, BookingFormActivity.this,editionsByIdGroupDialog);
                    editionGroupListByRecyclerView.setAdapter(recyclerviewViewadapter3);
                    progressBar.setVisibility(View.GONE);
                    editionGroupListByRecyclerView.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    editionGroupListByRecyclerView.setVisibility(View.GONE);

                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
// called when response HTTP status is "4XX" (eg. 401, 403, 404)
                progressBar.setVisibility(View.GONE);
                editionGroupListByRecyclerView.setVisibility(View.GONE);

            }

        });



        editionsByIdGroupDialog.show();
    }


    //editionsList popup spinner list ends------->


    private void categoryData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getcategoryListUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            categoryId = jsonObject1.getString("name");
                            listCategory.add(categoryId);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            categoryListSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_category, getApplicationContext()));

                            categoryListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        categoryIdValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        categoryIdValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void schemaData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getSchemaDataUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            schemaId = jsonObject1.getString("name");
                            listCategory.add(schemaId);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            SchemaSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_schema, getApplicationContext()));

                            SchemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        schemaIdValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        schemaIdValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void paymentModeData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getCollectionModeList, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            paymentString = jsonObject1.getString("name");
                            listCategory.add(paymentString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            paymentModeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected3, getApplicationContext()));

                            paymentModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        paymentStringValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        paymentStringValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void getDivisionListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getDivisionListURl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("division_id");
                            divisionString = jsonObject1.getString("division_name");
                            listCategory.add(divisionString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            divisonListSpinner.setAdapter(arrayAdapter);
                            //divisonListSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected7, getApplicationContext()));

                            divisonListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        divisionValueString = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        divisionValueString = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });




                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geSalesOfficeListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getSalesOfficeListURl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("unit_id");
                            salesOfficeString = jsonObject1.getString("unit_name");
                            listCategory.add(salesOfficeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            salesOfficeSpinner.setAdapter(arrayAdapter);
                           // salesOfficeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected8, getApplicationContext()));

                            salesOfficeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        salesOfficeStringValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        salesOfficeStringValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geBookinOfficeListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getBookinOfficeListURl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("unit_id");
                            bookingOfficeString = jsonObject1.getString("unit_name");
                            listCategory.add(bookingOfficeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            BookingOfficeSpinner.setAdapter(arrayAdapter);
                            //BookingOfficeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected9, getApplicationContext()));

                            BookingOfficeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        bookingOfficeStringValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        bookingOfficeStringValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geItemCategoryListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getItemCategoryListURl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("itemcategory_id");
                            itemCategoryString = jsonObject1.getString("itemcategory_name");
                            listCategory.add(itemCategoryString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            ItemCategorySpinner.setAdapter(arrayAdapter);
                           // ItemCategorySpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected10, getApplicationContext()));

                            ItemCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        itemCategoryStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        itemCategoryStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geAdvertiserCodeListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getAdvertiserCodeListURl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("advertiser_id");
                            advertiserCodeString = jsonObject1.getString("advertiser_name");
                            listCategory.add(advertiserCodeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            AdvertiserCodeSpinner.setAdapter(arrayAdapter);
                           // AdvertiserCodeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected11, getApplicationContext()));

                            AdvertiserCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        advertiserCodeStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        advertiserCodeStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geBusinessContentComponentListData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.geBusinessContentComponentUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("bcc_id");
                            businessComString = jsonObject1.getString("bcc_name");
                            listCategory.add(businessComString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            BusinessContentComponentSpinner.setAdapter(arrayAdapter);
                           // BusinessContentComponentSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected12, getApplicationContext()));

                            BusinessContentComponentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        businessComStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        businessComStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void gePagePositionData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getPagepositionUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("page_id");
                            pagePositionString = jsonObject1.getString("page_name");
                            listCategory.add(pagePositionString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            PagePostionSpinner.setAdapter(arrayAdapter);

                            //PagePostionSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected13, getApplicationContext()));

                            PagePostionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        pagePositionStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        pagePositionStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geSegmentsData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getSegmentsUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("segment_id");
                            segmentsString = jsonObject1.getString("segment_name");
                            listCategory.add(segmentsString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            segmentsListSpinner.setAdapter(arrayAdapter);

                           // segmentsListSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected14, getApplicationContext()));

                            segmentsListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        segmentsStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        segmentsStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geFieldCollectorData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getFieldCollectorUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("fc_id");
                            fieldCollectorString = jsonObject1.getString("fc_name");
                            listCategory.add(fieldCollectorString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            FieldCollectorSpinner.setAdapter(arrayAdapter);
                            //FieldCollectorSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected15, getApplicationContext()));

                            FieldCollectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        fieldCollectorStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        fieldCollectorStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void geSalesDocTypeData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getSalesDocTypeUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            salesDocTypeString = jsonObject1.getString("field_value");
                            listCategory.add(salesDocTypeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            SalesDocTypeSpinner.setAdapter(arrayAdapter);
                            //SalesDocTypeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected16, getApplicationContext()));

                            SalesDocTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        salesDocTypeStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        salesDocTypeStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getSalesOrgData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getSalesOrgUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            salesOrgString = jsonObject1.getString("field_value");
                            listCategory.add(salesOrgString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            SalesOrgSpinner.setAdapter(arrayAdapter);
                          //  SalesOrgSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected17, getApplicationContext()));

                            SalesOrgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        salesOrgStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        salesOrgStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getDistributionChannelData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getDistributionChannelUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            DistributionChannelString = jsonObject1.getString("field_value");
                            listCategory.add(DistributionChannelString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);
                            DistributionChannelSpinner.setAdapter(arrayAdapter);
                            //DistributionChannelSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected18, getApplicationContext()));

                            DistributionChannelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        DistributionChannelStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        DistributionChannelStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getRangeData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getRangeUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            rangeString = jsonObject1.getString("field_value");
                            listCategory.add(rangeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            RangeSpinner.setAdapter(arrayAdapter);
                           // RangeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected19, getApplicationContext()));

                            RangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        rangeStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        rangeStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getItemTypeData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getItemTypeUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            itmTypeString = jsonObject1.getString("field_value");
                            listCategory.add(itmTypeString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            ItemTypeSpinner.setAdapter(arrayAdapter);
                            //ItemTypeSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected20, getApplicationContext()));

                            ItemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        itemTypeStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        itemTypeStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void getTermData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getTermUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            termString = jsonObject1.getString("field_value");
                            listCategory.add(termString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            getTermSpinner.setAdapter(arrayAdapter);

                         //   getTermSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected21, getApplicationContext()));

                            getTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        termStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        termStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void getUnitMeasureData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getUnitMeasureUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            unitMeasureString = jsonObject1.getString("field_value");
                            listCategory.add(unitMeasureString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            unitMeasureSpinner.setAdapter(arrayAdapter);
                           // unitMeasureSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected22, getApplicationContext()));

                            unitMeasureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        unitMeasureStringValue = s;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        unitMeasureStringValue = s;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void getFieldcollectorRoleData(){


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Urls.getFieldcollectorRoleUrl, new AsyncHttpResponseHandler() {

            public void onStart() {


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final ArrayList<String> listCategory = new ArrayList<String>();
                final ArrayList<String> listCat_id=new ArrayList<String>();
                String s = new String(responseBody);
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            String stateId = jsonObject1.getString("id");
                            FieldCollectorString = jsonObject1.getString("field_value");
                            listCategory.add(FieldCollectorString);
                            listCat_id.add(stateId);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.spinner_item, listCategory);

                            FieldcollectorRoleSpinner.setAdapter(arrayAdapter);
                           // FieldcollectorRoleSpinner.setAdapter(new NothingSelectedSpinnerAdapter(arrayAdapter, R.layout.spinner_row_nothingselected23, getApplicationContext()));

                            FieldcollectorRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position == 0) {

                                        String s = listCat_id.get(position);
                                        String title = listCategory.get(position);
                                        FieldCollectorRoleStringValue = title;

                                    } else {

                                        String s = listCat_id.get(position - 1);
                                        String title = listCategory.get(position -1);
                                        FieldCollectorRoleStringValue = title;

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this,"Please try again later..",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void bookingSubmitData(String newName,String adCaption,String discount,String roNumber,String size,String height,String description,String categoryDiscount,String otherDiscount,String price,String actualPrice,String date,String roDate,String addressString,String townStg,String districtStg,String pincodeStg,String phoneStg,String aadharString,String panString,String positionStg,String instrumentNoStg,String emailId,String collectingAmount,String otpNEW,String moreDatesString,String spaceString,String ratePerSqcmString){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", ClientId);
        params.put("new_name", newName);
        params.put("address", addressString);
        params.put("town", townStg);
        params.put("district", districtStg);
        params.put("pincode", pincodeStg);
        params.put("phone", phoneStg);
        params.put("aadhaar_number", aadharString);
        params.put("pan_number", panString);
        params.put("editionlist_id",editionsId);
        params.put("position",positionStg);
        params.put("color",ColorId);
        params.put("payment_mode",paymentStringValue);
        params.put("instrument_no",instrumentNoStg);
        params.put("date",sendChequeDate);
        params.put("description", description);
        params.put("scheme", schemaIdValue);
        params.put("category_discount", categoryDiscount);
        params.put("other_discount", otherDiscount);
        params.put("checq_image", img);
        params.put("signature", signatureImageStg);
      //  params.put("media", media);
        params.put("width", size);
        params.put("height", height);
        params.put("total_price", price);
        params.put("actual_price", actualPrice);
        params.put("discount", discountIDValue);
        params.put("edition_date", date);
        params.put("email", emailId);
        params.put("created_by",PrefManager.getEmpCode(BookingFormActivity.this, "username"));
        params.put("created_unit",PrefManager.getUnitId(BookingFormActivity.this, "unit_id"));
        params.put("collected_amount", collectingAmount);
        params.put("sent_otp", otpSuccess);
        params.put("given_otp", otpNEW);
        params.put("lat", lat);
        params.put("long", lang);
        params.put("location", address);
        params.put("RO_number", roNumber);
        params.put("ro_date", roDateEdt);
        params.put("category", categoryIdValue);
        params.put("ad_caption", adCaption);
        params.put("ad_discount", discount);

        params.put("edition_more_dates", moreDatesString);
        params.put("sqcms", spaceString);
        params.put("rate_sqcms", ratePerSqcmString);


        params.put("sales_doc_type", salesDocTypeStringValue);
        params.put("sales_org", salesOrgStringValue);
        params.put("distribution_channel", DistributionChannelStringValue);
        params.put("division", divisionValueString);

        params.put("item_type", itemTypeStringValue);
        params.put("item_category", itemCategoryStringValue);
        params.put("advertiser", advertiserCodeStringValue);
        params.put("bc_component", businessComStringValue);
        params.put("page_position", pagePositionStringValue);
        params.put("range", rangeStringValue);
        params.put("term", termStringValue);
        params.put("segment", segmentsStringValue);
        params.put("field_collector_role", FieldCollectorRoleStringValue);
        params.put("field_collector", FieldCollectorStringValue);
        //params.put("action", "mobile");


        Log.v("paramsNew", params.toString());

        client.post(Urls.addBookingUrl,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started

                progressDialog = new ProgressDialog(BookingFormActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {

                    JSONObject jsonObject = new JSONObject(new String(response));
                    if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                        if (editOtp.getText().toString().equals(otpSuccess)) {
                            Toast.makeText(BookingFormActivity.this, "Booking submitted sucessfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();

                        }else{
                            final AlertDialog.Builder builder = new AlertDialog.Builder(BookingFormActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle("Otp not maching You want to submit Booking?");
                            builder.setIcon(R.drawable.alert_icon_png);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Toast.makeText(BookingFormActivity.this, "Booking submitted sucessfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    progressDialog.dismiss();
                                    dialog.cancel();
                                }

                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                    else{
                        Toast.makeText(BookingFormActivity.this,"Please Try Again later",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                Toast.makeText(BookingFormActivity.this,"Please Try Again later",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });


    }

    private void clientUpdateData(){



    }

    public void update_publication_checkbox1(String dets,String id){
        if(dets.length()>1) {
            editionsText.setVisibility(View.VISIBLE);
            editionsText.setText(dets);
            editionsId = id;
        }else{
            editionsText.setVisibility(View.GONE);
        }
        // Toast.makeText(AddSalesPromotersActivity.this,""+pos,Toast.LENGTH_SHORT).show();
    }
    public void update_publication_checkbox(String dets,String id){
        if(dets.length()>1) {
            editionsText.setVisibility(View.VISIBLE);
            editionsText.setText(dets);
            editionsId = id.substring(0, id.lastIndexOf(","));
        }else{
            editionsText.setVisibility(View.GONE);
        }
        // Toast.makeText(AddSalesPromotersActivity.this,""+pos,Toast.LENGTH_SHORT).show();
    }


    public class signature extends View {

        private static final float STROKE_WIDTH = 10f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 10;
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        @SuppressLint("WrongThread")
        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(canvasLL.getWidth(), canvasLL.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }


    private void photoOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a option");
        // add a list
        String[] options = {"Camera", "Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // Camera
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        break;
                    case 1: // Gallery
                        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
                        gallerypickerIntent.setType("image/*");
                        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
                        break;
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    Bitmap photo;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (data != null) {
                if (data.getExtras() != null) {
                    photo = (Bitmap) data.getExtras().get("data");
                    img = getStringImage(photo);
                    imagePreview.setImageBitmap(photo);
                    //  ivImagePreviewFullScreen.setImageBitmap(photo);
                  /*  TextView tvImg = (TextView) findViewById(R.id.tvImg);
                    tvImg.setText("Image is taken successfully.");*/
                }
            }
        } else {
            if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                new AsyGalleryTask().execute();
                //ivImagePreviewFullScreen.setVisibility(View.VISIBLE);
                //ivImagePreviewFullScreen.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
        }
    }

    class AsyGalleryTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
           /* pd=new ProgressDialog(AddRemittenceActivity.this);
            pd.setTitle("Please wait, image is being loaded.");
            pd.show();*/
        }
        @Override
        protected Void doInBackground(Void... params) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            photo= BitmapFactory.decodeFile(picturePath);
            img = getStringImage(photo);
            return null;
        }

        @Override
        protected void onPostExecute(Void Void) {
            super.onPostExecute(Void);
            // pd.hide();
            imagePreview.setImageBitmap(photo);
            //  ivImagePreviewFullScreen.setImageBitmap(photo);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImageNew(Bitmap bmp) {
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
        byte[] imageBytes = baos1.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void submitOtpData(String number){

        AsyncHttpClient client1 = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("number",number);
        params.put("collected_amount",collectAmountEdt.getText().toString());

        client1.post(Urls.otpUrl,params, new AsyncHttpResponseHandler() {


            public void onStart() {

                progressDialog = new ProgressDialog(BookingFormActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();


            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    otpSuccess = jsonObject.getString("otp");
                    Toast.makeText(getApplicationContext(),"OTP sent successfully.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(BookingFormActivity.this, "Try Again later", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                Toast.makeText(BookingFormActivity.this, "Try Again later", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }



    private void closeScreenDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to exit from screen?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void clientList(String clientName, String address,String town,String district,String pincode,String mobileNumber,String clientId,String emailId,String outStanding,String aadhar,String panNumber) {
        clientSpinner.setText(clientName);
        oldNameEdt.setText(clientName);
        addressOldEdt.setText(address);
        townEdtOld.setText(town);
        districtEdtOld.setText(district);
        pincodeEdtOld.setText(pincode);
        mobileNumberEdtOld.setText(mobileNumber);
        aadharNumberEdt.setText(aadhar);
        panNumberEdt.setText(panNumber);

        if (!emailId.equalsIgnoreCase("null"))
            emailEdtOld.setText(emailId);

        showClientDetails.setVisibility(View.VISIBLE);
        this.ClientId = clientId;
    }

    @Override
    public void editionGroupList(String shortName,String groupName) {

        editionGrouping.setText(shortName+"  "+groupName);
        this.editionGroupId = groupName;
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        closeScreenDialog();
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
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                //  addressText.setText("Current Location: " +address+", "+city);
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
                                ActivityCompat.requestPermissions(BookingFormActivity.this,
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
}
