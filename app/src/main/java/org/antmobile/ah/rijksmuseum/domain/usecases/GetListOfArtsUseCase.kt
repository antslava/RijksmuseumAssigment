package org.antmobile.ah.rijksmuseum.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.ah.rijksmuseum.utils.Result

class GetListOfArtsUseCase(
    private val repository: ArtsRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun execute(page: Int): Result<ArtsPage> {
        return withContext(ioDispatcher) {
            repository.getCollection(page)
        }
    }

}
