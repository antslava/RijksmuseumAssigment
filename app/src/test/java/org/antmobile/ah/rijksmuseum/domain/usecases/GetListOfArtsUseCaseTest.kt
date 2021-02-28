package org.antmobile.ah.rijksmuseum.domain.usecases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class GetListOfArtsUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var useCase: GetListOfArtsUseCase
    private lateinit var fakeRepository: ArtsRepository

    @Before
    fun setUp() {
        fakeRepository = mock(ArtsRepository::class.java)
        useCase = GetListOfArtsUseCase(fakeRepository, testDispatcher)
    }

    @Test
    fun `when specific page was requested then we should load that page`() =
        testDispatcher.runBlockingTest {
            val page = 10
            useCase.execute(page)

            verify(fakeRepository).getCollection(page)
        }


}
