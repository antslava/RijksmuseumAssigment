package org.antmobile.ah.rijksmuseum.domain.usecases

import kotlinx.coroutines.withContext
import org.antmobile.ah.rijksmuseum.domain.exceptions.WrongArtIdException
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.ah.rijksmuseum.utils.CoroutinesDispatcherProvider
import org.antmobile.ah.rijksmuseum.utils.Result

class GetArtDetailsByIdUseCase(
    private val artsRepository: ArtsRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {

    suspend fun execute(artId: String): Result<ArtDetails> = withContext(dispatchers.io) {
        if (artId.isBlank()) {
            Result.Failure(WrongArtIdException("ArtId should not be blank"))
        } else {
            artsRepository.getArt(artId)
        }
    }

}
