package com.example.babul.wsmt.utils;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babul.wsmt.R;


public class UserDialog {
    private static final UserDialog instance = new UserDialog();
    private ProgressDialog pDialog;

    private UserDialog() {

    }


    public static UserDialog getInstanc() {
        return instance;
    }

    public static void showUserAlert(final Context context, String message) {
        System.out.println("WSMT is loading :..." + message);
        LayoutInflater layout_inflater = LayoutInflater.from(context);
        View view = layout_inflater.inflate(R.layout.user_alert_dialog, null);

        AlertDialog.Builder alert_builder = new AlertDialog.Builder(context);
        alert_builder.setView(view);
        final AlertDialog alert = alert_builder.create();

        TextView tvTitle = (TextView) view.findViewById(R.id.idDialogHeader);
        tvTitle.setText("WSMT");
        // FontsManager.changeFonts(tvTitle, Fonts.SourceSansPro_SEMI_Bold.setFont(context));


        TextView tvMessage = (TextView) view.findViewById(R.id.idAlertMessage);
        tvMessage.setText(message);

        Button btnConfirm = (Button) view.findViewById(R.id.idConfirm);
        // FontsManager.changeFonts(btnConfirm, Fonts.SourceSansPro_SEMI_Bold.setFont(context));

        btnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                alert.cancel();
            }
        });

        alert.show();

    }

    public void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading! Please wait . . .");
        pDialog.show();

    }

    public void stopProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static class AppController extends Application {


        private static AppController app;

        @Override
        public void onCreate() {
            super.onCreate();
            app = this;
            setFontFamilyRegular();
        }

        public static synchronized AppController getInstance() {
            return app;
        }

        @Override
        public void registerComponentCallbacks(ComponentCallbacks callback) {
            super.registerComponentCallbacks(callback);
        }

        public void showToast(String msg) {
            Toast.makeText(app, ""+msg, Toast.LENGTH_SHORT).show();
        }

        public void showToastShort(String msg) {
            Toast.makeText(app, ""+msg, Toast.LENGTH_SHORT).show();
        }

        public int getRootHeight() {
            WindowManager localWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            assert localWindowManager != null;
            localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
            return localDisplayMetrics.heightPixels;
        }

        public int getRootWidth() {
            WindowManager localWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            assert localWindowManager != null;
            localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
            return localDisplayMetrics.widthPixels;
        }
        public void setFontFamilyBold() {
            //FontsManager.initFormAssets(getInstance(), "fonts/Assistant_Bold.ttf");
        }

        public void setFontFamilyRegular() {
           // FontsManager.initFormAssets(getInstance(), "fonts/Assistant_Regular.ttf");
        }

        public void setFontFamilySemiBold() {
           // FontsManager.initFormAssets(getInstance(), "fonts/Assistant_Semibold.ttf");
        }


    }
}
