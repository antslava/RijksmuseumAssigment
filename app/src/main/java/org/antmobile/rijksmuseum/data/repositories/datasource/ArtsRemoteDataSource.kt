package org.antmobile.rijksmuseum.data.repositories.datasource

import org.antmobile.rijksmuseum.domain.models.ArtDetails
import org.antmobile.rijksmuseum.domain.models.ArtsPage
import org.antmobile.rijksmuseum.utils.Result

interface ArtsRemoteDataSource {

    suspend fun getCollection(
        page: Int,
    ): Result<ArtsPage>

    suspend fun getArt(artId: String): Result<ArtDetails>

}
