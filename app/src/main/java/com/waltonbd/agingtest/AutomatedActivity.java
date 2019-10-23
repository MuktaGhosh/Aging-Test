package com.waltonbd.agingtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AutomatedActivity extends AppCompatActivity  {
    private static final String ACCESS_FINE_LOCATION ="ACCESS_FINE_LOCATION" ;

    Button tbutton,wifibutton,bluetoothbutton;
    VideoView videoView1;
    private SeekBar brightbar;
    private boolean flashLightStatus = false;
    //Content resolver used as a handle to the system's settings
    private ContentResolver Conresolver;
    //Window object, that will store a reference to the current window
    private Window window;
    int Brightness,getbrightness;
    Context context,mContext;
    LocationManager locationManager ;
    boolean GpsStatus ;
    private BluetoothAdapter mBluetoothAdapter;
    boolean wifiEnabled;
    WifiManager wifiManager;
    final static int REQUEST_LOCATION = 199;
    private MediaController mediaController;
    String cameraId = null;
    CameraManager camManager;
    int flag =0;
    private TextView timerValue,timerbuttonone,timerbutoontwo;
    private long startTime = 0L;
    private Handler customHandler ;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    public int counter;
    boolean retVal = false;

    private int brightness;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //Window object, that will store a reference to the current window


    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        context = this;

        tbutton = (Button) findViewById(R.id.toggleButton);
        brightbar = (SeekBar) findViewById(R.id.seekBar);
        wifibutton = (Button) findViewById(R.id.wifiButton);
        bluetoothbutton = (Button) findViewById(R.id.bluetoothButton);
        videoView1 = (VideoView) findViewById(R.id.videoButton);
        timerValue = (TextView) findViewById(R.id.timerButton);
        timerbuttonone = (TextView) findViewById(R.id.timerButtonone);
        timerbutoontwo = (TextView) findViewById(R.id.timerButtontwo);
        // requestPermission();

        //turnGPSOn();

        startTime = SystemClock.uptimeMillis();
        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1246FF")));

       checkSystemWritePermission();
        try {
            Process p = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }

        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiEnabled = wifiManager.isWifiEnabled();
         camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        // Usually back camera is at 0 position.
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (wifiEnabled == true) {
            wifibutton.setBackgroundResource(R.drawable.wifi_on);
        }
        if (mBluetoothAdapter == null) {
            bluetoothNotAvailable();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                bluetoothEnabled();
            }
        }
        getbrightness= getbrighness();

        //Setting up current screen brightness to seekbar;
        brightbar.setProgress(getbrightness);
        videoView1.setZOrderOnTop(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            try {
                cameraId = camManager.getCameraIdList()[0];

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }
        handeler(6000);

      /*  try {
            if(retVal==true) {
                handeler(6000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/




        cResolver = getContentResolver();
//Get the current window
        window = getWindow();



    }



    @Override
    public void onResume() {
        super.onResume();
        Log.e("resume","resume");

    }
    private  void flashLightOff()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

            try {
                cameraId = camManager.getCameraIdList()[0];
                camManager.setTorchMode(cameraId, true);   //Turn ON

                tbutton.setBackgroundResource(R.drawable.lighton);
                flashLightStatus = true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }



    }
    private void bluetoothNotAvailable() {
        Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();

    }

    private void bluetoothEnabled() {
        bluetoothbutton.setBackgroundResource(R.drawable.bluetooth_on);

    }

    private  void brightnessfull()
    {
        //Get the content resolver
        Conresolver = getContentResolver();
        //Get the current window
        window = getWindow();
        //Getting Current screen brightness.
try{

       android.provider.Settings.System.putInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS, 255);
        getbrightness= getbrighness();
        brightbar.setProgress(getbrightness);

        brightbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.e("pro", String.valueOf(i));

                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS, i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });
}catch (Exception e) {
    e.printStackTrace();
}
    }
private int getbrighness()
{
    try {
        Brightness = android.provider.Settings.System.getInt(
                getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS);
        Log.e("brightness", String.valueOf(Brightness));
    } catch (Settings.SettingNotFoundException e) {
        e.printStackTrace();
    }
    return  Brightness;
}

    private boolean checkSystemWritePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.e("write", "Can Write Settings: " + retVal);
            if(retVal){
                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            }else{
                Log.e("write", "Can Write Settings: " + retVal);
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                startActivity(intent);

            }
        }
        return retVal;
    }

    public void handeler( int time)
    {
        try {
            int TIME = time;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // customHandler.postDelayed(updateTimerThread, 0);
                    flashLightOff();


                    brightnessfull();


                    if (wifiEnabled == false) {
                        wifiManager.setWifiEnabled(true);
                        wifibutton.setBackgroundResource(R.drawable.wifi_on);

                    }
                    if (!mBluetoothAdapter.isEnabled()) {
                        BluetoothAdapter.getDefaultAdapter().enable();
                        bluetoothbutton.setBackgroundResource(R.drawable.bluetooth_on);
                    }
                    torchbuttonclick();
                    wifibuttonclick();
                    bluetoothbuttonclick();
                   // videoClick();
                   /* Intent intent = new Intent(AutomatedActivity.this, MainActivity.class);
                    startActivity(intent);*/


                }
            }, TIME);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Invoke background service onStop method.", Toast.LENGTH_LONG).show();
       // timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            int mins = 0;

                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                int secs = (int) (updatedTime / 1000);
                Log.e("secs", String.valueOf(secs));
                 mins = secs / 60;
                 Log.e("mins", String.valueOf(mins));
                secs = secs % 60;
                int hour = mins / 60;
            Log.e("hour", String.valueOf(hour));
