package com.example.babul.wsmt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.adapters.DetailAdapter;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.BomBase;
import com.example.babul.wsmt.models.ItemsDetail;
import com.example.babul.wsmt.print.MyPrintDocumentAdapter;
import com.example.babul.wsmt.scanfile.ScanActivity;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReelDetailsActivity extends AppCompatActivity implements ResponseListener, View.OnClickListener {
    TextView tvHandsetTitle, tvTotalReel, tvTotalItems, tvScan, tvCheckReels;
    EditText etUserName, etPassword;
    Context mContext;
    String id = "", title = "";
    BomBase bomBase;
    DetailAdapter mAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ItemsDetail> itemsDetailList = new ArrayList<>();

    String qrCode = "";
    String myDeviceModel;
    int checktedItemeExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reel_layout_activity);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myDeviceModel = android.os.Build.MODEL;
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e("android id", android_id);
        Log.e("model", myDeviceModel);
        if (getIntent() != null) {
            if (getIntent().getStringExtra("id") != null) {
                id = getIntent().getStringExtra("id");
            }
            if (getIntent().getStringExtra("title") != null) {
                title = getIntent().getStringExtra("title");
            }
        }
        tvHandsetTitle = findViewById(R.id.tv_handsetTitle);
        tvHandsetTitle.setText(title + "");

        tvTotalItems = findViewById(R.id.tv_item);
        tvTotalReel = findViewById(R.id.tv_total_reel);
        tvCheckReels = findViewById(R.id.tv_checked);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DetailAdapter(mContext, itemsDetailList);
        recyclerView.setAdapter(mAdapter);

        tvScan = findViewById(R.id.tvScan);
        tvScan.setOnClickListener(this);

        getBomList(id);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void doPrint() {
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) mContext
                .getSystemService(Context.PRINT_SERVICE);

        // Set job name, which will be displayed in the print queue
        String jobName = mContext.getString(R.string.app_name) + " Document";

        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        if (printManager != null) {
            printManager.print(jobName, new MyPrintDocumentAdapter(mContext), null); //
        }
    }

    private void getBomList(String id) {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.getBomsList(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void getResponse(Object responseData) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("bom response", new Gson().toJson(responseData));

        if (responseData instanceof BomBase) {
            bomBase = (BomBase) responseData;
            tvTotalItems.setText(String.valueOf(bomBase.getTotalItems()));
            tvTotalReel.setText(bomBase.getTotalReels());
            tvCheckReels.setText(bomBase.getChecked_reels());
            itemsDetailList.clear();
            itemsDetailList.addAll(bomBase.getItemsDetails());
            mAdapter.notifyDataSetChanged();
            checktedItemeExist = Integer.parseInt(bomBase.getTotalReels())-Integer.parseInt(bomBase.getChecked_reels());
        }
    }

    @Override
    public void getError(String errMsg) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("error in bom list ", errMsg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvScan:
                Intent in = new Intent(ReelDetailsActivity.this, ScanActivity.class);
                in.putExtra("check", checktedItemeExist);
                startActivityForResult(in, 0);
                break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            if (data.getStringExtra("qrcode") != null) {
                String barCode = "";
                qrCode = data.getStringExtra("qrcode");
                Log.e("qrcode", qrCode);
            }
        }
    }
}

