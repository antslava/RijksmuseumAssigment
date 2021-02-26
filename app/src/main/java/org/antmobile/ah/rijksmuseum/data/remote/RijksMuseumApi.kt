package org.antmobile.ah.rijksmuseum.data.remote

import org.antmobile.ah.rijksmuseum.data.remote.entities.collections.CollectionsResponse
import org.antmobile.ah.rijksmuseum.data.remote.entities.details.ArtDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
 * Models the RijksMuseumApi.
 * See https://data.rijksmuseum.nl/object-metadata/api/
 */
interface RijksMuseumApi {

    @GET("api/{language}/collection")
    suspend fun getCollection(
        @Path("language") language: Language,
        @Query("p") page: Int,
        @Query("ps") limit: Int,
        @Query("s") sortBy: SortBy = SortBy.ARTIST,
        @Query("imgonly") imageOnly: Boolean = true
    ): Response<CollectionsResponse>

    @GET("api/{language}/collection/{id}")
    suspend fun getArtDetails(
        @Path("language") language: Language,
        @Path("id") id: String,
    ): Response<ArtDetailsResponse>

    companion object {
        const val ENDPOINT = "https://www.rijksmuseum.nl/"
        const val PER_PAGE = 20
    }

}

enum class SortBy(
    private val value: String
) {
    RELEVANCE("relevance"),
    OBJECT_TYPE("objecttype"),
    OLDEST_FIRST("chronologic"),
    NEWEST_FIRST("achronologic"),
    ARTIST("artist"),
    ARTIST_DESC("artistdesc");

    override fun toString(): String = value.toLowerCase(Locale.ENGLISH)

}
