package org.antmobile.ah.rijksmuseum.data.remote.datasource

import org.antmobile.ah.rijksmuseum.data.remote.LanguageProvider
import org.antmobile.ah.rijksmuseum.data.remote.RijksMuseumApi
import org.antmobile.ah.rijksmuseum.data.remote.toArt
import org.antmobile.ah.rijksmuseum.data.remote.toArtDetails
import org.antmobile.ah.rijksmuseum.data.repositories.datasource.ArtsRemoteDataSource
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.utils.Result
import org.antmobile.ah.rijksmuseum.utils.safeApiCall
import java.io.IOException

class ArtsRemoteDataSourceImpl(
    private val languageProvider: LanguageProvider,
    private val api: RijksMuseumApi
) : ArtsRemoteDataSource {

    override suspend fun getCollection(
        page: Int
    ): Result<ArtsPage> = safeApiCall(
        call = { requestGetCollection(page) },
        errorMessage = "Error getting collection. Page=${page}"
    )

    private suspend fun requestGetCollection(page: Int): Result<ArtsPage> {
        val total = page * RijksMuseumApi.PER_PAGE
        if (total > MAX_LOADED_ITEMS) {
            return Result.Failure(Exception("You reach maximum items"))
        }
        val response = api.getCollection(
            languageProvider.provideSupportedLanguage(),
            page,
            RijksMuseumApi.PER_PAGE
        )
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val canLoadMore = body.artObjects.size >= RijksMuseumApi.PER_PAGE
                val listOfArts = body.artObjects.map { it.toArt() }
                return Result.Success(ArtsPage(listOfArts, canLoadMore))
            }
        }

        return Result.Failure(
            IOException("Error getting art collection. Code=${response.code()}, Message=${response.message()}")
        )
    }

    override suspend fun getArt(artId: String): Result<ArtDetails> = safeApiCall(
        call = { requestGetArtDetails(artId) },
        errorMessage = "Error getting art details. ArtId=${artId}"
    )

    private suspend fun requestGetArtDetails(artId: String): Result<ArtDetails> {
        val language = languageProvider.provideSupportedLanguage()
        val response = api.getArtDetails(language, artId)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body.artObject.toArtDetails())
            }
        }

        return Result.Failure(
            IOException("Error getting art details. Code=${response.code()}, Message=${response.message()}")
        )
    }

    private companion object {
        const val MAX_LOADED_ITEMS = 10_000
    }

}
