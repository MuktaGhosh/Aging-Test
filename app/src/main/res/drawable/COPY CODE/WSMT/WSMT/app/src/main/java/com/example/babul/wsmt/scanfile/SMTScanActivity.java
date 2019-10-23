package com.example.babul.wsmt.scanfile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.R;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.Alternatebom;
import com.example.babul.wsmt.models.BomSearchDeatils;
import com.example.babul.wsmt.models.Feeder;
import com.example.babul.wsmt.models.SearchBom;
import com.example.babul.wsmt.utils.HideSoftkeyboard;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SMTScanActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {

    Button btnScanBarcode;
    TextView tvQrCode, tvLocation, tvFeedRqno, tvFeedName, tvType, tvSize, tvRefference;
    TextView tvBOmName, tvManuPartNO, tvDescription, tvManuName;
    EditText editText;
    Context mContext;
    SearchBom searchBom;
    Alternatebom alternatebom;
    List<Feeder> feedList = new ArrayList<>();
    List<String> locationList = new ArrayList<>();
    String qrcode, checkoption;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HideSoftkeyboard.hideKeyboard(this);

        setContentView(R.layout.smt_scan_layout_activity);
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String myDeviceModel = android.os.Build.MODEL;

        Log.e("android id", android_id);
        Log.e("model", myDeviceModel);

        tvQrCode = findViewById(R.id.qrcode);
        editText = findViewById(R.id.qrText);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    CharSequence s = editText.getText();

                    Log.e("hand set", SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID));
                    Log.e("qr codeee", String.valueOf(s));
                    searchBom(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), String.valueOf(s));
                    editText.getText().clear();
                }
                return false;
            }
        });

        initViews();
        if (myDeviceModel.equalsIgnoreCase("PL-50L")) {
            btnScanBarcode.setVisibility(View.INVISIBLE);

        }
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
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnScanBarcode.setOnClickListener(this);

    }

    private void searchBom(String handset, String qrcode) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.searchSMTBoms(handset, qrcode, "1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanBarcode:
                startActivityForResult(new Intent(SMTScanActivity.this, ScannedBarcodeActivity.class), 0);
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            if (data.getStringExtra("qrcode") != null) {
                Log.e("cmaera qr code", data.getStringExtra("qrcode"));
                tvQrCode.setText("QRCode     :" + data.getStringExtra("qrcode"));
                qrcode = data.getStringExtra("qrcode");
                Log.e("handset id", SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID));
                searchBom(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), qrcode);

            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getResponse(Object responseData) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("bom search smt response", new Gson().toJson(responseData));
        try {
            if (responseData instanceof SearchBom) {
                alternatebom = new Alternatebom();

                feedList.clear();
                locationList.clear();
                searchBom = (SearchBom) responseData;
                if (searchBom.getFeeders() != null) {
                    if (searchBom.getFeeders().size() > 0) {
                        feedList.addAll(searchBom.getFeeders());
                        Log.e("niot null", "ok");
                    } else {
                        if (searchBom.getAlternateboms() != null) {
                            feedList.addAll(searchBom.getAlternateboms().getFeeders());
                            Log.e(" null", "ok");
                        } else {

                        }
                    }
                }
           /* Log.e("okkk ", new Gson().toJson((searchBom.getAlternateboms().getFeeders())));
            Log.e("llll size", String.valueOf(feedList.size()));*/
                alternatebom = searchBom.getAlternateboms();

                if (alternatebom != null) {
                    tvFeedRqno.setText("" + alternatebom.getPartnumber());
                    tvDescription.setText("" + alternatebom.getDescription());
                    for (int i = 0; i < alternatebom.getFeeders().size(); i++) {
                        locationList.add(alternatebom.getFeeders().get(i).getLocation());
                    }
                } else {
                    tvFeedRqno.setText("" + searchBom.getPartnumber());
                    tvDescription.setText("" + searchBom.getDescription());
                }


                for (int i = 0; i < feedList.size(); i++) {
                    locationList.add(feedList.get(i).getLocation());
                }

                tvRefference.setText("" + searchBom.getReference());
                tvBOmName.setText("" + searchBom.getName());
                tvManuPartNO.setText("" + searchBom.getManufacturerPartnumber());
                tvManuName.setText("" + searchBom.getManufacturer());

                if (searchBom.getFeeders().size() > 0) {

                    tvFeedName.setText("" + feedList.get(0).getName());
                    tvType.setText("" + feedList.get(0).getType());
                    tvSize.setText("" + feedList.get(0).getqTY());
                }

                StringBuilder builder = new StringBuilder();
                for (String details : locationList) {
                    builder.append(details + ",");
                }

                tvLocation.setText("" + builder.toString());

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
        }catch(Exception ex){
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
        Log.e("error in bom list ", errMsg);

    }

}

