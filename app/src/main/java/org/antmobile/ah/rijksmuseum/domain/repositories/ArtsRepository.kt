package org.antmobile.ah.rijksmuseum.domain.repositories

import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.utils.Result

interface ArtsRepository {

    suspend fun getCollection(
        page: Int
    ): Result<ArtsPage>

    suspend fun getArt(artId: String): Result<ArtDetails>
}
