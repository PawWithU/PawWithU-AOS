package com.kusitms.connectdog.core.data.repository

import com.kusitms.connectdog.core.data.api.ApiService
import com.kusitms.connectdog.core.data.mapper.toData
import com.kusitms.connectdog.core.model.Application
import javax.inject.Inject

internal class ManagementRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ManagementRepository {
    override suspend fun getApplicationWaiting(page: Int?, size: Int?): List<Application> {
        return api.getApplicationWaiting(page, size).map { it.toData() }
    }

    override suspend fun getApplicationInProgress(page: Int?, size: Int?): List<Application> {
        return api.getApplicationInProgress(page, size).map { it.toData() }
    }

    override suspend fun getApplicationCompleted(page: Int?, size: Int?): List<Application> {
        return api.getApplicationCompleted(page, size).map { it.toData() }
    }

}