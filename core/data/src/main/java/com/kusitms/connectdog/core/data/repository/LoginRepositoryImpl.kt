package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.api.InterApiService
import com.kusitms.connectdog.core.data.api.model.EmailDto
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.PhoneDto
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailAuthDto
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val interApi: InterApiService
) : LoginRepository {
    override suspend fun postLoginData(
        loginBody: NormalLoginBody
    ): LoginResponseItem {
        return api.postLoginData(
            loginBody
        )
    }

    override suspend fun postSocialLoginData(
        socialLoginBody: SocialLoginBody
    ): LoginResponseItem {
        return api.postSocialLoginData(
            socialLoginBody
        )
    }

    override suspend fun postIntermediatorLoginData(loginBody: NormalLoginBody): LoginResponseItem {
        return api.postIntermediatorLoginData(loginBody)
    }

    override suspend fun volunteerEmailSearch(phone: String): EmailDto {
        val body = PhoneDto(phone = phone)
        return api.volunteerEmailSearch(body)
    }

    override suspend fun interEmailSearch(phone: String): EmailDto {
        val body = PhoneDto(phone = phone)
        return interApi.interEmailSearch(body)
    }

    override suspend fun volunteerPasswordSearchAuth(email: String): EmailAuthDto {
        val body = EmailDto(email = email)
        return api.volunteerPasswordSearchAuth(body)
    }

    override suspend fun interPasswordSearchAuth(email: String): EmailAuthDto {
        val body = EmailDto(email = email)
        return interApi.interPasswordSearchAuth(body)
    }

    override suspend fun logout() {
        return api.logout()
    }
}
