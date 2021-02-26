package org.antmobile.ah.rijksmuseum.domain.usecases

import kotlinx.coroutines.withContext
import org.antmobile.ah.rijksmuseum.domain.models.ArtsPage
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.ah.rijksmuseum.utils.CoroutinesDispatcherProvider
import org.antmobile.ah.rijksmuseum.utils.Result

class GetListOfArtsUseCase(
    private val repository: ArtsRepository,
    private val dispatchers: CoroutinesDispatcherProvider
) {
    suspend fun execute(page: Int): Result<ArtsPage> {
        return withContext(dispatchers.io) {
            repository.getCollection(page)
        }
    }

}
