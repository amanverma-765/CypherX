package com.akv.cypherx.data.remote

import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface GoogleScraper {

    suspend fun getSimilarNamedWebsiteUrl(searchName: String): Flow<ApiResponse<String>>

}