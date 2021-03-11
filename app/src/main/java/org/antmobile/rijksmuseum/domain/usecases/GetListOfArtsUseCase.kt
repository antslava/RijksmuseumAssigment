package org.antmobile.rijksmuseum.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.antmobile.rijksmuseum.domain.models.ArtsPage
import org.antmobile.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.rijksmuseum.utils.Result

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
