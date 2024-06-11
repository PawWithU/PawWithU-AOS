package com.kusitms.connectdog.core.data.api

import com.kusitms.connectdog.core.data.api.model.EmailDto
import com.kusitms.connectdog.core.data.api.model.FcmTokenRequestBody
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberBody
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberResponse
import com.kusitms.connectdog.core.data.api.model.PhoneDto
import com.kusitms.connectdog.core.data.api.model.Response
import com.kusitms.connectdog.core.data.api.model.VolunteerResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.DuplicateDto
import com.kusitms.connectdog.core.data.api.model.intermediator.InterAnnouncementDetailResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationCompletedResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationInProgressResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationRecruitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterApplicationWaitingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileFindingResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.InterProfileInfoResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorAccountInfo
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorProfileInfoResponseItem
import com.kusitms.connectdog.core.data.api.model.intermediator.NameDto
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailAuthDto
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordCheckResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordDto
import com.kusitms.connectdog.core.data.api.model.volunteer.ReviewDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

internal interface InterApiService {

    /**
     * 회원가입
     */
    @Multipart
    @POST("/intermediaries/sign-up")
    suspend fun intermediatorSignUp(
        @Part("request") json: RequestBody,
        @Part file: MultipartBody.Part
    )

    @POST("/intermediaries/phone/isDuplicated")
    suspend fun getIsDuplicatePhoneNumber(
        @Body body: IsDuplicatePhoneNumberBody
    ): IsDuplicatePhoneNumberResponse

    @POST("/intermediaries/name/isDuplicated")
    suspend fun checkIsDuplicateName(
        @Body body: NameDto
    ): DuplicateDto

    @POST("/intermediaries/password/check")
    suspend fun checkInterPassword(
        @Body body: PasswordDto
    ): PasswordCheckResponse

    @PATCH("/intermediaries/password")
    suspend fun changeInterPassword(
        @Body body: PasswordDto
    )

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

    @GET("/intermediaries/reviews/{reviewId}")
    suspend fun getReviewDetail(
        @Path("reviewId") reviewId: Long
    ): ReviewDetailResponse

    @GET("/intermediaries/posts/{postId}")
    suspend fun getAnnouncementDetail(
        @Path("postId") postId: Long
    ): InterAnnouncementDetailResponse

    /**
     * 이동봉사 모집자 프로필
     * */
    @GET("/intermediaries/my/info")
    suspend fun getIntermediatorInfo(): InterProfileInfoResponse

    @GET("/intermediaries/my/reviews")
    suspend fun getIntermediatorReview(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<ReviewDetailResponse>

    @GET("/intermediaries/posts/recruiting")
    suspend fun getFindingApplication(
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): List<InterProfileFindingResponseItem>

    @DELETE("/intermediaries/posts/{postId}")
    suspend fun deleteAnnouncement(
        @Path("postId") postId: Long
    )

    @POST("/intermediaries/fcm")
    suspend fun postFcmToken(
        @Body fcmToken: FcmTokenRequestBody
    )

    @PATCH("/intermediaries/notifications/setting")
    suspend fun patchNotification()

    @POST("/intermediaries/search/send-email")
    suspend fun interPasswordSearchAuth(
        @Body body: EmailDto
    ): EmailAuthDto

    @Multipart
    @POST("/intermediaries/posts")
    suspend fun postApplication(
        @Part("request") json: RequestBody,
        @Part files: List<MultipartBody.Part>
    )

    @GET("/intermediaries/setting/my/info")
    suspend fun getInterAccountInfo(): IntermediatorAccountInfo

    @DELETE("/intermediaries/my")
    suspend fun interWithdraw()

    @POST("/intermediaries/search/email")
    suspend fun interEmailSearch(
        @Body body: PhoneDto
    ): EmailDto
}
