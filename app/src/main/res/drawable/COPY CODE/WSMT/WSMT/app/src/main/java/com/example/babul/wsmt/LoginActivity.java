package com.example.babul.wsmt;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.TelecomManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.aevi.print.PrinterApi;
import com.aevi.print.PrinterManager;
import com.aevi.print.model.PrintJob;
import com.aevi.print.model.PrintPayload;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.Profile;
import com.example.babul.wsmt.models.ProfileBase;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import io.reactivex.functions.Consumer;
import okhttp3.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements ResponseListener {
    TextView tvLogin;
    EditText etUserName, etPassword;
    Context mContext;
    ProfileBase profileBase;
    Profile profile;
    List<Profile> profileList = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mContext = this;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        tvLogin = findViewById(R.id.tvlogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

           etUserName.setText("smt");
           etPassword.setText("123");

                // etUserName.setText("36535");
                //etPassword.setText("36535");

                loginApiForUser(etUserName.getText().toString(), etPassword.getText().toString());


            }
        });

    }
    private void loginApiForUser(String userName, String password) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.login(userName, password);
    }

    @Override
    public void getResponse(Object responseData) {
        UserDialog.getInstanc().stopProgressDialog();

        if(responseData instanceof ProfileBase) {
            profileBase = (ProfileBase) responseData;
            profileList = profileBase.getDatalist();
            SharedPreference.setStringValue(mContext, SharedPreference.USER_ID, profileList.get(0).getId());
            SharedPreference.setStringValue(mContext, SharedPreference.USER_TYPE, profileList.get(0).getUserType().getTitle());
            startActivity(new Intent(getApplicationContext(), SelectHandsetActivity.class));
        }
    }


    @Override
    public void getError(String errMsg) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("error", errMsg);
    }
}

