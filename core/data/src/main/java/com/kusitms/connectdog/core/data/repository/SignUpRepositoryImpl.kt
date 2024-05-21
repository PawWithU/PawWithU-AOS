package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.IsDuplicateNicknameResponse
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberBody
import com.kusitms.connectdog.core.data.api.model.IsDuplicatePhoneNumberResponse
import com.kusitms.connectdog.core.data.api.model.intermediator.IntermediatorSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailCertificationResponseItem
import com.kusitms.connectdog.core.data.api.model.volunteer.IsDuplicateNicknameBody
import com.kusitms.connectdog.core.data.api.model.volunteer.NormalVolunteerSignUpBody
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import javax.inject.Inject

internal class SignUpRepositoryImpl @Inject constructor(
    private val volunteerApi: ApiService,
    private val intermediatorApi: InterApiService
) : SignUpRepository {
    override suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse {
        return volunteerApi.postNickname(nickname)
    }

    override suspend fun postEmail(email: EmailCertificationBody): EmailCertificationResponseItem {
        return volunteerApi.postEmail(email)
    }

    override suspend fun postNormalVolunteerSignUp(signUp: NormalVolunteerSignUpBody) {
        volunteerApi.postNormalVolunteerSignUp(signUp)
    }

    override suspend fun postSocialVolunteerSignUp(signUp: SocialVolunteerSignUpBody) {
        return volunteerApi.postSocialVolunteerSignUp(signUp)
    }

    override suspend fun postIntermediatorSignUp(signUp: IntermediatorSignUpBody) {
        return intermediatorApi.intermediatorSignUp(signUp)
    }

    override suspend fun getVolunteerPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
        return volunteerApi.getIsDuplicatePhoneNumber(body)
    }

    override suspend fun getInterMediatorPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse {
        return intermediatorApi.getIsDuplicatePhoneNumber(body)
    }
}
