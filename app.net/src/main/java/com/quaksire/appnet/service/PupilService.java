package com.quaksire.appnet.service;

import com.quaksire.model.Pupil;
import com.quaksire.model.Pupils;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Julio.
 */

public interface PupilService {

    @GET("api/pupils")
    Observable<Pupils> getAllPupils(@Query("page") int page);

    @GET("api/pupils/{pupilId}")
    Observable<Pupil> getPupil(@Path("pupilId") int pupilId);

    @POST("api/pupils")
    Observable<ResponseBody> create(@Body Pupil pupil);

    @PUT("api/pupils")
    Observable<ResponseBody> update(@Body Pupil pupil);

    @DELETE("api/pupils/{pupilId}")
    Observable<ResponseBody> delete(@Path("pupilId") int pupilId);

}
