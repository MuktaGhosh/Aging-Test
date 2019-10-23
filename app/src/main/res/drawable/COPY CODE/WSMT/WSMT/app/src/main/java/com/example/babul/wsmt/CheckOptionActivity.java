package com.example.babul.wsmt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.Profile;
import com.example.babul.wsmt.models.ProfileBase;
import com.example.babul.wsmt.scanfile.ReverseSMTScanActivity;
import com.example.babul.wsmt.scanfile.SMTScanActivity;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CheckOptionActivity extends AppCompatActivity {
    TextView tvSubmit;
    Context mContext;
    RadioGroup radioGroup, reverseLocationRadioGroup;
    int radioItem, subradioItem;
    LinearLayout rvLinearLayout;
    String subcheckoption="", checkoption="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_option_layout);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rvLinearLayout = findViewById(R.id.reverseLocationLayout);
        reverseLocationRadioGroup = findViewById(R.id.reverseLocationRadioGroup);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioItem = checkedId;
                Log.e("radio", String.valueOf(radioItem));
                RadioButton radioButton = radioGroup.findViewById(checkedId);
                checkoption= (String) radioButton.getText();

                Log.e("radio string ", checkoption);
                if(checkoption.equalsIgnoreCase("Random Check")){
                    rvLinearLayout.setVisibility(View.VISIBLE);

                }else{
                    rvLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        reverseLocationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                subradioItem = checkedId;
                rvLinearLayout.setVisibility(View.VISIBLE);
                RadioButton  rvRadioButton = reverseLocationRadioGroup.findViewById(checkedId);
                subcheckoption = (String) rvRadioButton.getText();
            }
        });

        tvSubmit = findViewById(R.id.tvStart);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (radioItem >0) {
                    if(subcheckoption.equalsIgnoreCase("Reverse Location")){
                        Intent in = new Intent(mContext, ReverseSMTScanActivity.class);
                        startActivity(in);
                    }else {
                        Intent in = new Intent(mContext, SMTScanActivity.class);
                        in.putExtra("checkoption", checkoption);
                        in.putExtra("subcheckoption", subcheckoption);
                        startActivity(in);
                    }

                } else {
                    Toast.makeText(mContext, "Please select one check option first", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioItem = checkedId;
                Log.e("radio", String.valueOf(radioItem));
                RadioButton radioButton = radioGroup.findViewById(checkedId);
                checkoption= (String) radioButton.getText();

                Log.e("radio string ", checkoption);
                if(checkoption.equalsIgnoreCase("Random Check")){
                    rvLinearLayout.setVisibility(View.VISIBLE);

                }else{
                    rvLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        reverseLocationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                subradioItem = checkedId;
                rvLinearLayout.setVisibility(View.VISIBLE);
                RadioButton  rvRadioButton = reverseLocationRadioGroup.findViewById(checkedId);
                subcheckoption = (String) rvRadioButton.getText();
            }
        });
    }
}


