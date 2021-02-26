package org.antmobile.ah.rijksmuseum.data.repositories.datasource

import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.utils.Result

interface ArtsRemoteDataSource {

    suspend fun getCollection(
        page: Int,
    ): Result<ArtsPage>

    suspend fun getArt(artId: String): Result<ArtDetails>

}
