package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberBody
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberResponse
import com.kusitms.connectdog.core.data.api.model.Response
import com.kusitms.connectdog.core.data.api.model.ReviewResponseItem
import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationRecruitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileFindingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorSignUpBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface InterApiService {

    /**
     * 회원가입
     */
    @POST("/intermediaries/sign-up")
    suspend fun intermediatorSignUp(
        @Body body: IntermediatorSignUpBody
    )

    @POST("/intermediaries/phone/isDuplicated")
    suspend fun getIsDuplicatePhoneNumber(
        @Body body: IsDuplicatePhoneNumberBody
    ): IsDuplicatePhoneNumberResponse

    /**
     * 봉사관리
     */
    @GET("/intermediaries/home")
    suspend fun getIntermediatorProfileInfo(): IntermediatorProfileInfoResponseItem

    @GET("/intermediaries/posts/recruiting")
    suspend fun getApplicationRecruiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationRecruitingResponseItem>

    @GET("/intermediaries/applications/waiting")
    suspend fun getApplicationWaiting(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationWaitingResponseItem>

    @GET("/intermediaries/applications/progressing")
    suspend fun getApplicationProgressing(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationInProgressResponseItem>

    @GET("/intermediaries/applications/completed")
    suspend fun getApplicationCompleted(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterApplicationCompletedResponseItem>

    @GET("/intermediaries/applications/{applicationId}")
    suspend fun getApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    ): VolunteerResponse

    @PATCH("/intermediaries/applications/{applicationId}")
    suspend fun patchApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    ): Response

    @DELETE("/intermediaries/applications/{applicationId}")
    suspend fun deleteApplicationVolunteer(
        @Path("applicationId") applicationId: Long
    ): Response

    @PATCH("/intermediaries/applications/{applicationId}/completed")
    suspend fun patchApplicationCompleted(
        @Path("applicationId") applicationId: Long
    ): Response

    /**
     * 이동봉사 모집자 프로필
     * */
    @GET("/intermediaries/my/info")
    suspend fun getIntermediatorInfo(): InterProfileInfoResponse

    @GET("/intermediaries/my/reviews")
    suspend fun getIntermediatorReview(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ReviewResponseItem>

    @GET("/intermediaries/posts/recruiting")
    suspend fun getFindingApplication(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterProfileFindingResponseItem>
}
