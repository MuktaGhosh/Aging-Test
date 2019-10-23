package com.example.babul.wsmt.scanfile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.R;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.Alternatebom;
import com.example.babul.wsmt.models.Feeder;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.models.ProfileBase;
import com.example.babul.wsmt.models.ReverseAlter;
import com.example.babul.wsmt.models.ReverseBOM;
import com.example.babul.wsmt.models.ReverseLocation;
import com.example.babul.wsmt.models.SearchBom;
import com.example.babul.wsmt.utils.HideSoftkeyboard;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReverseSMTScanActivity extends AppCompatActivity implements ResponseListener {


    LinearLayout checkFeederLayout, inputLayout;
    EditText etQrcodedigit;
    TextView tvLocation, tvFeedRqno, tvFeedName, tvType, tvSize, tvRefference, tvCheckPartnumber;
    TextView tvBOmName, tvManuPartNO, tvDescription, tvManuName, tvScan, tvInput, tvBack;
    Spinner spLocationSecondPart, machineSpinner;
    Context mContext;
    List<String> firstLoocationList = new ArrayList<>();
    List<String> secondLocationList = new ArrayList<>();
    List<ReverseLocation> reverseList = new ArrayList<>();
    ReverseBOM reverseBOM;
    ReverseAlter reverseAlter;
    String location, machineFirst, machineName, locationFirstPart, locationSecondPart;
    ArrayAdapter<String> dataAdapter, seconDataAdapter;
    int request_status;
    boolean isSelected = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HideSoftkeyboard.hideKeyboard(this);
        setContentView(R.layout.reverse_location_layout_activity);
/*
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String myDeviceModel = android.os.Build.MODEL;

        Log.e("android id", android_id);
        Log.e("model", myDeviceModel);
*/
        initViews();
        checkFeederLayout = findViewById(R.id.checkFeederLayout);
        tvBack = findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputLayout = findViewById(R.id.inputLayout);
        tvInput = findViewById(R.id.tvInput);
        tvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLayout.setVisibility(View.VISIBLE);
            }
        });
        tvScan = findViewById(R.id.tvScan);
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputLayout.setVisibility(View.GONE);
            }
        });


        machineSpinner = findViewById(R.id.sp_machine);

        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, firstLoocationList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        machineSpinner.setAdapter(dataAdapter);

        machineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "The option is:" + firstLoocationList.get(position), Toast.LENGTH_SHORT).show();
                String[] machineNameandPart = firstLoocationList.get(position).split("-", 2);
                machineName = machineNameandPart[0];
                locationFirstPart = machineNameandPart[1];
                getLocationSecondPart(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), firstLoocationList.get(position) + "");
                request_status = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("nothing ", "change");
            }

        });


        spLocationSecondPart = findViewById(R.id.spSecondLoc);
        seconDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, secondLocationList);
        seconDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocationSecondPart.setAdapter(seconDataAdapter);


        spLocationSecondPart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "The option is:" + secondLocationList.get(position), Toast.LENGTH_SHORT).show();

                locationSecondPart = secondLocationList.get(position);
                location = locationFirstPart + "" + "-" + locationSecondPart + "";

                Log.e("ok", SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID) + "===" + location + "===" + locationFirstPart);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getReverseData(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), location, machineName);
                        request_status = 2;
                    }
                }, 1000);
                isSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("nothing second ", "change");
                getReverseData(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), location, machineName);

            }

        });

        getLocationFirstPart(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID));
        request_status = 0;

    }

    private void initViews() {
        tvLocation = findViewById(R.id.tv_location);
        tvFeedRqno = findViewById(R.id.tv_qr_number);
        tvFeedName = findViewById(R.id.tv_feeder_name);
        tvType = findViewById(R.id.tv_type);
        tvSize = findViewById(R.id.tv_size);
        tvRefference = findViewById(R.id.tv_refference);

        tvBOmName = findViewById(R.id.tv_name);
        tvManuPartNO = findViewById(R.id.tv_manu_part_no);
        tvDescription = findViewById(R.id.tv_description);
        tvManuName = findViewById(R.id.tv_manu_name);
        tvCheckPartnumber = findViewById(R.id.tvcheckPartNumber);

    }

    private void getReverseData(String handset, String location, String machineName) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.getReverseLocationInfo(handset, location, machineName);
    }

    private void getLocationFirstPart(String handset) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.getLocationFirstPart(handset);
    }

    private void getLocationSecondPart(String handset, String machineName) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.getLocationSecondPart(handset, machineName);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void getResponse(Object responseData) {
        Log.e("reverse smt response", new Gson().toJson(responseData));
        try {
            if (request_status == 0) {
                inputLayout.setVisibility(View.GONE);
                UserDialog.getInstanc().stopProgressDialog();
                firstLoocationList.clear();
                firstLoocationList.addAll((Collection<? extends String>) responseData);
                dataAdapter.notifyDataSetChanged();


            } else if (request_status == 1) {
                inputLayout.setVisibility(View.GONE);
                UserDialog.getInstanc().stopProgressDialog();
                secondLocationList.clear();
                secondLocationList.addAll((Collection<? extends String>) responseData);
                seconDataAdapter.notifyDataSetChanged();

            } else {
                inputLayout.setVisibility(View.GONE);
                UserDialog.getInstanc().stopProgressDialog();
                checkFeederLayout.setVisibility(View.VISIBLE);
                reverseList.clear();
                reverseBOM = new ReverseBOM();
                reverseAlter = new ReverseAlter();
                reverseList.addAll((Collection<? extends ReverseLocation>) responseData);
                reverseBOM = reverseList.get(0).getBom();
                reverseAlter = reverseList.get(0).getReverseAlter();
                if (reverseList.size() > 0) {

                    tvFeedRqno.setText("" + reverseList.get(0).getPartnumber());
                    String partNumber = "" + reverseList.get(0).getPartnumber();

                    tvCheckPartnumber.setText((partNumber.substring(0, partNumber.length() - 4)));

                    firstLoocationList.add(reverseList.get(0).getLocation());
                    tvFeedName.setText("" + reverseList.get(0).getName());
                    tvType.setText("" + reverseList.get(0).getType());
                    tvSize.setText("" + reverseList.get(0).getqTY());
                    if (reverseBOM != null) {
                        tvFeedRqno.setText("" + reverseBOM.getPartnumber());
                        tvDescription.setText("" + reverseBOM.getDescription());
                        tvRefference.setText("" + reverseBOM.getReference());
                        tvBOmName.setText("" + reverseBOM.getName());
                        tvManuPartNO.setText("" + reverseBOM.getManufacturerPartnumber());
                        tvManuName.setText("" + reverseBOM.getManufacturer());
                    } else {
                        if (reverseAlter != null) {
                            tvFeedRqno.setText("" + reverseAlter.getPartnumber());
                            tvDescription.setText("" + reverseAlter.getDescription());
                            tvRefference.setText("" + reverseAlter.getBom().getReference());
                            tvBOmName.setText("" + reverseAlter.getBom().getName());
                            tvManuPartNO.setText("" + reverseAlter.getManufacturerPartnumber());
                            tvManuName.setText("" + reverseAlter.getManufacturer());
                        }
                    }

                } else {
                    Toast.makeText(mContext, "Not Found!", Toast.LENGTH_SHORT).show();
                    tvFeedRqno.setText("");
                    tvRefference.setText("");
                    tvBOmName.setText("");
                    tvManuPartNO.setText("");
                    tvDescription.setText("");
                    tvManuName.setText("");

                    tvFeedName.setText("");
                    tvType.setText("");
                    tvSize.setText("");
                    tvLocation.setText("");
                }
            }
        } catch (Exception ex) {
            Log.e("errorr smt scan ", String.valueOf(ex));
            Toast.makeText(mContext, "Not Found!", Toast.LENGTH_SHORT).show();
            tvFeedRqno.setText("");
            tvRefference.setText("");
            tvBOmName.setText("");
            tvManuPartNO.setText("");
            tvDescription.setText("");
            tvManuName.setText("");

            tvFeedName.setText("");
            tvType.setText("");
            tvSize.setText("");
            tvLocation.setText("");
        }
    }

    @Override
    public void getError(String errMsg) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("error in reverse list ", errMsg);
    }
}


