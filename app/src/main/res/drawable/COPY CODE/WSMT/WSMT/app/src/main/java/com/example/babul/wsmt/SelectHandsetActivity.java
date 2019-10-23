package com.example.babul.wsmt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.babul.wsmt.adapters.MyListAdapter;
import com.example.babul.wsmt.api.ApiModule;
import com.example.babul.wsmt.api.ResponseListener;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.utils.HideSoftkeyboard;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SelectHandsetActivity extends AppCompatActivity implements ResponseListener {

    Context mContext;
    EditText edtHandset;
    TextView tvHandset;
    TextView tvStart;
    ArrayList<Handset> handList = new ArrayList<>();
    ArrayList<Handset> filterList = new ArrayList<>();
    MyListAdapter myListAdapter;
    RecyclerView recycleSearch;
    LinearLayoutManager layoutManager;
    String id, title;
    String pattern = null;
    String Name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        HideSoftkeyboard.hideKeyboard(this);

        setContentView(R.layout.select_handset_layout);
        edtHandset = findViewById(R.id.sp_handset);
        tvStart = findViewById(R.id.tvStart);
        recycleSearch = findViewById(R.id.recyleSearch);
        recycleSearch.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mContext);
        recycleSearch.setLayoutManager(layoutManager);
        recycleSearch.setItemAnimator(new DefaultItemAnimator());
        recycleSearch.setHasFixedSize(true);
        getHandsets();
        edtHandset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filterList.clear();
                String inputMobileNumber = edtHandset.getText().toString().toLowerCase();
                if (TextUtils.isEmpty(inputMobileNumber)) {
                    filterList.addAll(handList);
                }
                for (Handset search : handList) {

                    try {

                        pattern = search.getBompattern().toLowerCase();
                        Name = search.getTitle().toLowerCase();

                    } catch (Exception ignored) {

                    }

                    if (inputMobileNumber.length() >= 1) {
                        if ((pattern.contains(inputMobileNumber)) || Name.contains(inputMobileNumber)) {
                            filterList.add(search);
                        }
                    }
                    myListAdapter = new MyListAdapter(mContext, filterList, new MyListAdapter.ClickCallback() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onItemClick(int position) {
                            id = filterList.get(position).getId() + "";
                            edtHandset.setText(filterList.get(position).getTitle()+"");
                            HideSoftkeyboard.hideKeyboard(SelectHandsetActivity.this);

                        }
                    });
                    recycleSearch.setAdapter(myListAdapter);
                    myListAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference.setStringValue(mContext, SharedPreference.HANDSET_ID, id);
                SharedPreference.setStringValue(mContext, SharedPreference.HANDSET_TITLE, edtHandset.getText().toString()+"");

                if (SharedPreference.getStringValue(mContext, SharedPreference.USER_TYPE).equalsIgnoreCase("SMT CHECKER")) {
                    startActivity(new Intent(mContext, CheckOptionActivity.class));

                } else {
                    Intent in = new Intent(SelectHandsetActivity.this, ReelDetailsActivity.class);
                    in.putExtra("id", id);
                    in.putExtra("title", edtHandset.getText().toString()+"");
                    startActivity(in);

                }
            }
        });
    }


    private void getHandsets() {
        UserDialog.getInstanc().showProgressDialog(mContext);
        ApiModule apiModule = new ApiModule(mContext, this);
        apiModule.handsets();
    }

    @Override
    public void getResponse(Object responseData) {
        UserDialog.getInstanc().stopProgressDialog();


        handList.clear();
        handList.addAll((Collection<? extends Handset>) responseData);
        myListAdapter = new MyListAdapter(mContext, handList, new MyListAdapter.ClickCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(int position) {
                id = handList.get(position).getId() + "";
                edtHandset.setText(handList.get(position).getTitle()+"");
            }
        });
        recycleSearch.setAdapter(myListAdapter);
        myListAdapter.notifyDataSetChanged();

        Log.e("ghand set response", new Gson().toJson(responseData));
 }

    @Override
    public void getError(String errMsg) {
        UserDialog.getInstanc().stopProgressDialog();
        Log.e("error in handset", errMsg);
    }
}
