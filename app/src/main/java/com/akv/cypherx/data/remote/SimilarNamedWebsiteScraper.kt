package com.akv.cypherx.data.remote

import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class SimilarNamedWebsiteScraper : GoogleScraper {

    private val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    private val regex = Regex("https://[\\w.-]+")

    override suspend fun getSimilarNamedWebsiteUrl(searchName: String): Flow<ApiResponse<String>> {
        val searchUrl = "https://www.google.com/search?q=$searchName"
        return flow {
            emit(ApiResponse.Loading)
            try {
                val doc = Jsoup.connect(searchUrl).userAgent(userAgent).get()
                val url = extractFirstValidUrl(doc, regex)
                if (url.isNotEmpty()) {
                    emit(ApiResponse.Success(url))
                } else {
                    emit(ApiResponse.Error("No URL found"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ApiResponse.Error("Network error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun extractFirstValidUrl(doc: Document, regex: Regex): String {
        val elements = doc.select("a[href]")
        for (element in elements) {
            val url = element.attr("href")
            if (url.startsWith("/url?esrc=")) {
                val match = regex.find(url)
                match?.groups?.get(0)?.value?.let {
                    return it
                }
            }
        }
        return ""
    }
}
