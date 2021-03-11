package org.antmobile.rijksmuseum.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.antmobile.rijksmuseum.domain.exceptions.WrongArtIdException
import org.antmobile.rijksmuseum.domain.models.ArtDetails
import org.antmobile.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.rijksmuseum.utils.Result

class GetArtDetailsByIdUseCase(
    private val artsRepository: ArtsRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun execute(artId: String): Result<ArtDetails> = withContext(ioDispatcher) {
        if (artId.isBlank()) {
            Result.Failure(WrongArtIdException("ArtId should not be blank"))
        } else {
            artsRepository.getArt(artId)
        }
    }

}
