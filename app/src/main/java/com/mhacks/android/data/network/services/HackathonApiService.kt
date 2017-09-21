package com.mhacks.android.data.network.services

import com.mhacks.android.data.kotlin.*
import com.mhacks.android.data.model.FcmDevice
import com.mhacks.android.data.model.Login
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by jeffreychang on 9/3/17.
 */

interface HackathonApiService {

    @GET("configuration")
    fun getMetaConfiguration(): Observable<MetaConfiguration>

    @POST("auth/login/")
    @FormUrlEncoded
    fun postLogin(@Field("email") email: String,
                  @Field("password") password: String): Observable<Login>

    @GET("user/profile/")
    fun getMetaUser(): Single<MetaUser>

    @GET("floor")
    fun getMetaFloors(): Observable<MetaFloor>

    @GET("announcements")
    fun getMetaAnnouncements(): Observable<MetaAnnouncements>

    @GET("event")
    fun getMetaEvent(): Observable<MetaEvents>

    @FormUrlEncoded
    @POST("device")
    fun postFirebaseToken(@Field("push_id") pushId: String): Observable<FcmDevice>

}
