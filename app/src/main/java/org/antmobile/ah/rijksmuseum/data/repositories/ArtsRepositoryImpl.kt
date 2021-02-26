package org.antmobile.ah.rijksmuseum.data.repositories

import org.antmobile.ah.rijksmuseum.data.repositories.datasource.ArtsRemoteDataSource
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.ah.rijksmuseum.utils.Result

class ArtsRepositoryImpl(
    private val remoteDataSource: ArtsRemoteDataSource
) : ArtsRepository {

    override suspend fun getCollection(page: Int): Result<ArtsPage> {
        return remoteDataSource.getCollection(page)
    }

    override suspend fun getArt(artId: String): Result<ArtDetails> {
        return remoteDataSource.getArt(artId)
    }
}
