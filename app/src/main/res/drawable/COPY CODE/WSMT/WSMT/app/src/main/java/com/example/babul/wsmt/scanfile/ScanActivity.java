package com.example.babul.wsmt.scanfile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteCursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.R;
import com.example.babul.wsmt.ReelDetailsActivity;
import com.example.babul.wsmt.SelectHandsetActivity;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.BaseBomSearch;
import com.example.babul.wsmt.models.BomSearchDeatils;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.models.SearchBom;
import com.example.babul.wsmt.utils.HideSoftkeyboard;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {

    Button btnScanBarcode;
    TextView tvQrCode, tvBOmName, tvManuPartNO, tvSubmit;
    EditText editText, editCheckedItem;
    Context mContext;
    BomSearchDeatils bomSearchDeatils;
    String qrcode = "";
    LinearLayout checkLinearLayout, passFailLinearLayout;
    TextView tvToalChecked, tvTotalReel;
    Spinner passfailSpinner;
    int passFailStaus =0;
    int checkExistItem;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_main);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        HideSoftkeyboard.hideKeyboard(this);
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String myDeviceModel = android.os.Build.MODEL;
        Log.e("android id", android_id);
        Log.e("model", myDeviceModel);

        tvQrCode = findViewById(R.id.qrcode);
        editText = findViewById(R.id.qrText);
        tvSubmit = findViewById(R.id.tvSubmit);
        tvSubmit.setOnClickListener(this);

        tvToalChecked = findViewById(R.id.tv_reel_check);
        tvTotalReel = findViewById(R.id.tv_reel);

        passFailLinearLayout = findViewById(R.id.passfailLayout);
        checkLinearLayout = findViewById(R.id.checkLayout);
        editCheckedItem = findViewById(R.id.et_checked_item);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    CharSequence s = editText.getText();

                    Log.e("xxxx", String.valueOf(s));
                    searchBom(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID), String.valueOf(s));
                    editText.getText().clear();
                }
                return false;
            }
        });
        editText.setShowSoftInputOnFocus(false);
        initViews();
        if (myDeviceModel.equalsIgnoreCase("PL-50L")) {
            btnScanBarcode.setVisibility(View.INVISIBLE);

        } else {
            editText.setVisibility(View.GONE);
        }
        passfailSpinner = findViewById(R.id.sp_passfail);

        final List<String> list = new ArrayList<String>();
        list.add("Pass");
        list.add("Fail");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        passfailSpinner.setAdapter(dataAdapter);
        passfailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "The option is:" + list.get(position), Toast.LENGTH_SHORT).show();

                if(list.get(position).equals("Pass")){
                    passFailStaus = 1;
                }else{
                    passFailStaus = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void initViews() {

        tvBOmName = findViewById(R.id.tv_name);
        tvManuPartNO = findViewById(R.id.tv_qr_number);

        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        btnScanBarcode.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanBarcode:
                startActivityForResult(new Intent(ScanActivity.this, ScannedBarcodeActivity.class), 0);
                break;
            case R.id.tvSubmit:
                try {
                    int inputItemCheck = Integer.parseInt(editCheckedItem.getText().toString());
                    if (!editCheckedItem.getText().toString().equalsIgnoreCase("")) {
                        if (inputItemCheck <= checkExistItem) {
                            sendCheckData(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID),
                                    SharedPreference.getStringValue(mContext, SharedPreference.USER_ID),
                                    tvManuPartNO.getText().toString(), editCheckedItem.getText().toString(), String.valueOf(passFailStaus));
                        } else {
                            Toast.makeText(mContext, "Item Limit Exceed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please input number of item checked", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("exception", String.valueOf(ex));
                }
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
                searchBom(SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID),qrcode);

            }
        }
    }


    private void sendCheckData(String handset, String user_id, String qrcode, String item_chekced, String passFailStatus) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.sendCheckData(handset, user_id, qrcode, item_chekced, passFailStatus);
    }

    private void searchBom(String handset, String qrcode) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.searchBoms(handset, qrcode);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getResponse(Object responseData) {
        try {
            UserDialog.getInstanc().stopProgressDialog();
            Log.e("bom search response", new Gson().toJson(responseData));
            if (responseData instanceof BomSearchDeatils) {
                bomSearchDeatils = (BomSearchDeatils) responseData;
                tvBOmName.setText("" + bomSearchDeatils.getName());
                tvManuPartNO.setText("" + bomSearchDeatils.getPartnumber());
                tvToalChecked.setText("" + bomSearchDeatils.getReelsChecked());
                tvTotalReel.setText("" + bomSearchDeatils.getTotalReel());
                checkLinearLayout.setVisibility(View.VISIBLE);
                passFailLinearLayout.setVisibility(View.VISIBLE);
                checkExistItem = Integer.parseInt(bomSearchDeatils.getTotalReel()) - Integer.parseInt(bomSearchDeatils.getReelsChecked());
                tvSubmit.setVisibility(View.VISIBLE);

            } else {
                SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_ID);
                SharedPreference.getStringValue(mContext, SharedPreference.HANDSET_TITLE);
                finish();
            }
        } catch (Exception ex) {
            Log.e("Exception", String.valueOf(ex));
        }

    }

    @Override
    public void getError(String errMsg) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("error in bom list ", errMsg);
    }
}
