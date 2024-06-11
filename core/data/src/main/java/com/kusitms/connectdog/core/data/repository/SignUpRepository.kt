package com.kusitms.connectdog.core.data.repository

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
import com.kusitms.connectdog.core.data.api.model.volunteer.SocialVolunteerSignUpBody
import java.io.File

interface SignUpRepository {
    suspend fun postNickname(nickname: IsDuplicateNicknameBody): IsDuplicateNicknameResponse
    suspend fun postEmail(email: EmailCertificationBody): AuthDto
    suspend fun postNormalVolunteerSignUp(signUp: NormalVolunteerSignUpBody)
    suspend fun postSocialVolunteerSignUp(signUp: SocialVolunteerSignUpBody)
    suspend fun postIntermediatorSignUp(signUp: IntermediatorSignUpBody, file: File)
    suspend fun getVolunteerPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse
    suspend fun getInterMediatorPhoneNumberDuplicated(body: IsDuplicatePhoneNumberBody): IsDuplicatePhoneNumberResponse
    suspend fun isDuplicateInterNickName(body: NameDto): DuplicateDto
    suspend fun checkVolunteerPassword(password: String): PasswordCheckResponse
    suspend fun checkInterPassword(password: String): PasswordCheckResponse
    suspend fun changeVolunteerPassword(password: String)
    suspend fun changeInterPassword(password: String)
}
