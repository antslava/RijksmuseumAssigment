package org.antmobile.rijksmuseum.data.repositories

import org.antmobile.rijksmuseum.data.repositories.datasource.ArtsRemoteDataSource
import org.antmobile.rijksmuseum.domain.models.ArtDetails
import org.antmobile.rijksmuseum.domain.models.ArtsPage
import org.antmobile.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.rijksmuseum.utils.Result

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
