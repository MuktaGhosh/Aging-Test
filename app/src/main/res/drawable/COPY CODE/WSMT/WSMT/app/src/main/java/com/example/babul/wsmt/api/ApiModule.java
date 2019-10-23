package com.example.babul.wsmt.api;

import android.content.Context;
import android.util.Log;

import com.example.babul.wsmt.models.BomBase;
import com.example.babul.wsmt.models.BomSearchDeatils;
import com.example.babul.wsmt.models.ErrorData;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.models.ProfileBase;
import com.example.babul.wsmt.models.ReverseLocation;
import com.example.babul.wsmt.models.SearchBom;
import com.example.babul.wsmt.models.Success;
import com.example.babul.wsmt.utils.SharedPreference;
import com.example.babul.wsmt.utils.UserDialog;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by newton on 7/9/17.
 */

public class ApiModule {
    private ApiRequests mApiRequests;
    private ResponseListener mResponseListener;
    private Context mContext;


    public ApiModule(Context context, ResponseListener listener) {
        mApiRequests = SetReadyApiRequest.getInstance().getRetrofit(SetReadyApiRequest.getInstance().getOkHttpClient()).create(ApiRequests.class);
        mResponseListener = listener;
        mContext = context;
    }


    public void login(String userName, String pass) {
        mApiRequests.getLogin(userName, pass)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ProfileBase>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);
                    }

                    @Override
                    public void onSuccess(ProfileBase otp) {
                        mResponseListener.getResponse(otp);
                    }
                });
    }

    public void handsets() {
        mApiRequests.getHandsets()
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Handset>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Handset> handsets) {
                        mResponseListener.getResponse(handsets);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }

    public void getBomsList(String id) {
        mApiRequests.getBomList( id)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BomBase>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BomBase bomBase) {
                        mResponseListener.getResponse(bomBase);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }

    public void searchBoms(String handset, String qrcode) {
        mApiRequests.searchBomDetails(handset,  qrcode)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BomSearchDeatils>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BomSearchDeatils  bomBase) {
                        mResponseListener.getResponse(bomBase);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }
    public void searchSMTBoms(String handset, String qrcode, String feeder_no) {
        mApiRequests.searchSMTBomDetails(handset,  qrcode, feeder_no)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SearchBom>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(SearchBom  bomBase) {
                        mResponseListener.getResponse(bomBase);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }

    public void getReverseLocationInfo(String handset, String location, String machineName) {
        mApiRequests.getReverseLocationInfo(handset,  location, machineName)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ReverseLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ReverseLocation>  bomBase) {
                        mResponseListener.getResponse(bomBase);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }

    public void getLocationFirstPart(String handset) {
        mApiRequests.getLocationFirstPart(handset)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<String>  bomBase) {
                        mResponseListener.getResponse(bomBase);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }
    public void getLocationSecondPart(String handset, String machine) {
        mApiRequests.getLocationSecondPart(handset, machine)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<String>  bomBase) {
                        mResponseListener.getResponse(bomBase);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }

    public void sendCheckData(String handset, String user_id, String qrcode, String item_chekced, String passFailStaus) {
        mApiRequests.sendcheckdata( handset, user_id, qrcode, item_chekced, passFailStaus)
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String  bomBase) {
                        mResponseListener.getResponse(bomBase);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getExceptions(throwable);

                    }
                });
    }


    private void getExceptions(Throwable throwable) {
        if (throwable instanceof NoNetworkException) {
            UserDialog.AppController.getInstance().showToastShort("No connectivity");
            mResponseListener.getError("No connectivity");
        } else if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).code();
            System.out.println(" err code == " + code + "/" +
                    ((HttpException) throwable).response().isSuccessful() + "/" +
                    ((HttpException) throwable).response().body() + "/" +
                    ((HttpException) throwable).response().message() + "/" +
                    ((HttpException) throwable).response().errorBody().byteStream().toString());

            switch (code) {
                case 500:
                    mResponseListener.getError(((HttpException) throwable).message());
                    break;
                case 501:
                    mResponseListener.getError(((HttpException) throwable).message());
                    break;
                case 401:
                    SharedPreference.setStringValue(mContext, SharedPreference.API_TOKEN, null);
                    Object response = getError(((HttpException) throwable).response());
                    if (response instanceof ErrorData) {
                        ErrorData mErrorData = (ErrorData) response;
                        System.out.println("error 401 : " + mErrorData.getErrorData());

                        mResponseListener.getError(mErrorData.getErrorData());
                    }
                    SharedPreference.setBooleanValue(mContext, SharedPreference.LOG_IN_STATUS, false);
                    SharedPreference.setStringValue(mContext, SharedPreference.API_TOKEN, null);
//                    Intent intent = new Intent(mContext, SignupOrLoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent);

//                        mResponseListener.getError((String) response);

                    break;
                case 400:
                    response = getError(((HttpException) throwable).response());
                    if (response instanceof ErrorData) {
                        try {
                            Log.e("error response", new Gson().toJson(response));
                            ErrorData mErrorData = (ErrorData) response;
                            mResponseListener.getError(mErrorData.getErrorData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("error response else", new Gson().toJson(response));
                        mResponseListener.getError((String) response);
                    }
                    break;
                case 403:
                    mResponseListener.getError(((HttpException) throwable).message());
                    break;
                case 404:
                    mResponseListener.getError(((HttpException) throwable).message());
                    break;
                default:
                    response = getError(((HttpException) throwable).response());
                    if (response instanceof ErrorData) {
                        try {
                            Log.e("default 1 response ", new Gson().toJson(response));
                            ErrorData mErrorData = (ErrorData) response;
                            mResponseListener.getError(mErrorData.getErrorData());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("default 1 message ", new Gson().toJson(response));
                        mResponseListener.getError((String) response);
                    }

                    Log.e("default 1", "" + ((HttpException) throwable).message());
                    mResponseListener.getError(((HttpException) throwable).message());
                    break;
            }
        } else if (throwable instanceof SocketTimeoutException) {
            mResponseListener.getError("Request time out");
        } else {
            Log.e("default 2", "" + throwable.getMessage());
            mResponseListener.getError("" + throwable.getMessage());
        }
    }

    private Object getError(Response errorData) {

        try {
            InputStream is = errorData.errorBody().byteStream();
            if (is != null) {
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder errorResult = new StringBuilder();
                String line;
                try {
                    while ((line = r.readLine()) != null) {
                        errorResult.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                return gson.fromJson(errorResult.toString(), ErrorData.class);


            } else return "Try again later!";
        } catch (Exception e) {
            return "" + errorData.message();
        }

    }
}
