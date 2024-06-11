package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.model.EmailDto
import com.kusitms.connectdog.core.data.api.model.LoginResponseItem
import com.kusitms.connectdog.core.data.api.model.NormalLoginBody
import com.kusitms.connectdog.core.data.api.model.SocialLoginBody
import com.kusitms.connectdog.core.data.api.model.volunteer.EmailAuthDto

interface LoginRepository {
    suspend fun postLoginData(
        loginBody: NormalLoginBody
    ): LoginResponseItem

    suspend fun postSocialLoginData(
        socialLoginBody: SocialLoginBody
    ): LoginResponseItem

    suspend fun postIntermediatorLoginData(
        loginBody: NormalLoginBody
    ): LoginResponseItem

    suspend fun volunteerEmailSearch(phone: String): EmailDto

    suspend fun interEmailSearch(phone: String): EmailDto

    suspend fun volunteerPasswordSearchAuth(email: String): EmailAuthDto

    suspend fun interPasswordSearchAuth(email: String): EmailAuthDto

    suspend fun logout()
}
