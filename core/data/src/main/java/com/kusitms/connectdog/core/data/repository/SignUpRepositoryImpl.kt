package com.kusitms.connectdog.core.data.repository

import com.google.gson.Gson
import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.AuthDto
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberBody
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.DuplicateDto
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorSignUpBody
import com.kusitms.connectdog.core.data.api.model.intermediator.NameDto
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordCheckResponse
import com.kusitms.connectdog.core.data.api.model.volunteer.PasswordDto
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

internal class SignUpRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
) : SignUpRepository {
    override suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse {
        return volunteerApi.postNickname(nickname)
    }

    override suspend fun postEmail(email: EmailCertificationBody): AuthDto {
        return volunteerApi.postEmail(email)
    }

    override suspend fun postNormalVolunteerSignUp(signUp: NormalVolunteerSignUpBody) {
        volunteerApi.postNormalVolunteerSignUp(signUp)
    }

    override suspend fun postSocialVolunteerSignUp(signUp: SocialVolunteerSignUpBody) {
        return volunteerApi.postSocialVolunteerSignUp(signUp)
    }

    override suspend fun postIntermediatorSignUp(signUp: IntermediatorSignUpBody, image: File) {
        val jsonBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            Gson().toJson(signUp)
        )

        val fileBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), image)

        val file = MultipartBody.Part.createFormData("profileImage", image.name, fileBody)

        return intermediatorApi.intermediatorSignUp(jsonBody, file)
    }

    override suspend fun getVolunteerPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
        return volunteerApi.getIsDuplicatePhoneNumber(body)
    }

    override suspend fun getInterMediatorPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
        return intermediatorApi.getIsDuplicatePhoneNumber(body)
    }

    override suspend fun isDuplicateInterNickName(body: NameDto): DuplicateDto {
        return intermediatorApi.checkIsDuplicateName(body)
    }

    override suspend fun checkVolunteerPassword(password: String): PasswordCheckResponse {
        val body = PasswordDto(password)
        return volunteerApi.checkVolunteerPassword(body)
    }

    override suspend fun checkInterPassword(password: String): PasswordCheckResponse {
        val body = PasswordDto(password)
        return intermediatorApi.checkInterPassword(body)
    }

    override suspend fun changeVolunteerPassword(password: String) {
        val body = PasswordDto(password)
        volunteerApi.changeVolunteerPassword(body)
    }

    override suspend fun changeInterPassword(password: String) {
        val body = PasswordDto(password)
        intermediatorApi.changeInterPassword(body)
    }
}