int temp=mins;

                 mins=mins%60;
            Log.e("mins%60", String.valueOf(mins));

                // int milliseconds = (int) (updatedTime % 1000);
                timerValue.setText(" " + hour + " :" +
                        " " +  String.format("%02d", mins) + " :" + " "
                        + String.format("%02d", secs)
                );
                customHandler.postDelayed(this, 0);

            }


    };
    /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, 5000);*/
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void turnGPSOn() {
        GPSStatus();
        statusCheck();


    }
    public void wifibuttonclick(){

        wifibutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            public void onClick(View v) {
                wifiEnabled = wifiManager.isWifiEnabled();
                if (wifiEnabled == true) {

                    int flag=1;
                    Log.e("flag1", String.valueOf(flag));
                    wifiManager.setWifiEnabled(false);
                    wifibutton.setBackgroundResource(R.drawable.wifi_off);
                    //  wifiEnabled = wifiManager.isWifiEnabled();
                    Log.e("wifienable_true", String.valueOf(wifiEnabled));
                }
                else if(wifiEnabled==false) {

                    int flag=2;
                    Log.e("flag2", String.valueOf(flag));
                    wifiManager.setWifiEnabled(true);
                    wifibutton.setBackgroundResource(R.drawable.wifi_on);
                    Log.e("wifienable_false", String.valueOf(wifiEnabled));
                }

            }
        });
    }
    public void videoClick()
    {
        videoView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            public void onClick(View v) {
               /* Intent intent = new Intent(AutomatedActivity.this, MainActivity.class);
                startActivity(intent);*/
            }
        });

    }

    public void bluetoothbuttonclick(){

        bluetoothbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            public void onClick(View v) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                    if (!mBluetoothAdapter.isEnabled()) {
                        BluetoothAdapter.getDefaultAdapter().enable();
                        bluetoothbutton.setBackgroundResource(R.drawable.bluetooth_on);
                    }
                    else
                    {
                        BluetoothAdapter.getDefaultAdapter().disable();
                        bluetoothbutton.setBackgroundResource(R.drawable.bluetooth_off);
                    }

            }
        });
    }

    public void torchbuttonclick(){

        tbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            public void onClick(View v) {
                camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
if(flashLightStatus==true)
{
    Log.e("flash_true", String.valueOf(flashLightStatus));
    try {
        cameraId = camManager.getCameraIdList()[0];
        camManager.setTorchMode(cameraId, false);   //Turn OFF

        tbutton.setBackgroundResource(R.drawable.lightoff);
        flashLightStatus = false;
    } catch (CameraAccessException e) {
        e.printStackTrace();
    }
}
else if(flashLightStatus== false)
{
    Log.e("flash_flase", String.valueOf(flashLightStatus));
    try {
        cameraId = camManager.getCameraIdList()[0];
        camManager.setTorchMode(cameraId, true);   //Turn ON

        tbutton.setBackgroundResource(R.drawable.lighton);
        flashLightStatus = true;
    } catch (CameraAccessException e) {
        e.printStackTrace();
    }

}


            }
        });
    }
    public void GPSStatus(){
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.e("gpsstatus", String.valueOf(GpsStatus));
    }
    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.LOCATION_HARDWARE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {}

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

            }




        }).check();
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();
        Intent intent = new Intent(AutomatedActivity.this, AutomatedActivity.class);
        startActivity(intent);

    }




}
