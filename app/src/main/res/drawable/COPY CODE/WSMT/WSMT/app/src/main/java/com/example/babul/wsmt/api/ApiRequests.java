package com.example.babul.wsmt.api;

import com.example.babul.wsmt.models.BaseBomSearch;
import com.example.babul.wsmt.models.BomBase;
import com.example.babul.wsmt.models.BomSearchDeatils;
import com.example.babul.wsmt.models.Handset;
import com.example.babul.wsmt.models.ProfileBase;
import com.example.babul.wsmt.models.ReverseLocation;
import com.example.babul.wsmt.models.SearchBom;
import com.example.babul.wsmt.models.SuccessBase;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequests {


    /************** Authentication******************/


    @FormUrlEncoded
    @POST("users/applogin")
    Single<ProfileBase> getLogin(
            @Field("username") String token,
            @Field("password") String password);


    @GET("handsets/gethandsets")
    Single<List<Handset>> getHandsets();

    @GET("boms/getbomlist/{id}")
    Single<BomBase> getBomList(
            @Path("id") String token);

    @FormUrlEncoded
    @POST("boms/getbomdetails")
    Single<BomSearchDeatils> searchBomDetails(
            @Field("handset_id") String handset,
            @Field("qrcode") String qrcode
    );

    @FormUrlEncoded
    @POST("boms/getbomdetails")
    Single<SearchBom> searchSMTBomDetails(
            @Field("handset_id") String handset,
            @Field("qrcode") String qrcode,
            @Field("feeder") String feeder_no
    );

    @FormUrlEncoded
    @POST("iqcchecks/submitiqc")
    Single<String> sendcheckdata(
            @Field("handset_id") String handset,
            @Field("user_id") String user_id,
            @Field("partnumber") String qrcode,
            @Field("number") String item_of_checked,
            @Field("status") String passOrfail);



    @FormUrlEncoded
    @POST("boms/getreverselocation")
    Single<List<ReverseLocation>> getReverseLocationInfo(
            @Field("handset_id") String handset_id,
            @Field("location") String location,
            @Field("machine") String machine
    );


   // http://192.168.100.13/wsmt/boms/sequentialfeeder

    @FormUrlEncoded
    @POST("feeders/machinespinner")
    Single<List<String>> getLocationFirstPart(
      @Field("handset_id") String handset_id
    );

    @GET("feeders/machinespinner/{handset_id}/{machine}")
    Single<List<String>> getLocationSecondPart(
            @Path("handset_id") String handset_id,
            @Path("machine") String machine
    );

    @POST("api/v1/customer/authentication/logout")
    Single<SuccessBase> logout(@Header("Authorization") String token);

}


