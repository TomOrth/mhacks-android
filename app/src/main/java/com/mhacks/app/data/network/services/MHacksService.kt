package com.mhacks.app.data.network.services

import com.mhacks.app.data.models.*
import com.mhacks.app.data.models.FcmDevice
import io.reactivex.Single
import retrofit2.http.*

/**
 * Provides definitions for interactions with the RESTful service.
 */

interface MHacksService {

    @GET("configuration/")
    fun getConfigurationResponse(): Single<ConfigurationResponse>

    @POST("auth/login/")
    @FormUrlEncoded
    fun postLogin(@Field("email") email: String,
                  @Field("password") password: String): Single<Login>

    @GET("user/profile/")
    fun getUserResponse(): Single<UserResponse>

    @POST("user/ticket/verify")
    @FormUrlEncoded
    fun verifyUserTicket(@Field("email") email: String): Single<VerifyTicketResponse>

    @GET("floor")
    fun getFloorResponse(): Single<FloorResponse>

    @GET("announcements")
    fun getAnnouncementResponse(): Single<AnnouncementResponse>

    @POST("announcements")
    @FormUrlEncoded
    fun postAnnouncement(@Field("title") title: String,
                         @Field("category") category: String,
                         @Field("body") body: String,
                         @Field("isApproved") isApproved: Boolean,
                         @Field("isSent") isSent: Boolean,
                         @Field("push") push: Boolean): Single<CreateAnnouncement>

    @GET("event")
    fun getEventResponse(): Single<EventsResponse>

    @FormUrlEncoded
    @POST("device")
    fun postFireBaseToken(@Field("push_id") pushId: String): Single<FcmDevice>

}
